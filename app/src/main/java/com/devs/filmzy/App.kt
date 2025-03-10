package com.devs.filmzy

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this) // initialisasi library threetenabp (date)
    }
}
