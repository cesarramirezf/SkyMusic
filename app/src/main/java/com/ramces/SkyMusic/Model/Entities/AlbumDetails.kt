package com.ramces.SkyMusic.Model.Entities

import com.google.gson.annotations.SerializedName

data class AlbumDetails(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("artist")
    val artist: String,

    @SerializedName("release_date")
    val releaseDate: String,
)
