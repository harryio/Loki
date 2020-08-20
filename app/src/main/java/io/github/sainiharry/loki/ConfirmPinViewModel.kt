package io.github.sainiharry.loki

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.sainiharry.loki.utils.Event

class ConfirmPinViewModel(private val currentPin: String) : ViewModel() {

    private val _pinSetSuccessEvent = MutableLiveData<Event<String>>()
    val pinSetSuccessEvent: LiveData<Event<String>>
        get() = _pinSetSuccessEvent

    private val _pinSetErrorEvent = MutableLiveData<Event<Any>>()
    val pinSetErrorEvent: LiveData<Event<Any>>
        get() = _pinSetErrorEvent

    fun handleConfirmPin(confirmPin: String?) {
        if (confirmPin.isNullOrEmpty()) return
        if (confirmPin.length < 4) return

        if (confirmPin == currentPin) {
            _pinSetSuccessEvent.value = Event(confirmPin)
        } else {
            _pinSetErrorEvent.value = Event(Any())
        }
    }
}