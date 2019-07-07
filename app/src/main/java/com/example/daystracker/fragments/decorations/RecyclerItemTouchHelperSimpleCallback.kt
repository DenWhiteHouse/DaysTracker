package com.example.daystracker.fragments.decorations

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.daystracker.fragments.DayAdapter
import com.example.daystracker.fragments.DayTrackerViewModel

class RecyclerItemTouchHelperSimpleCallback(var viewModel: DayTrackerViewModel, var adapter : DayAdapter) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ){

    lateinit private var _adapter : DayAdapter
    lateinit private var _viewModel : DayTrackerViewModel

    init {
        _adapter = adapter
        _viewModel = viewModel
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        var day =_adapter.getItem(viewHolder.adapterPosition)
        _viewModel.deleteDay(day.dayId)
        _adapter.notifyDataSetChanged()
    }
}