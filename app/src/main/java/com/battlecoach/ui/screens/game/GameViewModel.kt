package com.battlecoach.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.battlecoach.data.repository.GameImportRepository
import com.battlecoach.domain.bot.AiPersonality
import com.battlecoach.domain.engine.ChessEngineManager
import com.battlecoach.domain.engine.EngineLevel
import com.battlecoach.domain.bot.AiDifficultyManager
import com.battlecoach.ui.theme.getRankTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.util.UUID

@HiltViewModel
class GameViewModel @Inject constructor(
    private val engineManager: ChessEngineManager,
    private val gameImportRepository: GameImportRepository,
    private val difficultyManager: AiDifficultyManager
) : ViewModel() {
    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState

    private var currentGameId = UUID.randomUUID().toString()

    private val _aiResponse = MutableStateFlow<String?>(null)
    val aiResponse: StateFlow<String?> = _aiResponse

    private var consecutiveWins = 0
    private var consecutiveLosses = 0
    private var playerElo = 1500 // Load from preferences

    private val currentPersonality = difficultyManager.getPersonalityForElo(playerElo)
    
    val boardTheme = getRankTheme(playerElo)

    init {
        viewModelScope.launch {
            engineManager.initialize()
            updateDifficulty()
        }
    }

    private fun updateDifficulty() {
        val difficulty = difficultyManager.calculateDifficulty(
            playerElo = playerElo,
            personality = currentPersonality,
            consecutiveWins = consecutiveWins,
            consecutiveLosses = consecutiveLosses
        )
        engineManager.setDifficulty(difficulty)
    }

    private fun handleGameEnd(result: GameResult) {
        when (result) {
            GameResult.WIN -> {
                consecutiveWins++
                consecutiveLosses = 0
                playerElo += 15
            }
            GameResult.LOSS -> {
                consecutiveLosses++
                consecutiveWins = 0
                playerElo -= 15
            }
            GameResult.DRAW -> {
                consecutiveWins = 0
                consecutiveLosses = 0
            }
        }
        updateDifficulty()
    }

    fun onSquareClick(square: String) {
        // Handle move logic
        viewModelScope.launch {
            val state = _gameState.value
            
            if (state.selectedSquare == null) {
                // First click - select piece
                if (state.pieces[square]?.color == state.sideToMove) {
                    _gameState.value = state.copy(
                        selectedSquare = square,
                        validMoves = calculateValidMoves(square)
                    )
                }
            } else {
                // Second click - make move
                if (square in state.validMoves) {
                    makeMove(state.selectedSquare, square)
                    makeEngineMove()
                }
                _gameState.value = state.copy(
                    selectedSquare = null,
                    validMoves = emptySet()
                )
            }
        }
    }

    private suspend fun makeEngineMove() {
        val fen = getCurrentFen()
        val move = engineManager.getBestMove(fen, timeMs = 1000)
        executeMove(move)
        
        // Check for game end
        if (isGameOver()) {
            engineManager.storeGameResult(
                gameId = currentGameId,
                playerName = "Player",
                engineName = "Stockfish",
                finalFen = getCurrentFen(),
                pgn = generatePGN(),
                result = getGameResult(),
                engineLevel = EngineLevel.INTERMEDIATE
            )
        }
    }

    fun importGame(pgn: String) {
        viewModelScope.launch {
            gameImportRepository.importFromPgn(pgn, "Player") // Replace with actual username
        }
    }

    fun importFromUrl(url: String) {
        viewModelScope.launch {
            gameImportRepository.importFromUrl(url, "Player")
        }
    }

    private fun getAiResponse(situation: GameSituation, evaluation: Double): String {
        val responses = currentPersonality.responses[situation] ?: return "..."
        return responses.random()
    }

    private suspend fun makeAiMove() {
        val fen = getCurrentFen()
        val analysis = engineManager.analyze(fen)
        
        // Get appropriate response based on position evaluation
        val situation = when {
            analysis.evaluation > 3.0 -> GameSituation.BOT_ADVANTAGE
            analysis.evaluation < -3.0 -> GameSituation.PLAYER_ADVANTAGE
            else -> GameSituation.PLAYER_GOOD_MOVE
        }
        
        _aiResponse.value = getAiResponse(situation, analysis.evaluation)
        executeMove(analysis.bestMove)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            engineManager.quit()
        }
    }

    // ... other methods ...
}

data class GameState(
    val pieces: Map<String, ChessPiece> = emptyMap(),
    val selectedSquare: String? = null,
    val validMoves: Set<String> = emptySet(),
    val lastMove: Pair<String, String>? = null,
    val checkSquare: String? = null,
    val sideToMove: PieceColor = PieceColor.WHITE,
    val moves: List<ChessMove> = emptyList(),
    val currentMoveIndex: Int = 0
) 