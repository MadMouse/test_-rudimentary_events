package uk.co.madmouse.marshal.core.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uk.co.madmouse.marshal.core.models.database.Lap

@Dao
interface LapDao {
    @Query("SELECT * FROM ${Constants.TABLE_NAME_LAP} where ${Constants.FIELD_HEAT_ID} = :heatId")
    fun laps(heatId: Long): Flow<List<Lap>>

    @Insert
    suspend fun insert(lap: Lap): Long

    @Insert
    suspend fun insertAll(vararg lap: Lap)

    @Delete
    suspend fun delete(lap: Lap): Int

    @Delete
    suspend fun delete(vararg lap: Lap): Int

    @Update
    suspend fun update(lap: Lap): Int
}