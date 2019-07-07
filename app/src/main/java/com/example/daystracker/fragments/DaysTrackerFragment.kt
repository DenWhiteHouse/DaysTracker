package com.example.daystracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import com.example.daystracker.R
import com.example.daystracker.database.DayDatabase
import com.example.daystracker.databinding.DaysTrackerFragmentBinding
import com.example.daystracker.fragments.decorations.RecyclerItemTouchHelperSimpleCallback
import androidx.recyclerview.widget.ItemTouchHelper

/**
 * Fragment for the starting or title screen of the app
 */
class DaysTrackerFragment : Fragment() {
    lateinit var itemTouchHelperCallback : RecyclerItemTouchHelperSimpleCallback
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

        itemTouchHelperCallback = RecyclerItemTouchHelperSimpleCallback(viewModel, adapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.dayList)

        binding.dayList.adapter = adapter


        viewModel.eventSaveButtonPressed.observe(this, Observer { hasBeenPressed ->
            if (hasBeenPressed) {
                viewModel.createDayAndInsert(binding.dayInput2.text.toString(), binding.activityInput.text.toString())
            }
        })

        return binding.root
    }
}