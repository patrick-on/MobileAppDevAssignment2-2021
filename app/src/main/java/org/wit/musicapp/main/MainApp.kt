package org.wit.musicapp.main

import android.app.Application
import org.wit.musicapp.models.SongMemStore
import org.wit.musicapp.models.SongModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    //val songs = ArrayList<SongModel>()
    val songs = SongMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Song started")
    //    songs.add(SongModel("Max", "About one..."))
    //    songs.add(SongModel("Verstappen", "About two..."))
    //    songs.add(SongModel("Goat", "About three..."))
    }
}