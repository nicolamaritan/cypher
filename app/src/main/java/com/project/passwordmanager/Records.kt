package com.project.passwordmanager

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records_table")
data class Records(

    @PrimaryKey(autoGenerate = true) //auto-increment
    var id:Long = 0L,

    @ColumnInfo(name = "Service")
    var service:String = "",

    @ColumnInfo(name = "Username")
    var username:String = "",

    @ColumnInfo(name = "Password")
    var password:String = "",

    )