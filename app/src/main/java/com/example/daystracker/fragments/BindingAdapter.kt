package com.example.daystracker.fragments

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.daystracker.database.Day

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Day>?){
    val adapter = recyclerView.adapter as DayAdapter
    adapter.submitList(data)
}