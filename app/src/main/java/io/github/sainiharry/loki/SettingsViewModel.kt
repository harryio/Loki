package io.github.sainiharry.loki

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.sainiharry.loki.utils.Event

class SettingsViewModel : ViewModel() {

    private val _newPinNavigationEvent = MutableLiveData<Event<Any>>()
    val newPinNavigationEvent: LiveData<Event<Any>>
        get() = _newPinNavigationEvent

    private val _isPinAvailable = MutableLiveData<Boolean>()
    val isPinAvailable: LiveData<Boolean>
        get() = _isPinAvailable

    private val _enterPinNavigationEvent = MutableLiveData<Event<String>>()
    val enterPinNavigationEvent: LiveData<Event<String>>
        get() = _enterPinNavigationEvent

    private var isUserAuthenticated = false

    fun handlePin(pin: String?) {
        _isPinAvailable.value = !pin.isNullOrEmpty()
        if (_isPinAvailable.value == true && !isUserAuthenticated) {
            _enterPinNavigationEvent.value = Event(pin!!)
        }
    }

    fun handlePinToggleClick() {
        if (isPinAvailable.value == false) {
            _newPinNavigationEvent.value = Event(Any())
        } else {
            _isPinAvailable.value = false
        }
    }

    fun handleUserAuthenticated(isUserAuthenticated: Boolean) {
        this@SettingsViewModel.isUserAuthenticated = isUserAuthenticated
    }
}