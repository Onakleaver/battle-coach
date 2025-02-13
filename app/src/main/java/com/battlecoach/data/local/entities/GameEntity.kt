package com.battlecoach.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "games",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["username"],
            childColumns = ["username"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GameEntity(
    @PrimaryKey
    val id: String,
    val username: String,
    val opponent: String,
    val result: String,
    val pgn: String,
    val timestamp: Long,
    val platform: String,
    val timeControl: String,
    val playerRating: Int,
    val opponentRating: Int,
    val isSynced: Boolean = false
) 