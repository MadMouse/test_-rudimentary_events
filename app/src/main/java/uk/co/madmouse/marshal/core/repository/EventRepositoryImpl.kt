package uk.co.madmouse.marshal.core.repository

import kotlinx.coroutines.flow.Flow
import uk.co.madmouse.marshal.core.database.EventDao
import uk.co.madmouse.marshal.core.models.database.Event
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Events repository used to store event locally breakdown.
 */
@Singleton
class EventRepositoryImpl @Inject constructor(private val eventDao: EventDao) : EventsRepository {
    override fun fetchEvents(fromDate: Long): Flow<List<Event>> {
        return eventDao.events(fromDate)
    }

    override suspend fun fetchEventById(id: Long): Flow<Event> {
        return eventDao.fetchEventById(id)
    }

    /**
     * Flush old events after inserting/Replacing new events.
     */
    override suspend fun batchInsert(vararg events: Event) {
        eventDao.batchInsert(*events).apply {
            eventDao.flushOldEvents();
        }
    }

    override suspend fun Insert(event: Event) {
        eventDao.Insert(event)
    }
}