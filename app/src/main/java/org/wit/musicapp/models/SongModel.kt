package org.wit.musicapp.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SongModel(var id: Long = 0,
                     var title: String = "",
                     var artist: String = "",
                     var image: Uri = Uri.EMPTY) : Parcelable
