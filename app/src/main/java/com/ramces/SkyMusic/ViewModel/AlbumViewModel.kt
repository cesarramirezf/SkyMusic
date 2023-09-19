package com.ramces.SkyMusic.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ramces.SkyMusic.Model.Entities.Album
import com.ramces.SkyMusic.Model.Repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumViewModel(private val repository: AlbumRepository) : ViewModel() {

    private val _albumsLiveData = MutableLiveData<List<Album>>()
    val albumsLiveData: LiveData<List<Album>> = _albumsLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private var currentPage = 0
    private var isLoading = false

    init {
        loadAlbums()
    }

    fun loadAlbums() {
        if (isLoading) return

        isLoading = true

        GlobalScope.launch {
            try {
                val albums = repository.getRecentAlbums(currentPage)
                withContext(Dispatchers.Main) {
                    _albumsLiveData.value = albums
                    isLoading = false
                    currentPage++
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _errorLiveData.value = "Error al cargar los álbumes: ${e.message}"
                    isLoading = false
                }
            }
        }
    }

    fun searchAlbums(query: String) {
    }
}