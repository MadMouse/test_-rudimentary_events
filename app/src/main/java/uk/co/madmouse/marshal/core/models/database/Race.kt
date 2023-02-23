package uk.co.madmouse.marshal.core.models.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uk.co.madmouse.marshal.core.database.Constants

@Entity(
    tableName = "${Constants.TABLE_NAME_RACE}"
)
data class Race(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.FIELD_ID) val id: Long,
    @ColumnInfo(name = Constants.FIELD_DESCRIPTION) val description: String,
    @ColumnInfo(name = Constants.FIELD_EVENT_ID) val eventId: Long,
    @ColumnInfo(name = Constants.FIELD_HEATS) val heats: List<Heat>,
    @ColumnInfo(name = Constants.FIELD_TIMESTAMP) val timeStamp: Long
)