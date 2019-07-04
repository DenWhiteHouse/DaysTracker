package com.example.daystracker.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DayTrackerViewModel : ViewModel(){

    private val _day = MutableLiveData<Int>()
    val day : LiveData<Int>
        get() = _day

    private val _activity = MutableLiveData<String>()
    val activity : LiveData<String>
        get() = _activity

    private val _eventSaveButtonPressed = MutableLiveData<Boolean>()
    val eventSaveButtonPressed: LiveData<Boolean>
        get()=_eventSaveButtonPressed

    init {
        _eventSaveButtonPressed.value = false
        }

    fun onEventSaveButtonPressed(){
        _eventSaveButtonPressed.value = true
    }

    fun saveButtonReset(){
        _eventSaveButtonPressed.value = false
    }
}