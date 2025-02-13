package com.battlecoach.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path

interface ChessComApi {
    @GET("pub/player/{username}/stats")
    suspend fun getUserStats(@Path("username") username: String): ChessComStatsResponse

    @GET("pub/player/{username}/games/archives")
    suspend fun getGameArchives(@Path("username") username: String): ChessComArchivesResponse

    @GET("pub/player/{username}/games/{year}/{month}")
    suspend fun getMonthlyGames(
        @Path("username") username: String,
        @Path("year") year: Int,
        @Path("month") month: Int
    ): ChessComGamesResponse
}

data class ChessComStatsResponse(
    val chess_rapid: ChessComRating?,
    val chess_blitz: ChessComRating?,
    val chess_bullet: ChessComRating?,
    val tactics: ChessComRating?
)

data class ChessComRating(
    val last: ChessComRatingDetails,
    val best: ChessComRatingDetails
)

data class ChessComRatingDetails(
    val rating: Int,
    val date: Long
)

data class ChessComArchivesResponse(
    val archives: List<String>
)

data class ChessComGamesResponse(
    val games: List<ChessComGame>
)

data class ChessComGame(
    val url: String,
    val pgn: String,
    val time_control: String,
    val end_time: Long,
    val rated: Boolean,
    val white: ChessComPlayer,
    val black: ChessComPlayer
)

data class ChessComPlayer(
    val username: String,
    val rating: Int,
    val result: String
) 