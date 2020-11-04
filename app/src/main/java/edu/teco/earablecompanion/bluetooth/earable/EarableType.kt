package edu.teco.earablecompanion.bluetooth.earable

sealed class EarableType {
    object ESense : EarableType()
    object NotSupported : EarableType()

    data class Cosinuss(val accSupported: Boolean = false) : EarableType()
    data class Generic(val heartRateSupported: Boolean = false, val bodyTemperatureSupported: Boolean = false) : EarableType()
}