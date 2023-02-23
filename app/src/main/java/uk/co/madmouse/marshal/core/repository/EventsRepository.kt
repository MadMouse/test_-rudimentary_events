package uk.co.madmouse.marshal.core.repository

import kotlinx.coroutines.flow.Flow
import uk.co.madmouse.marshal.core.models.database.Event
import java.util.*

interface EventsRepository {
    fun fetchEvents(fromDate: Long = Date().time): Flow<List<Event>>
    suspend fun fetchEventById(id:Long): Flow<Event>
    suspend fun batchInsert(vararg events: Event)
    suspend fun Insert(events: Event)
}