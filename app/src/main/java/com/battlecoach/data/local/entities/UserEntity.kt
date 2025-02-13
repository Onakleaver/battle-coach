package com.battlecoach.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val username: String,
    val chessComRating: Int,
    val lichessRating: Int,
    val puzzleRating: Int,
    val lastSyncTimestamp: Long
) 