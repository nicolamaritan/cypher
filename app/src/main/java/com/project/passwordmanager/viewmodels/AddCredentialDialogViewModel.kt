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
 * ViewModel class for `CredentialsDialogFragment`.
 * This class is responsible for handling user interactions and data management related to adding new credentials.
 * It provides methods to add credentials and handle events for displaying toasts.
 *
 * @param dao The DAO (Data Access Object) for accessing the Credential entity in the database.
 * @see com.project.passwordmanager.fragments.AddCredentialDialogFragment
 */
class AddCredentialDialogViewModel(private val dao: CredentialDao) : ViewModel()
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
     * Inserted master password by the user
     */
    var insertedMasterPassword = ""


    private var _toastStringId = MutableLiveData<Int>()

    /**
     * LiveData for observing toast events.
     */
    val toastStringId: LiveData<Int>
        get() = _toastStringId

    /**
     * Adds a new credential to the database.
     *
     * This method creates a new Credential object using the input data and inserts it into the database using the DAO.
     * Before inserting, it validates the input data.
     * If the input data is invalid, it emits a toast event signaling that the data is invalid.
     * After inserting the credential, it sets the closing flag to true and emits a toast event with positive
     * result message.
     *
     * @return True if the credential is added successfully, false otherwise.
     */
    fun addCredential()
    {
        Log.d(TAG, "addCredential invoked.")
        val credential = Credential()
        credential.username = newCredentialUsername
        credential.service = newCredentialService
        credential.password = newCredentialPassword
        credential.date = LocalDate.now()

        // Encrypt the password before insertion
        credential.password = Cryptography.encryptText(newCredentialPassword, insertedMasterPassword)
        viewModelScope.launch {
            dao.insert(credential)
        }

        _toastStringId.value = R.string.credential_added_toast
    }

    /**
     * Validates the input credential data.
     *
     * This method validates the username, service, and password of the new credential.
     * If any of the fields is invalid, it emits a toast event signaling that the credential is invalid.
     *
     * @return True if the credential is valid, false otherwise.
     */
    fun validateCredential() : Boolean
    {
        val credential = Credential()
        credential.username = newCredentialUsername
        credential.service = newCredentialService
        credential.password = newCredentialPassword

        if (!CredentialValidator.validate(credential)) {
            _toastStringId.value = R.string.invalid_credential_toast
            return false
        }
        return true
    }

    /**
     * Checks if the inserted master password is correct.
     *
     * This method compares the inserted master password with the true hashed master password.
     * If they match, it returns true. Otherwise, it emits a toast event signaling that the master password is wrong.
     *
     * @param trueHashedMasterPassword The true hashed master password stored in the system.
     * @return True if the inserted master password is correct, false otherwise.
     */
    fun checkInsertedMasterPassword(trueHashedMasterPassword: String) : Boolean
    {
        if (Hashing.sha256(insertedMasterPassword) != trueHashedMasterPassword)
        {
            _toastStringId.value = R.string.wrong_master_password
            return false
        }
        return true
    }

    companion object
    {
        val TAG: String = AddCredentialDialogViewModel::class.java.simpleName
    }
}
