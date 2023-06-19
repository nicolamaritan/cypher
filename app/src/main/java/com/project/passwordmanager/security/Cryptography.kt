package com.project.passwordmanager.security

import java.security.MessageDigest
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * The `Cryptography` object provides methods for encrypting and
 * decrypting text using AES encryption.
 */
object Cryptography
{
    /**
     * Encrypts the given text using AES encryption with a key size of 256 bits and
     * returns the encrypted text in base64 encoded format.
     *
     * @param text The text to be encrypted.
     * @param masterPassword The master password used to generate the secret key for encryption.
     * @return The encrypted text in base64 encoded format.
     */
    fun encryptText(text: String, masterPassword: String): String
    {
        val secretKey = generateSecretKey(masterPassword)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encryptedText = cipher.doFinal(text.toByteArray())
        return Base64.getEncoder().encodeToString(iv + encryptedText)
    }

    /**
     * Decrypts the given base64 encoded encrypted text using AES encryption with a
     * key size of 256 bits and returns the original text.
     *
     * @param encryptedText The base64 encoded encrypted text.
     * @param masterPassword The master password used to generate the secret key for decryption.
     * @return The decrypted text.
     * @throws BadDecryptionException if the decryption key for the encrypted text is incorrect.
     */
    fun decryptText(encryptedText: String, masterPassword: String): String
    {
        val secretKey = generateSecretKey(masterPassword)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val decodedText = Base64.getDecoder().decode(encryptedText)
        val iv = decodedText.sliceArray(0..15)
        val text = decodedText.sliceArray(16 until decodedText.size)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))

        try
        {
            return String(cipher.doFinal(text))
        }
        catch (bpe: javax.crypto.BadPaddingException)
        {
            throw BadDecryptionException("Wrong decryption key for the encrypted text.")
        }
    }

    /**
     * Generates a secret key using the SHA-256 algorithm and the given master password.
     *
     * @param masterPassword The master password used to generate the secret key.
     * @return The secret key.
     */
    private fun generateSecretKey(masterPassword: String): SecretKeySpec
    {
        val md = MessageDigest.getInstance("SHA-256")
        val key = md.digest(masterPassword.toByteArray(Charsets.UTF_8))
        return SecretKeySpec(key, "AES")
    }
}