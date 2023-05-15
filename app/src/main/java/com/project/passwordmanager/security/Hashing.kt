package com.project.passwordmanager.security

import java.security.MessageDigest

/**
 * A utility class for generating SHA-256 hash values of input strings.
 */
class Hashing
{
    companion object
    {
        /**
         * Computes the SHA-256 hash value of the given input string.
         * @param input the input string to be hashed
         * @return the SHA-256 hash value as a hexadecimal string
         */
        fun sha256(input: String): String
        {
            val bytes = input.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            return digest.fold("") { str, it -> str + "%02x".format(it) }
        }
    }
}