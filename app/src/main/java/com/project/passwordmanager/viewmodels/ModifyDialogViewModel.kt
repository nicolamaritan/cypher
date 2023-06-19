package com.project.passwordmanager.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.passwordmanager.R
import com.project.passwordmanager.common.CredentialValidator
import com.project.passwordmanager.model.Credential
import com.project.passwordmanager.model.CredentialDao
import com.project.passwordmanager.security.Cryptography
import com.project.passwordmanager.security.Hashing
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * ViewModel class for `ModifyDialogFragment`.
 * This class is responsible for handling user interactions and data management related to modifying new credentials.
 * It provides methods to modify credentials and handle events for displaying toasts.
 *
 * @param dao The `CredentialDao` for accessing the Credential entity in the database.
 * @see com.project.passwordmanager.fragments.ModifyDialogFragment
 */

class ModifyDialogViewModel(private val dao: CredentialDao) : ViewModel()
{
    /**
     * The username for the new credential.
     */
    var newCredentialUsername = ""

    /**
     * The service for the new credential.
     */
    var newCredentialService = ""

    /**
     * The password for the new credential.
     */
    var newCredentialPassword = ""

    /**
     * Indicates whether the dialog is closing.
     */
    var closing = false

    /**
     * Inserted master password by the user
     */
    var insertedMasterPassword = ""

    /**
     * Saves if the viewModel is istantiated for the first time by the system.
     */
    var firstInstantiation = true


    private val _toastStringId = MutableLiveData<Int>()

    /**
     * LiveData for observing toast events.
     */
    val toastStringId: LiveData<Int>
        get() = _toastStringId

    /**
     * Modifies a credential to the database.
     *
     * This method modify a Credential object using the input data and inserts it into the database using the DAO.
     * Before inserting, it validates the input data.
     * If the input data is invalid, it emits a toast event signaling that the data is invalid.
     * After modifying the credential, it sets the closing flag to true and emits a toast event with positive
     * result message.
     *
     * @param credentialId the id of the credential to modify
     * @param hashedMasterPassword hashed master password passed by the fragment
     */
    fun modifyCredential(credentialId : Int, hashedMasterPassword: String) {
        Log.d(TAG, "modifyCredential invoked.")
        val credential = Credential()
        credential.id = credentialId
        credential.username = newCredentialUsername
        credential.service = newCredentialService

        /* This value is only temporary, as it will be overwritten
        *  encrypted value. This is set to check if the user has
        *  inserted text in the field, blocking if it is empty.
        * */
        credential.password = newCredentialPassword


        if (!CredentialValidator.validate(credential)) {
            _toastStringId.value = R.string.invalid_credential_toast
            return
        }

        if (Hashing.sha256(insertedMasterPassword) != hashedMasterPassword)
        {
            _toastStringId.value = R.string.wrong_master_password
            return
        }

        // Encrypt the new password with the master password
        credential.password = Cryptography.encryptText(
            newCredentialPassword,
            insertedMasterPassword
        )

        // Update the last modified date field
        credential.date = LocalDate.now()

        viewModelScope.launch {
            dao.update(credential)
            _toastStringId.value = R.string.credential_modified_toast
        }
        closing = true

    }

    companion object
    {
        val TAG: String = ModifyDialogViewModel::class.java.simpleName
    }
}