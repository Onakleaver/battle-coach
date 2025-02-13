package com.battlecoach.domain.bot

import com.battlecoach.data.repository.GameRepository
import com.battlecoach.data.repository.ProgressionRepository
import com.battlecoach.domain.rank.RankingSystem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpponentSelector @Inject constructor(
    private val progressionRepository: ProgressionRepository,
    private val gameRepository: GameRepository,
    private val rankingSystem: RankingSystem
) {
    suspend fun suggestOpponents(username: String): List<AiOpponent> {
        val progress = progressionRepository.getPlayerProgress(username).first()
        val recentGames = gameRepository.getOfflineGames().first().take(10)
        
        // Calculate performance metrics
        val recentPerformance = recentGames.map { it.result }.let { results ->
            results.count { it == "1-0" || it == "0-1" }.toFloat() / results.size
        }
        
        // Generate opponent suggestions
        return listOf(
            // Slightly weaker opponent for warmup
            createOpponent(progress.currentElo - 200, recentPerformance),
            // Equal opponent for fair challenge
            createOpponent(progress.currentElo, recentPerformance),
            // Stronger opponent for growth
            createOpponent(progress.currentElo + 200, recentPerformance)
        )
    }

    private fun createOpponent(targetElo: Int, recentPerformance: Float): AiOpponent {
        val personality = when {
            targetElo >= 2500 -> AiPersonality.ThomasAndre()
            targetElo >= 2200 -> AiPersonality.ChaeHaeIn()
            targetElo >= 2000 -> AiPersonality.GoGunHee()
            else -> AiPersonality.SungJinWoo()
        }

        // Adjust difficulty based on recent performance
        val adjustedElo = when {
            recentPerformance > 0.7 -> targetElo + 100 // Player doing very well
            recentPerformance < 0.3 -> targetElo - 100 // Player struggling
            else -> targetElo
        }

        return AiOpponent(
            personality = personality,
            targetElo = adjustedElo,
            timeControl = null // Use player's preferred time control
        )
    }
}

data class AiOpponent(
    val personality: AiPersonality,
    val targetElo: Int,
    val timeControl: TimeControl?
) 