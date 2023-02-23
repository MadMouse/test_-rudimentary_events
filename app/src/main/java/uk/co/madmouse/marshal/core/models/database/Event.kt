package uk.co.madmouse.marshal.core.models.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uk.co.madmouse.marshal.core.database.Constants
import java.util.*

@Entity(
    tableName = "${Constants.TABLE_NAME_EVENTS}"
)
data class Event(
    @PrimaryKey()
    @ColumnInfo(name = Constants.FIELD_ID) val id: Long,
    @ColumnInfo(name = Constants.FIELD_DESCRIPTION) val description: String,
    @ColumnInfo(name = Constants.FIELD_START_DATETIME) val startDateTime: Long,
    @ColumnInfo(name = Constants.FIELD_END_DATETIME) val endDateTime: Long,
    @ColumnInfo(name = Constants.FIELD_ADDRESS) val address: String,
    @ColumnInfo(name = Constants.FIELD_NAME) val contact: String,
    @ColumnInfo(name = Constants.FIELD_PHONE) val phone: String,
    @ColumnInfo(name = Constants.FIELD_CREATED) val created: Long = Date().time,
    @ColumnInfo(name = Constants.FIELD_UPDATED) var updated: Long = Date().time
)
