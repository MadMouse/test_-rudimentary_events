package uk.co.madmouse.marshal.core.models.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import uk.co.madmouse.marshal.core.database.Constants

@Entity(
    tableName = "${Constants.TABLE_NAME_LAP}",
    indices = [
        Index(
            name = "${Constants.FIELD_HEAT_ID}_index",
            value = [Constants.FIELD_HEAT_ID]
        ),
        Index(
            name = "${Constants.FIELD_HEAT_ID}_${Constants.FIELD_VEHICLE_ID}_${Constants.FIELD_TIMESTAMP}_index",
            value = [Constants.FIELD_HEAT_ID, Constants.FIELD_VEHICLE_ID, Constants.FIELD_TIMESTAMP]
        ),
        Index(
            name = "${Constants.FIELD_HEAT_ID}_${Constants.FIELD_DRIVER_ID}_index",
            value = [Constants.FIELD_HEAT_ID, Constants.FIELD_DRIVER_ID]
        )]
)
data class Lap(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.FIELD_ID) val id: Long,
    @ColumnInfo(name = Constants.FIELD_HEAT_ID) val heatId: Long,
    @ColumnInfo(name = Constants.FIELD_DRIVER_ID) val driverId: Long,
    @ColumnInfo(name = Constants.FIELD_VEHICLE_ID) val vehicleId: String,
    @ColumnInfo(name = Constants.FIELD_LAP_TIME) val lapTime: Int,
    @ColumnInfo(name = Constants.FIELD_TRACK_TIMESTAMP) val trackTimeStamp: Long,
    @ColumnInfo(name = Constants.FIELD_TIMESTAMP) val timeStamp: Long
)
