package com.project.passwordmanager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Records::class], version = 1, exportSchema = false)
abstract class RecordsDatabase : RoomDatabase(){
    abstract val recordsDao:RecordsDao

    companion object{
        @Volatile
        private var INSTANCE: RecordsDatabase? = null

        fun getInstance(context: Context) : RecordsDatabase{  //returns an instance of RecordsDatabase. It builds the database if it doesn't exit yet
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RecordsDatabase::class.java,
                        "records_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}