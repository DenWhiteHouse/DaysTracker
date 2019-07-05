package com.example.daystracker.fragments

import android.app.Application
import android.renderscript.ScriptGroup
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.daystracker.database.Day
import com.example.daystracker.database.DayDatabaseDao
import com.example.daystracker.databinding.DaysTrackerFragmentBinding
import kotlinx.coroutines.*

class DayTrackerViewModel(
    val database: DayDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    lateinit var days : LiveData<List<Day>>


    private val _day = Day()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

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
        days = database.getAllDays()
    }

    private suspend fun getDaysFromDatabase(): LiveData<List<Day>> {
        return withContext(Dispatchers.IO) {
            var dayslist = database.getAllDays()
            dayslist
        }
    }

    private suspend fun insert(day: Day) {
        withContext(Dispatchers.IO) {
            database.insert(day)
        }
    }

    fun createDayAndInsert(dayNumber: String,activity:String) {
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