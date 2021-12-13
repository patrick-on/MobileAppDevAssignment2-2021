package org.wit.musicapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SongModel(var id: Long = 0,
                     var title: String = "",
                     var artist: String = "") : Parcelable
