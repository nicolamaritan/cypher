package com.project.passwordmanager.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WidgetCredentialDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(widgetCredential: WidgetCredential)

    @Delete
    suspend fun delete(widgetCredential: WidgetCredential)

    @Query("SELECT * FROM widget_credentials_table WHERE credentialId = :credentialId AND appWidgetId = :appWidgetId")
    fun getWidgetCredential(credentialId: Long, appWidgetId: Int): LiveData<WidgetCredential>

    @Query("SELECT * FROM widget_credentials_table WHERE appWidgetId = :appWidgetId")
    fun getWidgetCredentials(appWidgetId: Int): LiveData<List<WidgetCredential>>

    @Query("SELECT * FROM widget_credentials_table AS wc INNER JOIN credentials_table AS c ON wc.credentialId = c.id WHERE wc.appWidgetId = :appWidgetId")
    fun getWidgetCredentialsJoinCredentials(appWidgetId: Int): LiveData<List<WidgetCredentialWithCredential>>

    @Update
    suspend fun update(widgetCredential: WidgetCredential)
}