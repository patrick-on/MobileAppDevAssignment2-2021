package org.wit.musicapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.musicapp.databinding.ActivitySongBinding
import org.wit.musicapp.models.SongModel
import timber.log.Timber
import timber.log.Timber.i

class SongActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongBinding
    var song = SongModel()
    val songs = ArrayList<SongModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())
        i("Song Activity started...")

        binding.btnAdd.setOnClickListener() {
            song.title = binding.songTitle.text.toString()
            song.artist = binding.artist.text.toString()
            if (song.title.isNotEmpty()) {
                songs.add(song)
                i("add Button Pressed: ${song}")
                for (i in songs.indices)
                { i("Song[$i]:${this.songs[i]}") }
            }
            else {
                Snackbar.make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}