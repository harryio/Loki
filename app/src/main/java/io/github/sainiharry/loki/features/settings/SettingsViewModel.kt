package io.github.sainiharry.loki.features.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.sainiharry.loki.utils.Event

internal class SettingsViewModel : ViewModel() {

    private val _newPinNavigationEvent = MutableLiveData<Event<Any>>()
    internal val newPinNavigationEvent: LiveData<Event<Any>>
        get() = _newPinNavigationEvent

    private val _isPinAvailable = MutableLiveData<Boolean>()
    internal val isPinAvailable: LiveData<Boolean>
        get() = _isPinAvailable

    private val _enterPinNavigationEvent = MutableLiveData<Event<String>>()
    internal val enterPinNavigationEvent: LiveData<Event<String>>
        get() = _enterPinNavigationEvent

    private val _pinDeactivatedEvent = MutableLiveData<Event<Any>>()
    internal val pinDeactivatedEvent: LiveData<Event<Any>>
        get() = _pinDeactivatedEvent

    private var isUserAuthenticated = false

    internal fun handlePin(pin: String?, isAppLocked: Boolean) {
        _isPinAvailable.value = !pin.isNullOrEmpty()
        if ((_isPinAvailable.value == true && !isUserAuthenticated) || isAppLocked) {
            _enterPinNavigationEvent.value = Event(pin!!)
        }
    }

    internal fun handlePinToggleClick() {
        if (isPinAvailable.value == false) {
            _newPinNavigationEvent.value = Event(Any())
        } else {
            _pinDeactivatedEvent.value = Event(Any())
            _isPinAvailable.value = false
        }
    }

    internal fun handleUserAuthenticated(isUserAuthenticated: Boolean) {
        this@SettingsViewModel.isUserAuthenticated = isUserAuthenticated
    }
}