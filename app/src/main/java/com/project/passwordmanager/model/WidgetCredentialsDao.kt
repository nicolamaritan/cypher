package com.project.passwordmanager.model

import androidx.room.*

@Dao
interface WidgetCredentialDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(widgetCredential: WidgetCredential)

    @Delete
    suspend fun delete(widgetCredential: WidgetCredential)

    @Query("SELECT * FROM widget_credentials_table WHERE credentialId = :credentialId AND appWidgetId = :appWidgetId")
    suspend fun getWidgetCredential(credentialId: Long, appWidgetId: Int): WidgetCredential?

    @Query("SELECT * FROM widget_credentials_table WHERE appWidgetId = :appWidgetId")
    suspend fun getWidgetCredentialsByAppWidgetId(appWidgetId: Int): List<WidgetCredential>

    @Update
    suspend fun update(widgetCredential: WidgetCredential)
}