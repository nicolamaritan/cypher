package com.project.passwordmanager.security

import java.security.MessageDigest

class Hashing
{
    companion object {
        fun sha_256(input: String): String
        {
            val bytes = input.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            return digest.fold("") { str, it -> str + "%02x".format(it) }
        }
    }
}