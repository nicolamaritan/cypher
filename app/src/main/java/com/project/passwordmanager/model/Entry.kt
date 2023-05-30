package com.project.passwordmanager.model

/**
 * This data class represents an entry in the password manager.
 * It holds information such as the service, username, password, and encryption status.
 *
 * @property service The service associated with the entry.
 * @property username The username associated with the entry.
 * @property password The password associated with the entry.
 * @property encrypted The encryption status of the entry. Default value is true.
 */
data class Entry(
    var service: String,
    var username: String,
    var password: String,
    var encrypted: Boolean = true
)
