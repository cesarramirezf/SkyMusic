package com.ramces.SkyMusic.Model.Response

import com.google.gson.annotations.SerializedName
import com.ramces.SkyMusic.Model.Entities.Album

data class AlbumsResponse(
    @SerializedName("albums")
    val albums: AlbumList
)

data class AlbumList(
    @SerializedName("items")
    val items: List<Album>
)
