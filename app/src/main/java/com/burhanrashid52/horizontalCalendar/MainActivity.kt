package com.burhanrashid52.horizontalCalendar

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.burhanrashid52.horizontalCalendar.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val dateSelectionAdapter = DateSelectionAdapter { dateKey ->
            // mainViewModel.updateCurrentSelectedDate(dateKey)
        }

        val rvDates = binding.rvDates
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rvDates)
        rvDates.adapter = dateSelectionAdapter

        mainViewModel.dateDetailsList.observe(this) { integerPagingData ->
            dateSelectionAdapter.submitData(lifecycle, integerPagingData)
        }
    }
}