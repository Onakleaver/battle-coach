package com.battlecoach.domain.engine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class StockfishEngine : ChessEngine {
    private var process: Process? = null
    private var reader: BufferedReader? = null
    private var writer: BufferedWriter? = null

    override suspend fun initialize() = withContext(Dispatchers.IO) {
        process = Runtime.getRuntime().exec("stockfish")
        reader = BufferedReader(InputStreamReader(process!!.inputStream))
        writer = BufferedWriter(OutputStreamWriter(process!!.outputStream))
        
        sendCommand("uci")
        sendCommand("isready")
        waitForResponse("readyok")
    }

    override suspend fun setDifficulty(level: EngineLevel) {
        val (skill, depth, time) = when (level) {
            EngineLevel.BEGINNER -> Triple(0, 5, 100)
            EngineLevel.INTERMEDIATE -> Triple(5, 10, 300)
            EngineLevel.ADVANCED -> Triple(10, 15, 500)
            EngineLevel.MASTER -> Triple(15, 20, 1000)
            EngineLevel.GRANDMASTER -> Triple(20, 25, 2000)
        }

        sendCommand("setoption name Skill Level value $skill")
        sendCommand("setoption name Maximum Depth value $depth")
    }

    override suspend fun analyze(fen: String, timeMs: Long): EngineAnalysis = 
        withContext(Dispatchers.IO) {
            sendCommand("position fen $fen")
            sendCommand("go movetime $timeMs")
            
            var bestMove = ""
            var eval = 0.0
            var depth = 0
            val pv = mutableListOf<String>()
            
            while (true) {
                val line = reader?.readLine() ?: break
                when {
                    line.startsWith("bestmove") -> {
                        bestMove = line.split(" ")[1]
                        break
                    }
                    line.startsWith("info") -> {
                        // Parse evaluation info
                        if (line.contains("score cp")) {
                            eval = line.split("score cp ")[1]
                                .split(" ")[0].toDouble() / 100
                        }
                        if (line.contains("depth")) {
                            depth = line.split("depth ")[1]
                                .split(" ")[0].toInt()
                        }
                        if (line.contains("pv")) {
                            pv.clear()
                            pv.addAll(line.split("pv ")[1].split(" "))
                        }
                    }
                }
            }
            
            EngineAnalysis(bestMove, eval, depth, pv, timeMs)
        }

    override suspend fun getBestMove(fen: String, timeMs: Long): String =
        analyze(fen, timeMs).bestMove

    override suspend fun stop() {
        sendCommand("stop")
    }

    override suspend fun quit() {
        sendCommand("quit")
        process?.destroy()
    }

    private suspend fun sendCommand(command: String) = withContext(Dispatchers.IO) {
        writer?.write("$command\n")
        writer?.flush()
    }

    private suspend fun waitForResponse(expected: String) = withContext(Dispatchers.IO) {
        while (true) {
            val line = reader?.readLine() ?: break
            if (line == expected) break
        }
    }
} 