package com.battlecoach.domain.engine

sealed interface ChessEngine {
    suspend fun initialize()
    suspend fun setPosition(fen: String)
    suspend fun analyze(timeMs: Long): EngineAnalysis
    suspend fun getBestMove(timeMs: Long): String
    suspend fun setDifficulty(level: EngineLevel)
    suspend fun stop()
    suspend fun quit()
}

data class EngineAnalysis(
    val bestMove: String,
    val evaluation: Double, // In pawns, positive for white
    val depth: Int,
    val principalVariation: List<String>,
    val nodes: Long,
    val timeMs: Long
)

enum class EngineLevel(
    val elo: Int,
    val depth: Int,
    val timeMs: Long,
    val skillLevel: Int? = null // Stockfish specific
) {
    BEGINNER(1000, 5, 100, 0),
    INTERMEDIATE(1500, 10, 300, 5),
    ADVANCED(2000, 15, 500, 10),
    MASTER(2500, 20, 1000, 15),
    GRANDMASTER(3000, 25, 2000, 20)
} 