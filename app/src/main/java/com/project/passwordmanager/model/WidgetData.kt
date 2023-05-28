package com.project.passwordmanager.model

import com.project.passwordmanager.security.AlreadyDecryptedException
import com.project.passwordmanager.security.AlreadyEncryptedException
import com.project.passwordmanager.security.Cryptography

class WidgetData private constructor()
{
    companion object
    {
        /**
         * Checks if widget data with the given ID exists.
         *
         * @param appWidgetId The ID of the widget to check.
         * @return true if widget data with the given ID exists, false otherwise.
         */
        fun hasWidgetData(appWidgetId: Int) =
            widgetDataMap.containsKey(appWidgetId)


        /**
         * Creates widget data with the given ID.
         *
         * @param appWidgetId The ID of the widget to create data for.
         */
        fun createWidgetData(appWidgetId: Int)
        {
            widgetDataMap[appWidgetId] = WidgetData()
        }

        /**
         * Deletes widget data with the given ID.
         *
         * @param appWidgetId The ID of the widget to delete data for.
         */
        fun deleteWidgetData(appWidgetId: Int)
        {
            widgetDataMap.remove(appWidgetId)
        }

        /**
         * Gets widget data with the given ID.
         *
         * @param appWidgetId The ID of the widget to get data for.
         * @return The widget data for the given ID.
         */
        fun getWidgetData(appWidgetId: Int): WidgetData =
            widgetDataMap[appWidgetId]!!

        private val widgetDataMap = mutableMapOf<Int, WidgetData>()
        val TAG = WidgetData::class.java

    }

    /**
     * Encrypts the entry with the given ID.
     * This method should not be invoked if the password is
     * already encrypted. If this happens, something in the app logic
     * did not work. Therefore, the thrown exception should
     * rarely be caught.
     *
     * @throws AlreadyEncryptedException
     * @param entryId The ID of the entry to encrypt.
     * @param master The master password used to encrypt the entry's password
     */
    private fun encrypt(entryId: Int, master: String)
    {
        if (entries[entryId].encrypted)
            throw AlreadyEncryptedException("The $entryId-th entry is already encrypted.")

        val encryptedPassword = Cryptography.encryptText(entries[entryId].password, master)
        entries[entryId].encrypted = true
        entries[entryId].password = encryptedPassword
    }

    /**
     * Decrypts the entry with the given ID.
     * This method should not be invoked if the password is
     * already decrypted. If this happens, something in the app logic
     * did not work. Therefore, the thrown exception should
     * rarely be caught.
     *
     * @throws AlreadyDecryptedException
     * @param entryId The ID of the entry to decrypt.
     * @param master The master password used to decrypt the entry's password
     */
    private fun decrypt(entryId: Int, master: String)
    {
        if (!entries[entryId].encrypted)
            throw AlreadyDecryptedException("The $entryId-th entry is already decrypted.")

        val decryptedPassword = Cryptography.decryptText(entries[entryId].password, master)
        entries[entryId].encrypted = false
        entries[entryId].password = decryptedPassword

    }

    /**
     * Checks if the entry with the given ID is encrypted.
     *
     * @param entryId The ID of the entry to check.
     * @return true if the entry is encrypted, false otherwise.
     */
    private fun isEncrypted(entryId: Int) =
        getEntry(entryId).encrypted

    /**
     * Gets the entry with the given ID.
     *
     * @param entryId The ID of the entry to get.
     * @return The entry with the given ID.
     */
    fun getEntry(entryId: Int) =
        entries[entryId]

    /**
     * Removes the entry with the given ID.
     *
     * @param entryId The ID of the entry to remove.
     */
    fun removeEntry(entryId: Int)
    {
        entries.removeAt(entryId)
    }

    /**
     * Adds the entry with the given ID.
     *
     * @param entry The Entry to be added.
     */
    fun addEntry(entry: Entry)
    {
        entries.add(entry)
    }

    /**
     * Returns the number of entries.
     *
     * @return The number of entries.
     */
    fun size() = entries.size

    fun showEntry(entryId: Int)
    {
        entries[entryId].visible = true
    }

    fun hideEntry(entryId: Int)
    {
        entries[entryId].visible = false
    }

    var locked: Boolean = true
        private set
    fun isVisible(entryId: Int) = entries[entryId].visible

    private val entries = mutableListOf<Entry>()

}