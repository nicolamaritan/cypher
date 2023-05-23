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
import kotlinx.coroutines.launch

/**
 * ViewModel class for CredentialsDialogFragment.
 * This class is responsible for handling user interactions and data management related to adding new credentials.
 * It provides methods to add credentials and handle events for displaying toasts.
 *
 * @param dao The DAO (Data Access Object) for accessing the Credential entity in the database.
 * @see com.project.passwordmanager.fragments.CredentialsDialogFragment
 */
class CredentialsDialogViewModel(private val dao: CredentialDao) : ViewModel() {

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

    private val _toastStringId = MutableLiveData<Int>()

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
     */
    fun addCredential() {
        Log.d(TAG, "addCredential invoked.")
        val credential = Credential()
        credential.username = newCredentialUsername
        credential.service = newCredentialService
        credential.password = newCredentialPassword

        if (!CredentialValidator.validate(credential)) {
            _toastStringId.value = R.string.invalid_credential_toast
            return
        }

        viewModelScope.launch {
            dao.insert(credential)
        }

        closing = true
        _toastStringId.value = R.string.credential_added_toast
    }

    companion object {
        /**
         * TAG used for logging.
         */
        val TAG: String = CredentialsDialogViewModel::class.java.simpleName
    }
}
