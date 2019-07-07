package com.example.daystracker.fragments


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.daystracker.database.Day
import com.example.daystracker.databinding.DayItemBinding


class DayAdapter(val clickListener: DayListener): ListAdapter<Day, DayAdapter.ViewHolder>(DayDiffCallBack()){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,clickListener)
    }

    public override fun getItem(position: Int): Day {
        return super.getItem(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: DayItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Day, clickListener: DayListener) {
            binding.day = item
            binding.dayTextView.setText(item.dayNumber)
            binding.descriptionTextView2.setText(item.activityOfDay)
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DayItemBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }

    class DayDiffCallBack: DiffUtil.ItemCallback<Day>() {
        override fun areItemsTheSame(oldItem: Day, newItem: Day): Boolean {
            return oldItem.dayId == newItem.dayId
        }

        override fun areContentsTheSame(oldItem: Day, newItem: Day): Boolean {
            return oldItem == newItem
        }
    }

    class DayListener(val clickListener: (dayId: Long) -> Unit){
        fun onClick(day: Day) = clickListener(day.dayId)
    }

}