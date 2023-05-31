package com.project.passwordmanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "widget_credentials_table",
    foreignKeys = [ForeignKey(
        entity = Credential::class,
        parentColumns = ["id"],
        childColumns = ["credentialId"],
        onDelete = ForeignKey.CASCADE
    )],
    primaryKeys = ["appWidgetId", "credentialId"]
)
data class WidgetCredential(
    @ColumnInfo(name = "appWidgetId")
    var appWidgetId: Int,

    @ColumnInfo(name = "credentialId")
    var credentialId: Long,

    @ColumnInfo(name = "encrypted")
    var encrypted: Boolean = true,

    @ColumnInfo(name = "visible")
    var visible: Boolean = false,

    @ColumnInfo(name = "shownPassword")
    var shownPassword: String = "******"
)