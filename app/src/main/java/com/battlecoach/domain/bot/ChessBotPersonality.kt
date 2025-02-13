package com.battlecoach.domain.bot

import com.battlecoach.domain.engine.EngineLevel

data class BotResponse(
    val text: String,
    val voiceResourceId: String,
    val emotion: BotEmotion = BotEmotion.NEUTRAL
)

enum class BotEmotion {
    NEUTRAL, HAPPY, ANGRY, EXCITED, DISAPPOINTED, AMUSED
}

enum class GameSituation {
    GAME_START,
    GOOD_MOVE,
    BAD_MOVE,
    PLAYER_BLUNDER,
    BOT_ADVANTAGE,
    PLAYER_ADVANTAGE,
    CHECKMATE_WIN,
    CHECKMATE_LOSS,
    DRAW_OFFER,
    TIME_PRESSURE
}

interface ChessBotPersonality {
    val name: String
    val title: String
    val voicePackId: String
    val defaultLevel: EngineLevel
    
    fun getResponse(situation: GameSituation, evaluation: Double): BotResponse
    fun isVoicePackInstalled(): Boolean
    suspend fun downloadVoicePack(): Result<Unit>
} 