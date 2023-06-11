package com.project.passwordmanager.security

/**
 * Signals the failure of a decryption procedure.
 *
 * @param message the message to display
 */
class BadDecryptionException(message:String): Exception(message)