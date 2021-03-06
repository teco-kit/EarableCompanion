package edu.teco.earablecompanion.settings

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import edu.teco.earablecompanion.data.SensorDataRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingsViewModel @ViewModelInject constructor(
    private val sensorDataRepository: SensorDataRepository,
) : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, Log.getStackTraceString(throwable))
    }

    fun clearData() = viewModelScope.launch(coroutineExceptionHandler) {
        sensorDataRepository.removeAll()
    }

    val recordingActive = liveData(viewModelScope.coroutineContext + coroutineExceptionHandler) {
        sensorDataRepository.activeRecording.collectLatest {
            emit(it != null)
        }
    }

    companion object {
        private val TAG = SettingsViewModel::class.java.simpleName
    }
}