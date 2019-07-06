package com.example.daystracker.fragments

import android.app.Application
import android.renderscript.ScriptGroup
import android.view.View
import androidx.lifecycle.*
import com.example.daystracker.database.Day
import com.example.daystracker.database.DayDatabaseDao
import com.example.daystracker.databinding.DaysTrackerFragmentBinding
import kotlinx.coroutines.*

class DayTrackerViewModel(
    val database: DayDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var days = database.getAllDays()

    private val _eventSaveButtonPressed = MutableLiveData<Boolean>()
    val eventSaveButtonPressed: LiveData<Boolean>
        get() = _eventSaveButtonPressed


    fun onEventSaveButtonPressed() {
        _eventSaveButtonPressed.value = true
    }

    fun saveButtonReset() {
        _eventSaveButtonPressed.value = false
    }


    init{
        _eventSaveButtonPressed.value = false
    }


    private suspend fun insert(day: Day) {
        withContext(Dispatchers.IO) {
            database.insert(day)
        }
    }

    fun createDayAndInsert(dayNumber: String,activity:String) {
        val _day = Day()
        uiScope.launch {
            _day.dayNumber = dayNumber
            _day.activityOfDay = activity
            insert(_day)
            saveButtonReset()
        }
    }

    fun onDayClicked(){
        //TODO Implement clicked logic
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}