package com.project.passwordmanager.common

import com.project.passwordmanager.model.Entry
import com.project.passwordmanager.model.WidgetData

/**
 * ToyDataWidgetDataInitializer is a utility object for initializing widget data for a password manager app.
 * It provides a method to initialize widget data for a specific app widget ID.
 * The widget data contains entries with toy services, users, and passwords.
 */
object ToyDataWidgetDataInitializer
{
    /**
     * Initializes the widget data for the specified app widget ID.
     * If the widget data already exists for the app widget ID, the method returns without making any changes.
     *
     * @param appWidgetId The ID of the app widget to initialize the data for.
     */
    fun initialize(appWidgetId: Int)
    {
        if (!WidgetData.hasWidgetData(appWidgetId))
            return

        val widgetData = WidgetData.getWidgetData(appWidgetId)

        val services = getToyServices()
        val users = getToyUsers()
        val passwords = getToyPasswords()

        // Iterate the lists
        for (i in services.indices)
        {
            val entry = Entry(services[i], users[i], passwords[i])
            widgetData.addEntry(entry)
        }
    }

    /**
     * Returns a list of toy services.
     *
     * @return The list of toy services.
     */
    private fun getToyServices() =
        listOf(
            "Google",
            "GitHub",
            "Facebook",
            "Facebook",
            "iCloud",
            "Steam",
            "Google",
            "Google"
        )

    /**
     * Returns a list of toy users.
     *
     * @return The list of toy users.
     */
    private fun getToyUsers() =
        listOf(
            "Mario Rossi",
            "Luigi Verdi",
            "Anna Russo",
            "Francesco Martini",
            "Laura Ferrari",
            "Giovanni Ricci",
            "Sara Conti",
            "Alessandro Moretti"
        )

    /**
     * Returns a list of toy passwords.
     *
     * @return The list of toy passwords.
     */
    private fun getToyPasswords() =
        listOf(
            "password",
            "qwerty",
            "a%DSnCSpP?!",
            "1234",
            "let me in",
            "1234",
            "G1$%/pUn",
            "sono io"
        )
}