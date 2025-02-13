package com.battlecoach.domain.parser

import com.github.bhlangonijr.chesslib.game.Game
import com.github.bhlangonijr.chesslib.pgn.PgnHolder
import java.io.File
import javax.inject.Inject

class PgnParser @Inject constructor() {
    fun parseFromString(pgn: String): List<Game> {
        return try {
            val tempFile = File.createTempFile("temp", ".pgn").apply {
                writeText(pgn)
                deleteOnExit()
            }
            val holder = PgnHolder(tempFile.absolutePath)
            holder.loadPgn()
            holder.games
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun parseFromUrl(url: String): List<Game> {
        return try {
            val holder = PgnHolder(url)
            holder.loadPgn()
            holder.games
        } catch (e: Exception) {
            emptyList()
        }
    }
} 