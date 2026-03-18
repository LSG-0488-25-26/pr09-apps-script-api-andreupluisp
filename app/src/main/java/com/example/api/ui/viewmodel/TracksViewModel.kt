package com.example.api.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.data.model.Track
import com.example.api.data.repository.TracksRepository
import kotlinx.coroutines.launch

class TracksViewModel : ViewModel() {

    private val repository = TracksRepository()

    private val _tracks = MutableLiveData<List<Track>>(emptyList())
    val tracks: LiveData<List<Track>> = _tracks

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    init {
        loadTracks()
    }

    fun loadTracks() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.getTracks().fold(
                onSuccess = { items ->
                    _tracks.value = items
                },
                onFailure = { throwable ->
                    _error.value = throwable.message ?: "Hi ha hagut un error"
                }
            )

            _isLoading.value = false
        }
    }
}
