package com.example.daystracker

import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.daystracker.database.DayDatabase
import com.example.daystracker.database.DayDatabaseDao
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.daystracker.database.Day
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DayDatabaseTest {

    private lateinit var dayDao: DayDatabaseDao
    private lateinit var db: DayDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, DayDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        dayDao = db.dayDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetDay() {
        val day = Day()
        day.dayNumber = "2"
        dayDao.insert(day)
        val today = dayDao.getToday()
        assertEquals(day.dayNumber,"2")
    }

}