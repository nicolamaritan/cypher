package com.project.passwordmanager.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CredentialDao{
    @Insert
    suspend fun insert(credential: Credential)

    @Update
    suspend fun update(credential: Credential)

    @Delete
    suspend fun delete(credential: Credential)

    @Query("SELECT * FROM credentials_table ORDER BY id ASC")
    fun getAll(): LiveData<List<Credential>>
}