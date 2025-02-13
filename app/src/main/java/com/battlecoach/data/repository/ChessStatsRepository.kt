package com.battlecoach.data.repository

import com.battlecoach.data.remote.api.ChessComApi
import com.battlecoach.data.remote.api.LichessApi
import com.battlecoach.data.remote.dto.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChessStatsRepository @Inject constructor(
    private val chessComApi: ChessComApi,
    private val lichessApi: LichessApi
) {
    suspend fun getUserStats(username: String): List<ChessUserStats> = coroutineScope {
        val chessComDeferred = async { fetchChessComStats(username) }
        val lichessDeferred = async { fetchLichessStats(username) }

        listOf(
            chessComDeferred.await(),
            lichessDeferred.await()
        )
    }

    private suspend fun fetchChessComStats(username: String): ChessUserStats {
        val stats = chessComApi.getUserStats(username)
        val archives = chessComApi.getGameArchives(username)
        
        // Get most recent archive
        val latestArchive = archives.archives.lastOrNull()?.let {
            val parts = it.split("/")
            val year = parts[parts.size - 2].toInt()
            val month = parts.last().toInt()
            chessComApi.getMonthlyGames(username, year, month)
        }

        return ChessUserStats(
            username = username,
            platform = ChessPlatform.CHESS_COM,
            ratings = ChessRatings(
                rapid = stats.chess_rapid?.last?.rating ?: 0,
                blitz = stats.chess_blitz?.last?.rating ?: 0,
                bullet = stats.chess_bullet?.last?.rating ?: 0,
                puzzle = stats.tactics?.last?.rating ?: 0
            ),
            recentGames = latestArchive?.games?.take(20)?.map { game ->
                ChessGame(
                    id = game.url,
                    timeControl = game.time_control,
                    result = when {
                        game.white.username.equals(username, true) -> 
                            if (game.white.result == "win") GameResult.WIN 
                            else if (game.white.result == "draw") GameResult.DRAW 
                            else GameResult.LOSS
                        else -> 
                            if (game.black.result == "win") GameResult.WIN 
                            else if (game.black.result == "draw") GameResult.DRAW 
                            else GameResult.LOSS
                    },
                    opponent = if (game.white.username.equals(username, true)) 
                        game.black.username else game.white.username,
                    opponentRating = if (game.white.username.equals(username, true)) 
                        game.black.rating else game.white.rating,
                    playerColor = if (game.white.username.equals(username, true)) 
                        ChessColor.WHITE else ChessColor.BLACK,
                    date = game.end_time,
                    moves = game.pgn
                )
            } ?: emptyList(),
            performanceTrend = buildPerformanceTrend(latestArchive?.games ?: emptyList(), username)
        )
    }

    private suspend fun fetchLichessStats(username: String): ChessUserStats {
        val profile = lichessApi.getUserProfile(username)
        val recentGames = lichessApi.getUserGames(username)

        return ChessUserStats(
            username = username,
            platform = ChessPlatform.LICHESS,
            ratings = ChessRatings(
                rapid = profile.perfs.rapid?.rating ?: 0,
                blitz = profile.perfs.blitz?.rating ?: 0,
                bullet = profile.perfs.bullet?.rating ?: 0,
                puzzle = profile.perfs.puzzle?.rating ?: 0
            ),
            recentGames = recentGames.map { game ->
                ChessGame(
                    id = game.id,
                    timeControl = game.speed,
                    result = when {
                        game.winner == null -> GameResult.DRAW
                        game.winner == username -> GameResult.WIN
                        else -> GameResult.LOSS
                    },
                    opponent = if (game.players.white.user.name == username) 
                        game.players.black.user.name else game.players.white.user.name,
                    opponentRating = if (game.players.white.user.name == username) 
                        game.players.black.rating else game.players.white.rating,
                    playerColor = if (game.players.white.user.name == username) 
                        ChessColor.WHITE else ChessColor.BLACK,
                    date = System.currentTimeMillis(), // Lichess provides date in a different format
                    moves = game.moves
                )
            },
            performanceTrend = buildPerformanceTrend(recentGames, username)
        )
    }

    private fun buildPerformanceTrend(
        games: List<Any>,
        username: String
    ): List<RatingPoint> {
        // Implementation depends on the specific data structure of games
        // This would track rating changes over time
        return emptyList() // Placeholder
    }
} 