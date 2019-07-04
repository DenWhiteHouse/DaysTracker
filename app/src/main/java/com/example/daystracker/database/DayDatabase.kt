package com.example.daystracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Day::class],version = 1, exportSchema = false)
abstract class DayDatabase : RoomDatabase(){

    abstract val dayDatabaseDao: DayDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: DayDatabase? = null

        fun getInstance(context: Context) : DayDatabase {
            // sync ensures only 1 thread can work on the DB per time
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DayDatabase::class.java,
                        "day_tracker"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}