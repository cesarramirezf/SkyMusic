package com.ramces.SkyMusic.View.Activities

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.ramces.SkyMusic.Model.Entities.Album
import com.ramces.SkyMusic.R
import com.ramces.SkyMusic.databinding.ActivityAlbumDetailBinding
import com.squareup.picasso.Picasso

class AlbumDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlbumDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val album = intent.getParcelableExtra<Album>("album")

        album?.let {
            binding.albumName.text = it.name
            binding.artistName.text = it.artist
            Picasso.get().load(it.imageUrl).into(binding.albumImage)
        }
    }
}