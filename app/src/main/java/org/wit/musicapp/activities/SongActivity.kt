package org.wit.musicapp.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.musicapp.R
import org.wit.musicapp.databinding.ActivitySongBinding
import org.wit.musicapp.helpers.showImagePicker
import org.wit.musicapp.main.MainApp
import org.wit.musicapp.models.Location
import org.wit.musicapp.models.SongModel
import timber.log.Timber.i


class SongActivity : AppCompatActivity() {

    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var binding: ActivitySongBinding
    var song = SongModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var edit = false

        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("Song Activity started...")

        if (intent.hasExtra("song_edit")) {
            edit = true
            song = intent.extras?.getParcelable("song_edit")!!
            binding.songTitle.setText(song.title)
            binding.artist.setText(song.artist)
            binding.btnAdd.setText(R.string.save_song)
            Picasso.get()
                .load(song.image)
                .into(binding.albumImage)
            if (song.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_album_image)
            }
        }

        binding.btnAdd.setOnClickListener() {
            song.title = binding.songTitle.text.toString()
            song.artist = binding.artist.text.toString()
            if (song.title.isEmpty() or song.artist.isEmpty()) {
                Snackbar.make(it,R.string.enter_song_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.songs.update(song.copy())
                } else {
                    app.songs.create(song.copy())
                    i("Song has been added: $song")
                    setResult(RESULT_OK)
                    finish()
                }
            }
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        binding.songLocation.setOnClickListener {
            val location = Location(53.3352, -6.2285, 15f)
            if (song.zoom != 0f) {
                location.lat =  song.lat
                location.lng = song.lng
                location.zoom = song.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerImagePickerCallback()
        registerMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_song, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                app.songs.delete(song)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            song.image = result.data!!.data!!
                            Picasso.get()
                                .load(song.image)
                                .into(binding.albumImage)
                            binding.chooseImage.setText(R.string.change_album_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            song.lat = location.lat
                            song.lng = location.lng
                            song.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}