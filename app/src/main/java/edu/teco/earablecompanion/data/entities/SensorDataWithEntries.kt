package edu.teco.earablecompanion.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class SensorDataWithEntries(
    @Embedded val data: SensorData,
    @Relation(
        parentColumn = "data_id",
        entityColumn = "data_id"
    )
    val entries: List<SensorDataEntry>
)