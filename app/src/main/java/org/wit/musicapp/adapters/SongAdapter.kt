package org.wit.musicapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.musicapp.databinding.CardSongBinding
import org.wit.musicapp.models.SongModel


interface SongListener {
    fun onSongClick(song: SongModel)
}

class SongAdapter constructor(private var songs: List<SongModel>,
private val listener: SongListener) :
    RecyclerView.Adapter<SongAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardSongBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val song = songs[holder.adapterPosition]
        holder.bind(song, listener)
    }

    override fun getItemCount(): Int = songs.size

    class MainHolder(private val binding : CardSongBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(song: SongModel, listener: SongListener) {
            binding.songTitle.text = song.title
            binding.artist.text = song.artist
            Picasso.get().load(song.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onSongClick(song) }
        }
    }
}