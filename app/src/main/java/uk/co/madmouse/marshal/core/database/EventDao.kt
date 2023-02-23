package uk.co.madmouse.marshal.core.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uk.co.madmouse.marshal.core.models.database.Event

@Dao
interface EventDao {
    @Query("SELECT * FROM ${Constants.TABLE_NAME_EVENTS} " +
            "where ${Constants.FIELD_END_DATETIME} < :startDate " +
           " order by ${Constants.FIELD_START_DATETIME}")
    fun events(startDate : Long): Flow<List<Event>>

    @Query("SELECT * FROM ${Constants.TABLE_NAME_EVENTS} ")
    fun eventsAll(): Flow<List<Event>>

    @Query("SELECT * FROM ${Constants.TABLE_NAME_EVENTS} where ${Constants.FIELD_ID} = :id")
    fun fetchEventById(id: Long): Flow<Event>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun Insert(event: Event)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun batchInsert(vararg event: Event)

    @Query("DELETE FROM ${Constants.TABLE_NAME_EVENTS} where ${Constants.FIELD_END_DATETIME} < date('now')")
    suspend fun flushOldEvents():Int
}