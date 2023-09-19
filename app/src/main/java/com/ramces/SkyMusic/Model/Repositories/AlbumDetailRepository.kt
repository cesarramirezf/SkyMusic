package com.ramces.SkyMusic.Model.Repositories

import com.ramces.SkyMusic.Model.Entities.AlbumDetails
import com.ramces.SkyMusic.Model.Interface.SpotifyService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumDetailRepository(private val spotifyService: SpotifyService) {

    suspend fun getAlbumDetails(albumId: String): AlbumDetails? {
        return withContext(Dispatchers.IO) {
            try {
                val response = spotifyService.getAlbumDetails(albumId)

                if (response.isSuccessful) {
                    return@withContext response.body()
                } else {
                    return@withContext null
                }
            } catch (e: Exception) {
                return@withContext null
            }
        }
    }
}