package com.project.passwordmanager

import androidx.room.*

@Dao
interface RecordsDao {
    @Insert
    fun insert(record:Records)

    @Update
    fun update(record:Records)

    @Delete
    fun delete(record:Records)

    @Query("SELECT * FROM records_table ORDER BY id ASC")
    fun getAll(): List<Records>
}