package com.project.passwordmanager

import com.project.passwordmanager.security.BadDecryptionException
import com.project.passwordmanager.security.Cryptography
import org.junit.Test

import org.junit.Assert.*

class SecurityUnitTest
{
    @Test
    fun encryptionAndDecryption1()
    {
        val masterPassword = "Master"
        val text = "Hello World!"
        val encryptedText = Cryptography.encryptText(text, masterPassword)
        val decryptedText = Cryptography.decryptText(encryptedText, masterPassword)

        assertEquals(text, decryptedText)
    }

    @Test
    fun encryptionAndDecryption2()
    {
        val masterPassword = "H9ivKVbNjnWeFaA"
        val text = "The Linux kernel is a free and open-source, monolithic, modular," +
                " multitasking, Unix-like operating system kernel. It was originally" +
                " authored in 1991 by Linus Torvalds for his i386-based PC, and" +
                " it was soon adopted as the kernel for the GNU operating system," +
                " which was written to be a free (libre) replacement for Unix."
        val encryptedText = Cryptography.encryptText(text, masterPassword)
        val decryptedText = Cryptography.decryptText(encryptedText, masterPassword)

        assertEquals(text, decryptedText)
    }

    @Test
    fun encryptionAndDecryption3()
    {
        val fullMasterPassword = "DB7hdeKXfp2Ifq2"
        val text = "The quick brown fox jumps over a lazy dog"
        for (i in 1..fullMasterPassword.length)
        {
            // Tests different sizes of the master password
            val localMasterPassword = fullMasterPassword.substring(0, i)
            val encryptedText = Cryptography.encryptText(text, localMasterPassword)
            val decryptedText = Cryptography.decryptText(encryptedText, localMasterPassword)
            assertEquals(text, decryptedText)
        }
    }

    @Test
    fun layeredEncryption1()
    {
        val fullMasterPassword = "yXwqawVLViescAc"
        val text = "Android is a mobile operating system based on a" +
                "modified version of the Linux kernel and other open-source" +
                "software, designed primarily for touchscreen mobile" +
                "devices such as smartphones and tablets."

        var encryptedText = text
        for (i in 0.. 10)
        {
            encryptedText = Cryptography.encryptText(encryptedText, fullMasterPassword)
        }

        var decryptedText = encryptedText
        for (i in 0.. 10)
        {
            decryptedText = Cryptography.decryptText(decryptedText, fullMasterPassword)
        }
        assertEquals(text, decryptedText)
    }

    @Test
    fun layeredEncryption2()
    {
        val fullMasterPassword = "H9ivKVbNjnWeFaA"
        val text = "The Advanced Encryption Standard (AES), also known by its" +
                "original name Rijndael is a specification for the encryption" +
                " of electronic data established by the U.S. National Institute" +
                " of Standards and Technology (NIST) in 2001"

        var encryptedText = text
        for (i in 1.. fullMasterPassword.length)
        {
            // Password start from 1 character then it becomes full
            val localMasterPassword = fullMasterPassword.substring(0, i)
            encryptedText = Cryptography.encryptText(encryptedText, localMasterPassword)
        }

        var decryptedText = encryptedText
        for (i in 1.. fullMasterPassword.length)
        {
            // Password starts from full then it becomes of one char
            val localMasterPassword = fullMasterPassword.substring(0, fullMasterPassword.length-i+1)
            decryptedText = Cryptography.decryptText(decryptedText, localMasterPassword)
        }
        assertEquals(text, decryptedText)
    }

    @Test(expected = BadDecryptionException::class)
    fun wrongDecryption1()
    {
        val masterPassword = "Master"
        val text = "Hello World!"
        val encryptedText = Cryptography.encryptText(text, masterPassword)
        Cryptography.decryptText(encryptedText, "MASTER")
    }

    @Test(expected = BadDecryptionException::class)
    fun wrongDecryption2()
    {
        val masterPassword = "Master"
        val text = "Hello World!"
        val encryptedText = Cryptography.encryptText(text, masterPassword)
        Cryptography.decryptText(encryptedText, "trivial password")
    }
}