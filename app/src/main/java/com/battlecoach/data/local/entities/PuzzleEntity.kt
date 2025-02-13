package com.battlecoach.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "puzzles")
data class PuzzleEntity(
    @PrimaryKey
    val id: String,
    val fen: String,
    val moves: String,
    val rating: Int,
    val theme: String,
    val isCompleted: Boolean = false,
    val userRatingChange: Int? = null,
    val completedAt: Long? = null
) 