package io.github.sainiharry.loki

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlin.math.max

private const val PACKAGE_NAME = "io.github.sainiharry.loki"
private const val ENCRYPTED_SHARED_PREFERENCES_FILE = "$PACKAGE_NAME.EncryptedSharedPreferences"
private const val SHARED_PREFERENCES_FILE = "$PACKAGE_NAME.SharedPreferences"

private const val KEY_PIN = "KEY_LOKI_PIN"

private const val KEY_UNLOCK_SECONDS = "KEY_UNLOCK_SECONDS"

object PrefInteractor {

    lateinit var context: Context

    private val encryptedSharedPref by lazy {
        EncryptedSharedPreferences.create(
            context,
            ENCRYPTED_SHARED_PREFERENCES_FILE,
            MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private val sharedPref by lazy {
        context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
    }

    fun storePin(pin: String) {
        encryptedSharedPref.edit().putString(KEY_PIN, pin).apply()
    }

    fun clearPin() {
        encryptedSharedPref.edit().remove(KEY_PIN).apply()
    }

    fun getPin(): String? = encryptedSharedPref.getString(KEY_PIN, null)

    fun setUnlockSeconds(seconds: Int) {
        sharedPref.edit().putInt(KEY_UNLOCK_SECONDS, max(seconds, 0)).apply()
    }

    fun getUnlockSeconds() = sharedPref.getInt(KEY_UNLOCK_SECONDS, 0)

    fun isAppLocked(): Boolean = sharedPref.getInt(KEY_UNLOCK_SECONDS, 0) > 0
}