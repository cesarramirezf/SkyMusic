package com.ramces.SkyMusic.View.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ramces.SkyMusic.Model.Entities.Album
import com.ramces.SkyMusic.Model.Interface.SpotifyService
import com.ramces.SkyMusic.databinding.ActivityMainBinding
import com.ramces.SkyMusic.databinding.ItemAlbumBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var albumAdapter: AlbumAdapter
    private lateinit var albumsList: ArrayList<Album>

    //   private lateinit var spotifyAppRemote: SpotifyAppRemote

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.spotify.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val spotifyService = retrofit.create(SpotifyService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupRecyclerView()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    lifecycleScope.launch {
                        searchAlbums(newText)
                    }
                } else {
                    albumsList.clear()
                    albumAdapter.notifyDataSetChanged()
                }
                return false
            }
        })
    }
    /*
        override fun onStart() {
            super.onStart()
            // Inicializar SpotifyAppRemote en onStart
            SpotifyAppRemote.connect(
                applicationContext,
                ConnectionParams.Builder("590abf35d4734457995411bb0ce98b3e")
                    .setRedirectUri("a6bb8ab40589469dbfb9afe4b1589be6")
                    .showAuthView(true)
                    .build(),
                object : Connector.ConnectionListener {
                    override fun onConnected(remote: SpotifyAppRemote) {
                        spotifyAppRemote = remote
                    }

                    override fun onFailure(throwable: Throwable) {
                    }
                }
            )
        }

        override fun onStop() {
            super.onStop()
            // Desconectar SpotifyAppRemote en onStop
            SpotifyAppRemote.disconnect(spotifyAppRemote)
        }*/

    private fun setupRecyclerView() {
        albumsList = ArrayList()
        albumAdapter = AlbumAdapter(albumsList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = albumAdapter
    }

    private suspend fun searchAlbums(query: String) {
        try {
            val response = spotifyService.searchAlbums(query)
            if (response.isSuccessful) {
                val albumsResponse = response.body()
                if (albumsResponse != null) {
                    val albums = albumsResponse.albums.items
                    albumsList.clear()
                    albumsList.addAll(albums)
                    runOnUiThread {
                        albumAdapter = AlbumAdapter(albumsList)
                        binding.recyclerView.adapter = albumAdapter
                    }
                }
            } else {
                println("Error No Conected")
            }
        } catch (e: Exception) {
        }
    }

    inner class AlbumAdapter(private val albums: List<Album>) :
        RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

        inner class ViewHolder(private val binding: ItemAlbumBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(album: Album) {
                binding.albumName.text = album.name
                binding.artistName.text = album.artist
                Picasso.get().load(album.imageUrl).into(binding.albumImage)
                binding.root.setOnClickListener {
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding =
                ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
}