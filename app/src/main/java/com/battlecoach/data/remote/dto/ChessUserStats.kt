package com.battlecoach.data.remote.dto

data class ChessUserStats(
    val username: String,
    val platform: ChessPlatform,
    val ratings: ChessRatings,
    val recentGames: List<ChessGame>,
    val performanceTrend: List<RatingPoint>
)

enum class ChessPlatform {
    CHESS_COM, LICHESS
}

data class ChessRatings(
    val rapid: Int,
    val blitz: Int,
    val bullet: Int,
    val puzzle: Int
)

data class ChessGame(
    val id: String,
    val timeControl: String,
    val result: GameResult,
    val opponent: String,
    val opponentRating: Int,
    val playerColor: ChessColor,
    val date: Long,
    val moves: String? = null // PGN moves
)

data class RatingPoint(
    val timestamp: Long,
    val rating: Int,
    val gameType: String
)

enum class GameResult {
    WIN, LOSS, DRAW
}

enum class ChessColor {
    WHITE, BLACK
} 