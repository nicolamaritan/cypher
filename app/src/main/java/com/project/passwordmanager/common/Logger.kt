package com.project.passwordmanager.common

import android.util.Log

/**
 * Utility class to log simple formatted Logcat messages.
 */
object Logger
{
    /**
     * Logs the invocation of a callback function for a specified appWidgetId.
     * @param callback The name of the callback function being invoked.
     * @param appWidgetId The ID of the app widget for which the callback is being invoked.
     */
    fun logCallback(TAG: String?, callback: String, appWidgetId: Int) =
        Log.d(TAG, "Invoked $callback. AppWidgetId: $appWidgetId")

    /**
     * Logs the invocation of a callback function for a specified class.
     * @param callback The name of the callback function being invoked.
     * @param fromClass The class for which the callback is being invoked.
     */
    fun logCallback(TAG: String?, callback: String, fromClass: String) =
        Log.d(TAG, "Invoked $callback. Class: $fromClass")
}