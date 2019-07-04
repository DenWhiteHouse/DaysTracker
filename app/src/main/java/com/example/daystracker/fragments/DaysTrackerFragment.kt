package com.example.daystracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.daystracker.R
import com.example.daystracker.databinding.DaysTrackerFragmentBinding

/**
 * Fragment for the starting or title screen of the app
 */
class DaysTrackerFragment : Fragment() {
    private lateinit var viewModel: DayTrackerViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        // Get the viewmodel
        viewModel = ViewModelProviders.of(this).get(DayTrackerViewModel::class.java)

        // Inflate the layout for this fragment
        val binding: DaysTrackerFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.days_tracker_fragment, container, false)

        binding.saveButton.setOnClickListener { _ ->
            onSaveButtonPressed()
        }

        viewModel.eventSaveButtonPressed.observe(this, Observer { hasBeenPressed ->
            if(hasBeenPressed){
                Toast.makeText(context,binding.activityInput.text,Toast.LENGTH_SHORT).show()
                viewModel.saveButtonReset()
            }
        })

        return binding.root
    }

    fun onSaveButtonPressed(){
        viewModel.onEventSaveButtonPressed()
    }
}