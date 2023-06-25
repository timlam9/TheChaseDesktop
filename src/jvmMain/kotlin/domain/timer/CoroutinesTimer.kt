package domain.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CoroutinesTimer(private val timerScope: CoroutineScope) {

    private var startTime = 120_000L * 2

    private var _timerStateFlow = MutableStateFlow(CoroutinesTimerState(startTime))
    val timerStateFlow: StateFlow<CoroutinesTimerState> = _timerStateFlow

    init {
        timerScope.launch {
            _timerStateFlow.onEach { state ->
                if (state.isRunning) {
                    val newTime = _timerStateFlow.value.timeRemaining - 1000L
                    _timerStateFlow.update { it.copy(timeRemaining = newTime) }

                    delay(1000L)
                }
            }.launchIn(timerScope)
        }
    }

    fun setTimer(start: Long){
        startTime = start

        _timerStateFlow.update {
            it.copy(totalTime = startTime, timeRemaining = startTime)
        }
    }

    fun resume() {
        _timerStateFlow.update {
            it.copy(isRunning = true)
        }
    }

    fun pause() {
        _timerStateFlow.update {
            it.copy(isRunning = false)
        }
    }

    fun reset() {
        _timerStateFlow.update {
            it.copy(isRunning = false, timeRemaining = it.totalTime)
        }
    }
}
