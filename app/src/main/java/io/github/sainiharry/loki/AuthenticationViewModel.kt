package io.github.sainiharry.loki

import androidx.lifecycle.ViewModel

class AuthenticationViewModel : ViewModel() {

    private var _isUserAuthenticated = false
    val isUserAuthenticated: Boolean
        get() = _isUserAuthenticated

    fun setUserAuthenticated(authenticated: Boolean = true) {
        _isUserAuthenticated = authenticated
    }
}