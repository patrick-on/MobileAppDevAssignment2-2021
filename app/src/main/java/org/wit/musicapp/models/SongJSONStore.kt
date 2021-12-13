package org.wit.musicapp.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.musicapp.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "songs.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<SongModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class SongJSONStore(private val context: Context) : SongStore {

    var songs = mutableListOf<SongModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<SongModel> {
        logAll()
        return songs
    }

    override fun create(song: SongModel) {
        song.id = generateRandomId()
        songs.add(song)
        serialize()
    }


    override fun update(song: SongModel) {
        val songsList = findAll() as ArrayList<SongModel>
        var foundSong: SongModel? = songsList.find { s -> s.id == song.id }
        if (foundSong != null) {
            foundSong.title = song.title
            foundSong.artist = song.artist
            foundSong.image = song.image
            foundSong.lat = song.lat
            foundSong.lng = song.lng
            foundSong.zoom = song.zoom
        }
        serialize()
    }

    override fun delete(song: SongModel) {
        songs.remove(song)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(songs, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        songs = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        songs.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>, JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}