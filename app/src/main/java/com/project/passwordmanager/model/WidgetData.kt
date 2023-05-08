package com.project.passwordmanager.model

class WidgetData private constructor()
{
    companion object
    {
        fun hasWidgetData(appWidgetId: Int) =
            widgetDataMap.containsKey(appWidgetId)

        fun createWidgetData(appWidgetId: Int)
        {
            widgetDataMap[appWidgetId] = WidgetData()
        }

        fun deleteWidgetData(appWidgetId: Int) =
            widgetDataMap.remove(appWidgetId)

        fun getWidgetData(appWidgetId: Int) =
            widgetDataMap[appWidgetId]

        private val widgetDataMap = mutableMapOf<Int, WidgetData>()
        val TAG = WidgetData::class.java
    }

    fun encrypt(entryId: Int)
    {

    }

    fun decrypt(entryId: Int)
    {

    }

    fun isEncrypted(entryId: Int)
    {

    }

    fun getEntry(entryId: Int) = entries[entryId]
    fun removeEntry(entryId: Int) = entries.removeAt(entryId)


    private val entries = mutableListOf<Entry>()




}