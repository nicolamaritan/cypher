
package com.project.passwordmanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This class represents the credentials of an account.
 * It is used to store information such as id, service, username, and password.
 */
@Entity(tableName = "credentials_table")
data class Credential(

    @PrimaryKey(autoGenerate = true) // Auto-increment
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "Service")
    var service: String = "",

    @ColumnInfo(name = "Username")
    var username: String = "",

    @ColumnInfo(name = "Password")
    var password: String = ""
)