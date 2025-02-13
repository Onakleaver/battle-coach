package com.battlecoach.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.battlecoach.data.remote.dto.ChessUserStats
import com.battlecoach.data.repository.ChessStatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChessProfileViewModel @Inject constructor(
    private val repository: ChessStatsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Initial)
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun loadUserStats(username: String) {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            try {
                val stats = repository.getUserStats(username)
                _uiState.value = ProfileUiState.Success(stats)
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class ProfileUiState {
    object Initial : ProfileUiState()
    object Loading : ProfileUiState()
    data class Success(val stats: List<ChessUserStats>) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
} 