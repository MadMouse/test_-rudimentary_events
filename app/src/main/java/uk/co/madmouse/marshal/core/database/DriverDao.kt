package uk.co.madmouse.marshal.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import uk.co.madmouse.marshal.core.models.database.Driver

@Dao
interface DriverDao {
    @Query("SELECT * FROM ${Constants.TABLE_NAME_DRIVER}")
    fun drivers(): Flow<List<Driver>>

    @Query("SELECT * FROM ${Constants.TABLE_NAME_DRIVER} where ${Constants.FIELD_ID} = :driverId")
    fun driver(driverId: Long): Flow<Driver>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(driver: Driver): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg driver: Driver)

    @Update
    suspend fun update(driver: Driver): Int

    @Delete
    suspend fun delete(driver: Driver): Int

    @Delete
    suspend fun deleteAll(vararg driver: Driver): Int
}