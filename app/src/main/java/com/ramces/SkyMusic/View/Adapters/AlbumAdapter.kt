package com.ramces.SkyMusic.View.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramces.SkyMusic.Model.Entities.Album
import com.ramces.SkyMusic.databinding.ItemAlbumBinding
import com.squareup.picasso.Picasso

class AlbumAdapter(private val albums: List<Album>, private val onItemClick: (Album) -> Unit) :
    RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album) {
            binding.albumName.text = album.name
            binding.artistName.text = album.artist
            Picasso.get().load(album.imageUrl).into(binding.albumImage)

            itemView.setOnClickListener { onItemClick(album) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = albums[position]
        holder.bind(album)
    }

    override fun getItemCount(): Int {
        return albums.size
    }
}