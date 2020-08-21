package io.github.sainiharry.loki

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.sainiharry.loki.utils.Event

class EnterPinViewModel(private val existingPin: String) : ViewModel() {

    private val _settingsNavigationEvent = MutableLiveData<Event<Any>>()
    val settingsNavigationEvent: LiveData<Event<Any>>
        get() = _settingsNavigationEvent

    private val _pinEnterSuccessEvent = MutableLiveData<Event<Any>>()
    val pinEnterSuccessEvent: LiveData<Event<Any>>
        get() = _pinEnterSuccessEvent

    fun handlePinInput(pinInput: String?) {
        if (pinInput.isNullOrEmpty()) return
        if (pinInput.length < 4) return

        if (pinInput == existingPin) {
            _pinEnterSuccessEvent.value = Event(Any())
            _settingsNavigationEvent.value = Event(Any())
        }
    }
}