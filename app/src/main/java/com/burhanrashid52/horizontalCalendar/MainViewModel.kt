package com.burhanrashid52.horizontalCalendar

import androidx.lifecycle.*
import androidx.paging.*
import java.util.*

/**
 * Created by Burhanuddin Rashid on 2/1/21.
 * @author  <https://github.com/burhanrashid52>
 */
class MainViewModel : ViewModel() {

    private val _selectedDate = MutableLiveData(Date().toStringFormat())

    val selectedDate: LiveData<String>
        get() = _selectedDate

    private val dateSelectionPager: LiveData<PagingData<Date>> =
        Transformations.switchMap(selectedDate) { changedSelectedDate ->
            Pager(PagingConfig(pageSize = 10)) {
                DateSelectionPageSource(changedSelectedDate)
            }.liveData.cachedIn(viewModelScope)
        }

    val dateDetailsList = dateSelectionPager.map { pagingData ->
        pagingData.map { mealDate ->
            mealDate.toDateDetails()
        }
    }

    private val _resetDateList = MutableLiveData<Long>()

    val resetDateList: LiveData<Long>
        get() = _resetDateList

    @JvmOverloads
    fun updateCurrentSelectedDate(date: String, invalidateList: Boolean = false) {
        if (invalidateList) {
            _resetDateList.value = System.currentTimeMillis()
        }
        _selectedDate.value = date
    }
}