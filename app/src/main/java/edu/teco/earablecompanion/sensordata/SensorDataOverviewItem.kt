package edu.teco.earablecompanion.sensordata

import edu.teco.earablecompanion.data.entities.SensorData
import java.time.Duration
import java.time.LocalDateTime

sealed class SensorDataOverviewItem {
    object NoData : SensorDataOverviewItem()

    data class Data(val id: Long, val title: String, val description: String, val createdAt: LocalDateTime, val stoppedAt: LocalDateTime?, val duration: Duration?, val entryCount: Int) :
        SensorDataOverviewItem() {

        companion object {
            fun SensorData.toOverviewItem(entryCount: Int) = Data(
                id = dataId,
                title = title,
                description = description ?: "",
                createdAt = createdAt,
                stoppedAt = stoppedAt,
                duration = stoppedAt?.let { Duration.between(createdAt, stoppedAt) },
                entryCount = entryCount
            )
        }
    }
}