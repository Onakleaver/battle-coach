package com.battlecoach.domain.bot

import com.battlecoach.domain.engine.EngineLevel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AiDifficultyManager @Inject constructor() {
    fun calculateDifficulty(
        playerElo: Int,
        personality: AiPersonality,
        consecutiveWins: Int,
        consecutiveLosses: Int
    ): EngineLevel {
        // Base difficulty from Elo difference
        val eloDiff = personality.baseElo - playerElo
        
        // Adjust for win/loss streaks
        val streakAdjustment = (consecutiveWins * 100) - (consecutiveLosses * 100)
        val adjustedEloDiff = eloDiff - streakAdjustment

        return when {
            adjustedEloDiff > 500 -> EngineLevel.GRANDMASTER
            adjustedEloDiff > 300 -> EngineLevel.MASTER
            adjustedEloDiff > 100 -> EngineLevel.ADVANCED
            adjustedEloDiff > -100 -> EngineLevel.INTERMEDIATE
            else -> EngineLevel.BEGINNER
        }
    }

    fun getPersonalityForElo(playerElo: Int): AiPersonality {
        return when {
            playerElo >= 2500 -> AiPersonality.ThomasAndre()
            playerElo >= 2200 -> AiPersonality.ChaeHaeIn()
            playerElo >= 2000 -> AiPersonality.GoGunHee()
            else -> AiPersonality.SungJinWoo()
        }
    }
} 