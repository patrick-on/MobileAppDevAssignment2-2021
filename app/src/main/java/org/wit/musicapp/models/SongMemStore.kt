package org.wit.musicapp.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class SongMemStore : SongStore {

    val songs = ArrayList<SongModel>()

    override fun findAll(): List<SongModel> {
        return songs
    }

    override fun create(song: SongModel) {
        song.id = getId()
        songs.add(song)
        logAll()
    }

    override fun update(song: SongModel) {
        var foundSong: SongModel? = songs.find { s -> s.id == song.id }
        if (foundSong != null) {
            foundSong.title = song.title
            foundSong.artist = song.artist
            foundSong.image = song.image
            foundSong.lat = song.lat
            foundSong.lng = song.lng
            foundSong.zoom = song.zoom
            logAll()
        }
    }

    private fun logAll() {
        songs.forEach { i("$it") }
    }
}