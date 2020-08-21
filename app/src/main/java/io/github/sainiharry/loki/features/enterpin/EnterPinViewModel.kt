package io.github.sainiharry.loki.features.enterpin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.github.sainiharry.loki.utils.Event

private const val MAX_ATTEMPTS = 3

private const val MAX_UNLOCK_SECONDS = 60

internal class EnterPinViewModel(private val existingPin: String, unlockSeconds: Int) :
    ViewModel() {

    private val _settingsNavigationEvent = MutableLiveData<Event<Any>>()
    internal val settingsNavigationEvent: LiveData<Event<Any>>
        get() = _settingsNavigationEvent

    private val _pinEnterSuccessEvent = MutableLiveData<Event<Any>>()
    internal val pinEnterSuccessEvent: LiveData<Event<Any>>
        get() = _pinEnterSuccessEvent

    private val _startCountDownEvent = MutableLiveData<Event<Int>>()
    internal val startCountDownEvent: LiveData<Event<Int>>
        get() = _startCountDownEvent

    private val _incorrectPinEnteredEvent = MutableLiveData<Event<Any>>()
    internal val incorrectPinEnteredEvent: LiveData<Event<Any>>
        get() = _incorrectPinEnteredEvent

    private val unlockSecondsLiveData = MutableLiveData<Int>()

    internal val pinInputEnabled: LiveData<Boolean> = Transformations.map(unlockSecondsLiveData) {
        it == 0
    }

    private val _incorrectMsgVisible = MutableLiveData<Boolean>(false)
    internal val incorrectMsgVisible: LiveData<Boolean>
        get() = _incorrectMsgVisible

    internal val lockedMsgVisible: LiveData<Boolean> = Transformations.map(pinInputEnabled) {
        !it
    }

    private var attempt = 1

    init {
        if (unlockSeconds > 0) {
            _startCountDownEvent.value = Event(unlockSeconds)
        }

        unlockSecondsLiveData.value = unlockSeconds
    }

    internal fun handlePinInput(pinInput: String?) {
        if (pinInput.isNullOrEmpty()) return
        if (pinInput.length < 4) return

        if (pinInput == existingPin) {
            _incorrectMsgVisible.value = false
            _pinEnterSuccessEvent.value = Event(Any())
            _settingsNavigationEvent.value = Event(Any())
            return
        }

        attempt++
        if (attempt > MAX_ATTEMPTS) {
            _startCountDownEvent.value = Event(MAX_UNLOCK_SECONDS)
            attempt = 1
        }
        _incorrectPinEnteredEvent.value = Event(Any())
        _incorrectMsgVisible.value = true
    }

    internal fun handleUnlockSeconds(unlockSeconds: Int) {
        unlockSecondsLiveData.value = unlockSeconds
    }
}