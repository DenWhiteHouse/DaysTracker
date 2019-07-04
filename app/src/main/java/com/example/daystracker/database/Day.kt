package com.example.daystracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "days_table")
data class Day(

    @PrimaryKey(autoGenerate = true)
    var dayId: Long = 0L,

    @ColumnInfo(name = "day_number")
    var dayNumber: String = "",

    @ColumnInfo(name = "activity")
    var activityOfDay: String = ""

)