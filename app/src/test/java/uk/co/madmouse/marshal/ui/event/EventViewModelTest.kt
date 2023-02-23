package uk.co.madmouse.marshal.ui.event

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import retrofit2.Response
import uk.co.madmouse.marshal.core.models.database.Event
import uk.co.madmouse.marshal.core.network.ApiService
import uk.co.madmouse.marshal.core.repository.EventsRepository


@ExperimentalCoroutinesApi
class TestCoroutineRule : TestRule {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    override fun apply(base: Statement?, description: Description?) = object : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            Dispatchers.setMain(testCoroutineDispatcher)
            base?.evaluate()
            Dispatchers.resetMain()
            testCoroutineScope.cleanupTestCoroutines()
        }
    }

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
        testCoroutineScope.runBlockingTest { block() }

}


@ExperimentalCoroutinesApi
internal class EventViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    lateinit var eventsRepositoryMock: EventsRepository

    @MockK
    lateinit var apiServiceMock: ApiService

    private lateinit var eventViewModel: EventViewModel;


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        val flow = MutableStateFlow(emptyList<Event>())
        coEvery { eventsRepositoryMock.fetchEvents(any() as Long) } coAnswers { flow }
        eventViewModel = EventViewModel(eventsRepositoryMock, apiServiceMock)
    }

    @Test
    fun `WHEN loadFromWeb is called and valid list is returned THEN Events Repo Batch Insert is called `() = runTest {
        //Given
        val validList = ArrayList<Event>()
        validList.add(
            Event(
                2,
                "Event 1",
                1,
                1,
                "Address",
                "Contact", "Phone"
            )
        )

        coEvery { apiServiceMock.getEventsByDate(any() as Long) } coAnswers {
            Response.success(validList)
        }

        coEvery { eventsRepositoryMock.batchInsert(any()) } returns Unit

        //When
        eventViewModel.loadFromWeb()

        //Then
        coVerify {
            eventsRepositoryMock.fetchEvents(any())
            apiServiceMock.getEventsByDate(any())
        }

        confirmVerified(eventsRepositoryMock)
        confirmVerified(apiServiceMock)
    }

    @Test
    fun `WHEN loadFromWeb is called and empty list is returned THEN Events Repo Batch Insert is not called `() = runBlocking {
        coEvery { apiServiceMock.getEventsByDate(any() as Long) } coAnswers {
            Response.success(emptyList())
        }

        coEvery { eventsRepositoryMock.batchInsert(any()) } coAnswers {
            0
        }

        eventViewModel.loadFromWeb()
        coVerify {
            eventsRepositoryMock.fetchEvents(any())
            apiServiceMock.getEventsByDate(any())
        }
        coVerify(exactly = 0) { eventsRepositoryMock.batchInsert(any()) }

        confirmVerified(eventsRepositoryMock)
        confirmVerified(apiServiceMock)
    }

}