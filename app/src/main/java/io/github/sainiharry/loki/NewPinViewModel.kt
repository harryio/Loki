package io.github.sainiharry.loki

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.sainiharry.loki.utils.Event

class NewPinViewModel : ViewModel() {

    private val _confirmPinNavigationEvent = MutableLiveData<Event<String>>()
    val confirmPinNavigationEvent: LiveData<Event<String>>
        get() = _confirmPinNavigationEvent

    fun handleNewPin(newPin: String) {
        if (newPin.length == 4) {
            _confirmPinNavigationEvent.value = Event(newPin)
        }
    }
}