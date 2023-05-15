package com.project.passwordmanager

import androidx.room.*

@Dao
interface CredentialDao{
    @Insert
    suspend fun insert(credential:Credential)

    @Update
    suspend fun update(credential:Credential)

    @Delete
    suspend fun delete(credential:Credential)

    @Query("SELECT * FROM records_table ORDER BY id ASC")
    fun getAll(): List<Credential>
}