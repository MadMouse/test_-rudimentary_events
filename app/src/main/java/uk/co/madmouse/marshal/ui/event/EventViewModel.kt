package uk.co.madmouse.marshal.ui.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.co.madmouse.marshal.core.models.database.Event
import uk.co.madmouse.marshal.core.network.ApiService
import uk.co.madmouse.marshal.core.repository.EventsRepository
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventsRepository: EventsRepository,
    private val apiService: ApiService
) : ViewModel() {

    private var _liveDataEventList = MutableStateFlow<List<Event>>(emptyList())
    val eventsList: StateFlow<List<Event?>> = _liveDataEventList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            eventsRepository.fetchEvents(Date().time).collect() {
                if(it.size > 0) {
                    _liveDataEventList.value = it
                }
            }
        }
    }

    fun fetchFromDB(){
        viewModelScope.launch(Dispatchers.IO) {
            eventsRepository.Insert(
                Event(
                    2,
                    "Event 1",
                    1,
                    1,
                    "Address",
                    "Contact", "Phone"
                )
            )
        }
    }

    fun loadFromWeb() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiService.getEventsByDate(Date().time)
            if (response.code() == 200) {
                response.body().let {
                    if(it?.size!! > 0) {
                        eventsRepository.batchInsert(*it!!.toTypedArray())
                    }
                }
            }
        }
    }
}