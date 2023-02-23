package uk.co.madmouse.marshal.core.repository

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import uk.co.madmouse.marshal.core.database.EventDao
import uk.co.madmouse.marshal.core.models.database.Event
import java.util.Date

/**
 * As the Event Repo only calls through to the Events Dao these tests currently verify we are calling the
 * correct Dao methods.
 *
 * Note : If any logic is added to the repository the tests need to be added here.
 */
internal class EventRepositoryImplTest {

    @MockK
    var eventListMock = mutableListOf<Event>()

    val eventMock : Event = Event(
        1,
        "Event 1",
        Date().time,
        Date().time,
        "Address",
        "Contact", "Phone"
    )

    @MockK
    private lateinit var eventDaoMock : EventDao

    private lateinit var eventsRepository: EventsRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        eventsRepository = EventRepositoryImpl(eventDaoMock)
    }

    @Test
    fun `fetchEventById verify Event_Dao_fetchEventById is called`() {
        val flow = MutableStateFlow(eventMock)
         coEvery { eventDaoMock.fetchEventById(any()) } returns flow

        val expectedId: Long = 1234
        runBlocking {
            eventsRepository.fetchEventById(expectedId)
        }
        coVerify {
            eventDaoMock.fetchEventById(expectedId)
        }
        confirmVerified(eventDaoMock)
    }

    @Test
    fun `fetchEvent verify Event_Dao_events is called`() {
        val flow = MutableStateFlow(eventListMock)

        coEvery {
            eventDaoMock.events(any())
        }returns flow

        val expectedDate = Date().time
        runBlocking {
            eventsRepository.fetchEvents(expectedDate)
        }
        coVerify {
            eventDaoMock.events(expectedDate)
        }
        confirmVerified(eventDaoMock)
    }

    @Test
    fun `fetchEvent verify Event_Dao_batchInsert is called`() {
        coEvery {
            eventDaoMock.batchInsert(eventMock)
        }coAnswers {1}

        coEvery {
            eventDaoMock.flushOldEvents()
        } coAnswers {0}

        coEvery {
            eventDaoMock.flushOldEvents()
        }coAnswers {1}

        runBlocking {
            eventsRepository.batchInsert(eventMock)
        }
        coVerify {
            eventDaoMock.batchInsert(eventMock)
            eventDaoMock.flushOldEvents()
        }

        confirmVerified(eventDaoMock)
    }
}