package com.project.passwordmanager.common

import com.project.passwordmanager.model.Credential

/**
 * Utility object for validating credentials in the password manager application.
 *
 * This object provides methods to validate individual components of a Credential object,
 * such as the service, username, and password.
 * @see Credential
 */
object CredentialValidator {

    /**
     * Validates a Credential object.
     *
     * @param credential The Credential object to validate.
     * @return `true` if the credential is valid, `false` otherwise.
     */
    fun validate(credential: Credential): Boolean {
        return isServiceValid(credential.service) &&
                isUsernameValid(credential.username) &&
                isPasswordValid(credential.password)
    }

    /**
     * Validates the service of a Credential.
     *
     * @param service The service value to validate.
     * @return `true` if the service is valid, `false` otherwise.
     */
    private fun isServiceValid(service: String): Boolean {
        return service.isNotBlank()
    }

    /**
     * Validates the username of a Credential.
     *
     * @param username The username value to validate.
     * @return `true` if the username is valid, `false` otherwise.
     */
    private fun isUsernameValid(username: String): Boolean {
        return username.isNotBlank()
    }

    /**
     * Validates the password of a Credential.
     *
     * @param password The password value to validate.
     * @return `true` if the password is valid, `false` otherwise.
     */
    private fun isPasswordValid(password: String): Boolean {
        return password.isNotBlank()
    }
}
