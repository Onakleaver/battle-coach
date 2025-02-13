package com.battlecoach.ui.screens.game

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.battlecoach.domain.bot.BotResponse
import com.battlecoach.domain.bot.ChessBotPersonality
import com.battlecoach.domain.bot.GameSituation
import com.battlecoach.domain.engine.ChessEngineManager
import com.battlecoach.domain.engine.EngineLevel
import com.battlecoach.domain.engine.EngineAnalysis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChessGameViewModel @Inject constructor(
    private val engineManager: ChessEngineManager,
    private val bot: SungJinWooBot,
    private val voicePackManager: VoicePackManager
) : ViewModel() {
    
    private val _botResponse = MutableStateFlow<BotResponse?>(null)
    val botResponse: StateFlow<BotResponse?> = _botResponse

    private var mediaPlayer: MediaPlayer? = null

    init {
        viewModelScope.launch {
            if (!bot.isVoicePackInstalled()) {
                bot.downloadVoicePack()
            }
            
            // Initial greeting
            handleBotResponse(
                bot.getResponse(GameSituation.GAME_START, 0.0)
            )
        }
    }

    fun makeAIMove() {
        viewModelScope.launch {
            val currentFen = getCurrentFen()
            val analysis = engineManager.analyze(currentFen, timeMs = 1000)
            
            // Get appropriate response based on position evaluation
            val situation = when {
                analysis.evaluation > 3.0 -> GameSituation.BOT_ADVANTAGE
                analysis.evaluation < -3.0 -> GameSituation.PLAYER_ADVANTAGE
                else -> GameSituation.NEUTRAL
            }
            
            handleBotResponse(
                bot.getResponse(situation, analysis.evaluation)
            )

            executeMove(analysis.bestMove)
        }
    }

    private fun handleBotResponse(response: BotResponse) {
        _botResponse.value = response
        
        if (bot.isVoicePackInstalled()) {
            playVoice(response.voiceResourceId)
        }
    }

    private fun playVoice(resourceId: String) {
        viewModelScope.launch {
            try {
                mediaPlayer?.release()
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(
                        context,
                        voicePackManager.getVoiceUri(bot.voicePackId, resourceId)
                    )
                    prepare()
                    start()
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    // ... other methods ...
} 