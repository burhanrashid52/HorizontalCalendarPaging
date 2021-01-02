package com.burhanrashid52.horizontalCalendar

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.burhanrashid52.horizontalCalendar.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dateSelectionAdapter = DateSelectionAdapter { dateDetailsUI ->
            setSelectedDate(dateDetailsUI)
        }

        binding.btnPickDate.setOnClickListener {
            selectDateFromDatePickerDialog(mainViewModel.selectedDate.value!!)
        }

        val rvDates = binding.rvDates
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rvDates)
        rvDates.adapter = dateSelectionAdapter

        mainViewModel.dateDetailsList.observe(this) { integerPagingData ->
            dateSelectionAdapter.submitData(lifecycle, integerPagingData)
        }

        mainViewModel.selectedDate.observe(this) { selectedDate ->
            val dateDetailsUI = selectedDate.toDate()?.toDateDetails()
            dateDetailsUI?.let {
                setSelectedDate(it)
            }
        }

        mainViewModel.resetDateList.observe(this) { timeInMillis ->
            if (timeInMillis != null) {
                dateSelectionAdapter.submitData(lifecycle, PagingData.empty())

            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setSelectedDate(dateDetailsUI: DateDetailsUI) {
        binding.txtSelectedDate.text = """
                    ${dateDetailsUI.dayOfWeek}
                    ${dateDetailsUI.day} ${dateDetailsUI.monthName}
                    ${dateDetailsUI.year}
                """.trimIndent()
    }

    private fun selectDateFromDatePickerDialog(currentSelectedDate: String) {
        val cal = Calendar.getInstance()
        cal.time = currentSelectedDate.toDate()!!
        val day = cal[Calendar.DAY_OF_MONTH]
        val month = cal[Calendar.MONTH]
        val year = cal[Calendar.YEAR]

        val listener =
            DatePickerDialog.OnDateSetListener { _, selectedYear: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = getFormatDate(dayOfMonth, monthOfYear + 1, selectedYear)
                mainViewModel.updateCurrentSelectedDate(selectedDate, true)
            }
        val dpDialog = DatePickerDialog(this, listener, year, month, day)
        dpDialog.datePicker.maxDate = maxDate.time
        dpDialog.datePicker.minDate = minDate.time
        dpDialog.show()
    }
}