package uk.co.madmouse.marshal.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uk.co.madmouse.marshal.core.models.database.Event

@Database(entities = [Event::class], version = 1)
abstract class MarshalDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}