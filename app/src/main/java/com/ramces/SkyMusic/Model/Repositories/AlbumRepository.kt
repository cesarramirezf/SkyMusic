package com.ramces.SkyMusic.Model.Repositories

import com.ramces.SkyMusic.Model.Entities.Album
import com.ramces.SkyMusic.Model.Interface.SpotifyService
import com.ramces.SkyMusic.Model.Response.AlbumsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class AlbumRepository(private val spotifyService: SpotifyService) {

    suspend fun getRecentAlbums(page: Int): List<Album> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<AlbumsResponse> = spotifyService.getRecentAlbums(page)

                if (response.isSuccessful) {
                    val albumsResponse = response.body()
                    albumsResponse?.albums?.items ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}