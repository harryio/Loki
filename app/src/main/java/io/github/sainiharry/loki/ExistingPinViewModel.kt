package io.github.sainiharry.loki

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.sainiharry.loki.utils.Event

class ExistingPinViewModel(private val existingPin: String) : ViewModel() {

    private val _pinSuccessEvent = MutableLiveData<Event<Any>>()
    val pinSuccessEvent: LiveData<Event<Any>>
        get() = _pinSuccessEvent

    fun handlePinInput(pinInput: String?) {
        if (pinInput.isNullOrEmpty()) return
        if (pinInput.length < 4) return

        if (pinInput == existingPin) {
            _pinSuccessEvent.value = Event(Any())
        }
    }
}