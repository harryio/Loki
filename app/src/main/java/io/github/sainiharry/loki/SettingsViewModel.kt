package io.github.sainiharry.loki

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.sainiharry.loki.utils.Event

class SettingsViewModel(private val existingPin: String?) : ViewModel() {

    private val _newPinNavigationEvent = MutableLiveData<Event<Any>>()
    val newPinNavigationEvent: LiveData<Event<Any>>
        get() = _newPinNavigationEvent

    fun isPinSet(): Boolean = !existingPin.isNullOrEmpty()

    fun handlePinToggle(enabled: Boolean) {
        if (enabled && existingPin.isNullOrEmpty()) {
            _newPinNavigationEvent.value = Event(Any())
        }
    }
}