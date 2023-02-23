package uk.co.madmouse.marshal.core.models.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uk.co.madmouse.marshal.core.database.Constants

@Entity(
    tableName = "${Constants.TABLE_NAME_HEAT}"
)
data class Heat(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.FIELD_ID) val id: Long,
    @ColumnInfo(name = Constants.FIELD_DESCRIPTION) val description: String,
    @ColumnInfo(name = Constants.FIELD_RACE_ID) val raceId: Long,
    @ColumnInfo(name = Constants.FIELD_DRIVERS) val drivers: List<Driver>,
    @ColumnInfo(name = Constants.FIELD_TIMESTAMP) val timeStamp: Long
)
