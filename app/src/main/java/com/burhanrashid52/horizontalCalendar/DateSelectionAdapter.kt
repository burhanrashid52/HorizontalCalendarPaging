package com.burhanrashid52.horizontalCalendar

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.burhanrashid52.horizontalCalendar.databinding.RowDateDetailsBinding

class DateSelectionAdapter(private val onDateSelection: (DateDetailsUI) -> Unit) :
    PagingDataAdapter<DateDetailsUI, DateSelectionAdapter.DateViewHolder>(dateDifferentiators) {

    private val newWidth = Resources.getSystem().displayMetrics.widthPixels / 7

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = RowDateDetailsBinding.inflate(LayoutInflater.from(parent.context))
        return DateViewHolder(view)
    }

    inner class DateViewHolder(private val binding: RowDateDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dateDetailsUI: DateDetailsUI) {
            binding.txtDay.text = dateDetailsUI.day.toString()
            binding.txtDayName.text = dateDetailsUI.dayOfWeek.substring(0, 3)
            binding.txtMonthName.text = dateDetailsUI.monthName.substring(0, 3)
            binding.txtYear.text = dateDetailsUI.year.toString()
            binding.txtYear.text = dateDetailsUI.year.toString()
            binding.pbDay.max = dateDetailsUI.maxCalories
            binding.pbDay.progress = dateDetailsUI.inTakeCalories
            itemView.setOnClickListener {
                onDateSelection.invoke(dateDetailsUI)
            }
            val params = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            params.width = newWidth
            itemView.layoutParams = params
        }
    }
}

val dateDifferentiators = object : DiffUtil.ItemCallback<DateDetailsUI>() {

    override fun areItemsTheSame(oldItem: DateDetailsUI, newItem: DateDetailsUI): Boolean {
        return oldItem.dateKey == newItem.dateKey
    }

    override fun areContentsTheSame(oldItem: DateDetailsUI, newItem: DateDetailsUI): Boolean {
        return oldItem == newItem
    }
}