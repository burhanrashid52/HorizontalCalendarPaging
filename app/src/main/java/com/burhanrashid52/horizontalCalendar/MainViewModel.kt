package com.burhanrashid52.horizontalCalendar

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import java.util.*

/**
 * Created by Burhanuddin Rashid on 2/1/21.
 * @author  <https://github.com/burhanrashid52>
 */
class MainViewModel : ViewModel() {

    private val _selectedDate = MutableLiveData(Date().toStringFormat())

    val selectedDate: LiveData<String>
        get() = _selectedDate

    private val dateSelectionPager =
        Transformations.switchMap(selectedDate) { changedSelectedDate ->
            Pager(PagingConfig(pageSize = 10)) {
                DateSelectionPageSource(changedSelectedDate)
            }.liveData.cachedIn(viewModelScope)
        }

}