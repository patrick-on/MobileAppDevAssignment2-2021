package org.wit.musicapp.models

interface SongStore {
    fun findAll(): List<SongModel>
    fun create(song: SongModel)
    fun update(song: SongModel)
}