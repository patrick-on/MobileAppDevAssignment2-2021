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


class SongActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongBinding
    var song = SongModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp

        if (intent.hasExtra("song_edit")) {
            edit = true
            song = intent.extras?.getParcelable("song_edit")!!
            binding.songTitle.setText(song.title)
            binding.artist.setText(song.artist)
            binding.btnAdd.setText(R.string.save_song)
        }

        binding.btnAdd.setOnClickListener() {
            song.title = binding.songTitle.text.toString()
            song.artist = binding.artist.text.toString()
            if (song.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_song_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.songs.update(song.copy())
                } else {
                    app.songs.create(song.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
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