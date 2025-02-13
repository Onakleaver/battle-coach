package com.battlecoach.domain.bot

import android.content.Context
import com.battlecoach.domain.engine.EngineLevel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SungJinWooBot @Inject constructor(
    @ApplicationContext private val context: Context,
    private val voicePackManager: VoicePackManager
) : ChessBotPersonality {
    
    override val name = "Sung Jin-Woo"
    override val title = "Shadow Monarch"
    override val voicePackId = "sung_jin_woo_v1"
    override val defaultLevel = EngineLevel.MASTER

    private val responses = mapOf(
        GameSituation.GAME_START to listOf(
            BotResponse(
                text = "I've faced countless dungeons, but a chess match... this could be interesting.",
                voiceResourceId = "game_start_1",
                emotion = BotEmotion.AMUSED
            ),
            BotResponse(
                text = "Let's see if you're as strong as my shadows.",
                voiceResourceId = "game_start_2",
                emotion = BotEmotion.NEUTRAL
            )
        ),
        GameSituation.GOOD_MOVE to listOf(
            BotResponse(
                text = "Arise! That was a worthy move.",
                voiceResourceId = "good_move_1",
                emotion = BotEmotion.EXCITED
            ),
            BotResponse(
                text = "You might be strong enough to join my shadow army.",
                voiceResourceId = "good_move_2",
                emotion = BotEmotion.HAPPY
            )
        ),
        GameSituation.PLAYER_BLUNDER to listOf(
            BotResponse(
                text = "A fatal mistake. Just like those Japanese S-rank hunters...",
                voiceResourceId = "blunder_1",
                emotion = BotEmotion.AMUSED
            )
        ),
        GameSituation.CHECKMATE_WIN to listOf(
            BotResponse(
                text = "This is the power of the Shadow Monarch.",
                voiceResourceId = "checkmate_win_1",
                emotion = BotEmotion.EXCITED
            )
        ),
        GameSituation.CHECKMATE_LOSS to listOf(
            BotResponse(
                text = "You... you might be stronger than the Architect himself.",
                voiceResourceId = "checkmate_loss_1",
                emotion = BotEmotion.EXCITED
            )
        )
    )

    override fun getResponse(situation: GameSituation, evaluation: Double): BotResponse {
        val situationResponses = responses[situation] ?: return getDefaultResponse()
        return situationResponses.random().let { response ->
            // Modify response based on evaluation
            when {
                evaluation > 2.0 -> response.copy(
                    text = "I sense weakness... $${response.text}",
                    emotion = BotEmotion.EXCITED
                )
                evaluation < -2.0 -> response.copy(
                    text = "You're stronger than expected... ${response.text}",
                    emotion = BotEmotion.EXCITED
                )
                else -> response
            }
        }
    }

    private fun getDefaultResponse() = BotResponse(
        text = "...",
        voiceResourceId = "neutral_1",
        emotion = BotEmotion.NEUTRAL
    )

    override fun isVoicePackInstalled(): Boolean {
        return voicePackManager.isVoicePackInstalled(voicePackId)
    }

    override suspend fun downloadVoicePack(): Result<Unit> {
        return voicePackManager.downloadVoicePack(voicePackId)
    }
} 