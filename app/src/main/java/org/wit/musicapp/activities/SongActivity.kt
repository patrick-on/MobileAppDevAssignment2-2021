package org.wit.musicapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.musicapp.R
import org.wit.musicapp.databinding.ActivitySongBinding
import org.wit.musicapp.main.MainApp
import org.wit.musicapp.models.SongModel
import timber.log.Timber.i

class SongActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongBinding
    var song = SongModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Song Activity started...")
        binding.btnAdd.setOnClickListener() {
            song.title = binding.songTitle.text.toString()
            song.artist = binding.artist.text.toString()
            if (song.title.isNotEmpty()) {
                app.songs.add(song.copy())
                i("add Button Pressed: ${song}")
                for (i in app.songs.indices) {
                    { i("Song[$i]:${this.app.songs[i]}") }
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_song, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }
}