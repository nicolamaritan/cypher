package com.project.passwordmanager.common

import android.content.Context

/**
 * Manages the preferences for a widget.
 *
 * @param context The context in which the widget preferences are managed.
 * @param appWidgetId The ID of the app widget.
 */
class WidgetPreferencesManager(val context: Context, val appWidgetId: Int) {

    /**
     * Retrieves the list of added IDs from the widget preferences.
     *
     * @return The list of added IDs.
     */
    fun getAddedIds(): List<Long> {
        // Retrieves the added IDs from the shared preferences
        val widgetPreferences = context.getSharedPreferences(
            Constants.WIDGET_PREFERENCES + appWidgetId,
            Context.MODE_PRIVATE
        )
        val addedIdsPreferences = widgetPreferences.getString(
            Constants.WIDGET_ADDED_IDS,
            ""
        ) ?: ""
        return addedIdsPreferences.split(",").mapNotNull { it.toLongOrNull() }
    }

    /**
     * Retrieves the name of the widget from the preferences.
     *
     * @return The name of the widget.
     */
    fun getName(): String {
        val widgetPreferences = context.getSharedPreferences(
            Constants.WIDGET_PREFERENCES + appWidgetId,
            Context.MODE_PRIVATE
        )
        return widgetPreferences.getString(Constants.WIDGET_NAME, "")!!
    }

    /**
     * Clears all the preferences for the widget.
     */
    fun clear() {
        context.getSharedPreferences(
            Constants.WIDGET_PREFERENCES + appWidgetId,
            0
        ).edit().clear().apply()
    }

    /**
     * Inserts the name of the widget into the preferences.
     *
     * @param name The name to be inserted.
     */
    fun insertName(name: String) {
        val widgetPreferences = context.getSharedPreferences(
            Constants.WIDGET_PREFERENCES + appWidgetId,
            Context.MODE_PRIVATE
        )
        val editor = widgetPreferences.edit()
        editor.putString(Constants.WIDGET_NAME, name)
        editor.apply()
    }

    /**
     * Inserts the list of added IDs into the preferences.
     *
     * @param ids The list of IDs to be inserted.
     */
    fun insertAddedIds(ids: List<Long>) {
        val widgetPreferences = context.getSharedPreferences(
            Constants.WIDGET_PREFERENCES + appWidgetId,
            Context.MODE_PRIVATE
        )
        val editor = widgetPreferences.edit()
        val toBeAddedIdsPreferences = ids.joinToString(",")
        editor.putString(Constants.WIDGET_ADDED_IDS, toBeAddedIdsPreferences)
        editor.apply()
    }
}
