package org.wit.musicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import timber.log.Timber
import timber.log.Timber.i

class MusicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        Timber.plant(Timber.DebugTree())

        i("Music activity started")
    }
}