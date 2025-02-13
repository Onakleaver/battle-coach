package com.battlecoach.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.battlecoach.data.local.entities.GameEntity
import com.battlecoach.data.local.entities.UserEntity
import com.battlecoach.data.repository.GameHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameHistoryViewModel @Inject constructor(
    private val repository: GameHistoryRepository
) : ViewModel() {
    private val _username = MutableStateFlow<String?>(null)
    
    val uiState: StateFlow<GameHistoryUiState> = combine(
        _username.filterNotNull(),
        repository.getUserFlow(_username.value ?: ""),
        repository.getUserGamesFlow(_username.value ?: "")
    ) { username, user, games ->
        GameHistoryUiState(
            username = username,
            user = user,
            games = games,
            isLoading = false,
            error = null
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = GameHistoryUiState()
    )

    fun setUsername(username: String) {
        _username.value = username
        syncData()
    }

    fun syncData() {
        val username = _username.value ?: return
        viewModelScope.launch {
            try {
                repository.syncUserData(username)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

data class GameHistoryUiState(
    val username: String? = null,
    val user: UserEntity? = null,
    val games: List<GameEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) 