package com.example.daystracker.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.daystracker.database.Day
import com.example.daystracker.database.DayDatabaseDao
import kotlinx.coroutines.*

class DayTrackerViewModel(
    val database: DayDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    val days : LiveData<List<Day>> = database.getAllDays()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val _day = Day()

    private val _eventSaveButtonPressed = MutableLiveData<Boolean>()
    val eventSaveButtonPressed: LiveData<Boolean>
        get() = _eventSaveButtonPressed

    init {
        _eventSaveButtonPressed.value = false
    }

    fun onEventSaveButtonPressed() {
        _eventSaveButtonPressed.value = true
    }

    fun saveButtonReset() {
        _eventSaveButtonPressed.value = false
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private suspend fun getDaysFromDatabase(): LiveData<List<Day>> {
        return withContext(Dispatchers.IO) {
            var night = database.getAllDays()
            night
        }
    }

    private suspend fun insert(day: Day) {
        withContext(Dispatchers.IO) {
            database.insert(day)
        }
    }

    fun createDayAndInsert(dayNum: String, description: String) {
        uiScope.launch {
            _day.dayNumber = dayNum
            _day.activityOfDay = description
            insert(_day)
            saveButtonReset()
        }
    }

    fun onDayClicked(){
        //TODO Implement clicked logic
    }
}