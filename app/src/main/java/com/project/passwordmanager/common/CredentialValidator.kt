package com.project.passwordmanager.common

import com.project.passwordmanager.model.Credential

object CredentialValidator
{
    fun validate(credential: Credential): Boolean
    {
        return isServiceValid(credential.service) &&
                isUsernameValid(credential.username) &&
                isPasswordValid(credential.password)
    }

    private fun isServiceValid(service: String): Boolean
    {
        return service.isNotBlank()
    }

    private fun isUsernameValid(username: String): Boolean
    {
        return username.isNotBlank()
    }

    private fun isPasswordValid(password: String): Boolean
    {
        return password.isNotBlank()
    }

}
