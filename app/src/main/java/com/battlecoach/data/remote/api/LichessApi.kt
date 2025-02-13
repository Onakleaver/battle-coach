package com.battlecoach.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LichessApi {
    @GET("api/user/{username}")
    suspend fun getUserProfile(@Path("username") username: String): LichessProfileResponse

    @GET("api/games/user/{username}")
    suspend fun getUserGames(
        @Path("username") username: String,
        @Query("max") max: Int = 20,
        @Query("perfType") perfType: String? = null
    ): List<LichessGame>
}

data class LichessProfileResponse(
    val id: String,
    val username: String,
    val perfs: LichessPerfs
)

data class LichessPerfs(
    val rapid: LichessRating?,
    val blitz: LichessRating?,
    val bullet: LichessRating?,
    val puzzle: LichessRating?
)

data class LichessRating(
    val games: Int,
    val rating: Int,
    val prog: Int
)

data class LichessGame(
    val id: String,
    val rated: Boolean,
    val speed: String,
    val status: String,
    val players: LichessPlayers,
    val winner: String?,
    val moves: String
)

data class LichessPlayers(
    val white: LichessPlayer,
    val black: LichessPlayer
)

data class LichessPlayer(
    val user: LichessUser,
    val rating: Int
)

data class LichessUser(
    val name: String,
    val id: String
) 