package io.github.sainiharry.loki

import android.app.Application

class LokiApp : Application() {

    override fun onCreate() {
        super.onCreate()

        PrefInteractor.context = this
    }
}