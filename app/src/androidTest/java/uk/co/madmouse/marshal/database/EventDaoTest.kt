package uk.co.madmouse.marshal.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.co.madmouse.marshal.core.database.EventDao
import uk.co.madmouse.marshal.core.database.MarshalDatabase
import uk.co.madmouse.marshal.core.models.database.Event
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
@SmallTest
class EventDaoTest {
    @JvmField
    @Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: MarshalDatabase

    private lateinit var eventDao: EventDao

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MarshalDatabase::class.java
        ).allowMainThreadQueries().build()

        eventDao = db.eventDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun `WHEN batch_insert single event is inserted THEN verify record inserted`() = runBlocking {

        val startDate = Date()
        val startDateTimeCalendar = Calendar.getInstance()
        startDateTimeCalendar.time = startDate
        startDateTimeCalendar.add(Calendar.HOUR, 2)

        val endDateTimeCalendar = Calendar.getInstance()
        endDateTimeCalendar.time = startDate
        endDateTimeCalendar.add(Calendar.HOUR, 5)
        val eventData = Event(
            1,
            "Event 1",
            startDateTimeCalendar.time.time,
            endDateTimeCalendar.time.time,
            "Address",
            "Contact", "Phone",
            startDate.time,
            startDate.time
        );

        eventDao.batchInsert(eventData)
        eventDao.events(startDate.time).take(3).collect {
            Assert.assertEquals(1, it.size)
            Assert.assertEquals(eventData, it[0])
        }

    }

    @Test
    fun `WHEN batch_insert multiple events are inserted THEN verify records inserted`() = runBlocking {
        val startDate = Date()
        val startDateTimeCalendar = Calendar.getInstance()
        startDateTimeCalendar.time = startDate
        startDateTimeCalendar.add(Calendar.HOUR, 2)

        val endDateTimeCalendar = Calendar.getInstance()
        endDateTimeCalendar.time = startDate
        endDateTimeCalendar.add(Calendar.HOUR, 5)

        val eventList = ArrayList<Event>()
        eventList.add(
            Event(
                1,
                "Event 1",
                startDateTimeCalendar.time.time,
                endDateTimeCalendar.time.time,
                "Address",
                "Contact", "Phone",
                startDate.time,
                startDate.time
            )
        )
        eventList.add(
            Event(
                2,
                "Event 1",
                startDateTimeCalendar.time.time,
                endDateTimeCalendar.time.time,
                "Address",
                "Contact", "Phone",
                startDate.time,
                startDate.time
            )
        )

        eventDao.batchInsert(*eventList.toTypedArray())
        eventDao.events(startDate.time).take(3).collect {
            Assert.assertEquals(eventList.size, it.size)
            Assert.assertEquals(eventList[0], it[0])
        }

    }

    @Test
    fun `WHEN flushOldEvents is called THEN records that expired are deleted`() = runBlocking {

        val startDate = Date()
        val startDateTimeCalendar = Calendar.getInstance()
        startDateTimeCalendar.time = startDate
        startDateTimeCalendar.add(Calendar.HOUR, 2)

        val endDateTimeCalendar = Calendar.getInstance()
        endDateTimeCalendar.time = startDate
        endDateTimeCalendar.add(Calendar.HOUR, 5)

        val eventList = ArrayList<Event>()
        eventList.add(
            Event(
                1,
                "Event 1",
                startDateTimeCalendar.time.time,
                endDateTimeCalendar.time.time,
                "Address",
                "Contact", "Phone",
                startDate.time,
                startDate.time
            )
        )

        startDateTimeCalendar.add(Calendar.HOUR, -25)
        endDateTimeCalendar.add(Calendar.HOUR, -24)
        eventList.add(
            Event(
                2,
                "Event 1",
                startDateTimeCalendar.time.time,
                endDateTimeCalendar.time.time,
                "Address",
                "Contact", "Phone",
                startDate.time,
                startDate.time
            )
        )

        // Verify insert
        eventDao.batchInsert(*eventList.toTypedArray())
        eventDao.events(Long.MAX_VALUE).take(3).collect {
            Assert.assertEquals(eventList.size, it.size)
//            Assert.assertEquals(eventList[0], it[1])
//            Assert.assertEquals(eventList[1], it[0])
            // Flush Events
            eventDao.flushOldEvents().apply {
                // Verify delete
                eventDao.batchInsert(*eventList.toTypedArray())
                eventDao.events(Long.MAX_VALUE).take(3).collect {
                    Assert.assertEquals(1, it.size)
                    Assert.assertEquals(eventList[0], it[0])
                }
            }
        }


    }
}