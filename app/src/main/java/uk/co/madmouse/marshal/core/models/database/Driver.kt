package uk.co.madmouse.marshal.core.models.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uk.co.madmouse.marshal.core.database.Constants

@Entity(
    tableName = "${Constants.TABLE_NAME_DRIVER}"
)
data class Driver(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.FIELD_ID) val id: Long,
    @ColumnInfo(name = Constants.FIELD_NAME) val name: String,
    @ColumnInfo(name = Constants.FIELD_VEHICLE_ID) val vehicleId: String
    )
