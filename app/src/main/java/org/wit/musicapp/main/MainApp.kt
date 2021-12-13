package org.wit.musicapp.main

import android.app.Application
import org.wit.musicapp.models.SongJSONStore
import org.wit.musicapp.models.SongMemStore
import org.wit.musicapp.models.SongModel
import org.wit.musicapp.models.SongStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var songs: SongStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        songs = SongJSONStore(applicationContext)
        i("Music App started")
    }
}