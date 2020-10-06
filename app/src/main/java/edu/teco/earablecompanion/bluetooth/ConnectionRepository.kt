package edu.teco.earablecompanion.bluetooth

import edu.teco.earablecompanion.overview.connection.ConnectionEvent
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import no.nordicsemi.android.support.v18.scanner.ScanResult
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.absoluteValue

class ConnectionRepository {
    private val _scanResult = ConflatedBroadcastChannel<ConcurrentHashMap<String, ScanResult>>().apply { offer(ConcurrentHashMap()) }
    val scanResult: Flow<Map<String, ScanResult>> get() = _scanResult.asFlow()

    private val _connectionEvent = ConflatedBroadcastChannel<ConnectionEvent>().apply { offer(ConnectionEvent.Empty) }
    val connectionEvent: Flow<ConnectionEvent> get() = _connectionEvent.asFlow()

    @Synchronized
    fun updateScanResult(result: ScanResult) {
        val current = _scanResult.value
        current[result.device.address] = result
        current.forEach { (address, currentResult) ->
            val elapsed = (result.timestampNanos - currentResult.timestampNanos).absoluteValue
            if (elapsed > ELAPSED_TIMESTAMP_NANOS_LIMIT) {
                current.remove(address)
            }
        }

        _scanResult.offer(current)
    }

    fun clearScanResult() = _scanResult.offer(ConcurrentHashMap())

    fun updateConnectionEvent(event: ConnectionEvent) = _connectionEvent.offer(event)
    fun clearConnectionEvent() = _connectionEvent.offer(ConnectionEvent.Empty)

    companion object {
        private const val ELAPSED_TIMESTAMP_NANOS_LIMIT = 10_000_000_000L
    }
}