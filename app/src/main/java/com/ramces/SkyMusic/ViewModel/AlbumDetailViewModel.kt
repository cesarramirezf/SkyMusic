package com.ramces.SkyMusic.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramces.SkyMusic.Model.Entities.AlbumDetails
import com.ramces.SkyMusic.Model.Repositories.AlbumDetailRepository
import kotlinx.coroutines.launch

class AlbumDetailViewModel(private val albumDetailRepository: AlbumDetailRepository) : ViewModel() {

    private val _albumDetailsLiveData = MutableLiveData<AlbumDetails?>()
    val albumDetailsLiveData: MutableLiveData<AlbumDetails?> = _albumDetailsLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    fun loadAlbumDetails(albumId: String) {
        viewModelScope.launch {
            try {
                val albumDetails = albumDetailRepository.getAlbumDetails(albumId)
                if (albumDetails != null) {
                    _albumDetailsLiveData.value = albumDetails
                } else {
                    _errorLiveData.value = "No se encontraron detalles para este álbum."
                }
            } catch (e: Exception) {
                _errorLiveData.value = "Error al cargar los detalles del álbum: ${e.message}"
            }
        }
    }
}