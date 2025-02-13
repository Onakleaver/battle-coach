package com.battlecoach.domain.engine.stockfish

import com.battlecoach.domain.engine.ChessEngine
import com.battlecoach.domain.engine.EngineAnalysis
import com.battlecoach.domain.engine.EngineLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import javax.inject.Inject

class StockfishEngine @Inject constructor() : ChessEngine {
    private var process: Process? = null
    private var reader: BufferedReader? = null
    private var writer: BufferedWriter? = null
    private val mutex = Mutex()

    override suspend fun initialize() = mutex.withLock {
        withContext(Dispatchers.IO) {
            process = Runtime.getRuntime().exec("stockfish")
            reader = BufferedReader(InputStreamReader(process!!.inputStream))
            writer = BufferedWriter(OutputStreamWriter(process!!.outputStream))

            sendCommand("uci")
            sendCommand("setoption name UCI_AnalyseMode value true")
            sendCommand("setoption name Threads value 2")
            sendCommand("isready")
            waitForResponse("readyok")
        }
    }

    override suspend fun setPosition(fen: String) = mutex.withLock {
        sendCommand("position fen $fen")
    }

    override suspend fun analyze(timeMs: Long): EngineAnalysis = mutex.withLock {
        withContext(Dispatchers.IO) {
            sendCommand("go movetime $timeMs")
            
            var bestMove = ""
            var evaluation = 0.0
            var depth = 0
            var nodes = 0L
            val pv = mutableListOf<String>()
            
            while (true) {
                val line = reader?.readLine() ?: break
                when {
                    line.startsWith("bestmove") -> {
                        bestMove = line.split(" ")[1]
                        break
                    }
                    line.startsWith("info") -> {
                        if (line.contains("score cp")) {
                            evaluation = line.split("score cp ")[1]
                                .split(" ")[0].toDouble() / 100
                        }
                        if (line.contains("depth")) {
                            depth = line.split("depth ")[1]
                                .split(" ")[0].toInt()
                        }
                        if (line.contains("nodes")) {
                            nodes = line.split("nodes ")[1]
                                .split(" ")[0].toLong()
                        }
                        if (line.contains("pv")) {
                            pv.clear()
                            pv.addAll(line.split("pv ")[1].split(" "))
                        }
                    }
                }
            }
            
            EngineAnalysis(
                bestMove = bestMove,
                evaluation = evaluation,
                depth = depth,
                principalVariation = pv,
                nodes = nodes,
                timeMs = timeMs
            )
        }
    }

    override suspend fun getBestMove(timeMs: Long): String {
        return analyze(timeMs).bestMove
    }

    override suspend fun setDifficulty(level: EngineLevel) = mutex.withLock {
        level.skillLevel?.let { skill ->
            sendCommand("setoption name Skill Level value $skill")
        }
        sendCommand("setoption name Maximum Depth value ${level.depth}")
    }

    override suspend fun stop() = mutex.withLock {
        sendCommand("stop")
    }

    override suspend fun quit() = mutex.withLock {
        try {
            sendCommand("quit")
        } finally {
            process?.destroy()
            process = null
            reader = null
            writer = null
        }
    }

    private suspend fun sendCommand(command: String) {
        withContext(Dispatchers.IO) {
            writer?.write("$command\n")
            writer?.flush()
        }
    }

    private suspend fun waitForResponse(expected: String) {
        withContext(Dispatchers.IO) {
            while (true) {
                val line = reader?.readLine() ?: break
                if (line == expected) break
            }
        }
    }
} 