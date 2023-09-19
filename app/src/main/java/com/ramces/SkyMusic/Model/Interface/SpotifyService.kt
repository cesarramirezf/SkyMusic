package com.ramces.SkyMusic.Model.Interface

import com.ramces.SkyMusic.Model.Entities.AlbumDetails
import com.ramces.SkyMusic.Model.Response.AlbumsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotifyService {

    @GET("v1/search?type=album")
    suspend fun searchAlbums(@Query("q") query: String): Response<AlbumsResponse>

    @GET("v1/albums/{albumId}")
    suspend fun getAlbumDetails(@Path("albumId") albumId: String): Response<AlbumDetails>

    @GET("v1/browse/new-releases")
    suspend fun getRecentAlbums(@Query("offset") offset: Int): Response<AlbumsResponse>

}