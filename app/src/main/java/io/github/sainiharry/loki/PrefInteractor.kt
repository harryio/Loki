package io.github.sainiharry.loki

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

private const val SHARED_PREFERENCES_FILE = "LokiSharedPreferences"

private const val KEY_PIN = "KEY_LOKI_PIN"

class PrefInteractor(private val context: Context) {

    private val encryptedSharedPref by lazy {
        EncryptedSharedPreferences.create(
            context,
            SHARED_PREFERENCES_FILE,
            MasterKey.Builder(context).build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun storePin(pin: String) {
        encryptedSharedPref.edit().putString(KEY_PIN, pin).apply()
    }

    fun getPin(): String? = encryptedSharedPref.getString(KEY_PIN, null)
}