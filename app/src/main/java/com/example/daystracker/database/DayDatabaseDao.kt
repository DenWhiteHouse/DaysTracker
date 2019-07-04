package com.example.daystracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DayDatabaseDao{

    @Insert
    fun insert(day: Day)

    @Update
    fun update(day: Day)

    @Query("SELECT * FROM days_table WHERE dayId = :key")
    fun get(key: Long): Day

    @Query("DELETE FROM days_table")
    fun clear()

    @Query("SELECT * FROM days_table ORDER BY dayId DESC")
    fun getAllDays(): LiveData<List<Day>>

    @Query("SELECT * from days_table WHERE dayId = :key")
    fun getDayWithId(key: Long): LiveData<Day>


}