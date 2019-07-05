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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daystracker.R
import com.example.daystracker.database.DayDatabase
import com.example.daystracker.databinding.DaysTrackerFragmentBinding

/**
 * Fragment for the starting or title screen of the app
 */
class DaysTrackerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        val binding: DaysTrackerFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.days_tracker_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = DayDatabase.getInstance(application).dayDatabaseDao
        val viewModelFactory = DayTrackerViewModelFactory(dataSource, application)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(DayTrackerViewModel::class.java)

        binding.setLifecycleOwner(this)

        binding.viewModel = viewModel

        val adapter = DayAdapter(DayAdapter.DayListener { _ ->
            viewModel.onDayClicked()
        })

        binding.dayList.adapter = adapter

        // Implemented with BindingAdapter
//        viewModel.days.observe(viewLifecycleOwner, Observer {
//            it?.let{
//                adapter.submitList(it)
//            }
//        })


        binding.saveButton.setOnClickListener { _ ->
            viewModel.onEventSaveButtonPressed()
        }

        viewModel.eventSaveButtonPressed.observe(this, Observer { hasBeenPressed ->
            if (hasBeenPressed) {
                viewModel.createDayAndInsert(binding.dayInput2.text.toString(), binding.activityInput.text.toString())
            }
        })

        return binding.root
    }
}