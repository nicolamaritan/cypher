package com.project.passwordmanager.model

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
     *
     * @param entryId The ID of the entry to encrypt.
     */
    fun encrypt(entryId: Int)
    {

    }

    /**
     * Decrypts the entry with the given ID.
     *
     * @param entryId The ID of the entry to decrypt.
     */
    fun decrypt(entryId: Int)
    {

    }

    /**
     * Checks if the entry with the given ID is encrypted.
     *
     * @param entryId The ID of the entry to check.
     * @return true if the entry is encrypted, false otherwise.
     */
    fun isEncrypted(entryId: Int) =
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

    fun addEntry(entry: Entry)
    {
        entries.add(entry)
    }

    fun size() = entries.size

    private val entries = mutableListOf<Entry>()




}