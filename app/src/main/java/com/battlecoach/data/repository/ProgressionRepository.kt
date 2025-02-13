package com.battlecoach.data.repository

import com.battlecoach.data.local.dao.ProgressDao
import com.battlecoach.data.local.entities.ProgressEntity
import com.battlecoach.domain.rank.GameResult
import com.battlecoach.domain.rank.PlayerRank
import com.battlecoach.domain.rank.RankingSystem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgressionRepository @Inject constructor(
    private val progressDao: ProgressDao,
    private val rankingSystem: RankingSystem
) {
    fun getPlayerProgress(username: String): Flow<PlayerProgress> {
        return progressDao.getProgressByUsername(username).map { entity ->
            PlayerProgress(
                currentElo = entity.currentElo,
                peakElo = entity.peakElo,
                gamesPlayed = entity.gamesPlayed,
                wins = entity.wins,
                losses = entity.losses,
                draws = entity.draws,
                currentRank = rankingSystem.getRankForElo(entity.currentElo),
                rankProgress = rankingSystem.getProgressInRank(entity.currentElo),
                bossesDefeated = entity.bossesDefeated
            )
        }
    }

    suspend fun updateProgress(
        username: String,
        gameResult: GameResult,
        opponentElo: Int
    ): PlayerProgress {
        val currentProgress = progressDao.getProgressByUsername(username).first()
        val eloChange = rankingSystem.calculateEloChange(
            playerElo = currentProgress.currentElo,
            opponentElo = opponentElo,
            result = gameResult
        )

        val newElo = currentProgress.currentElo + eloChange
        val isRankUp = rankingSystem.isRankUp(currentProgress.currentElo, newElo)

        val updatedProgress = currentProgress.copy(
            currentElo = newElo,
            peakElo = max(newElo, currentProgress.peakElo),
            gamesPlayed = currentProgress.gamesPlayed + 1,
            wins = currentProgress.wins + if (gameResult == GameResult.WIN) 1 else 0,
            losses = currentProgress.losses + if (gameResult == GameResult.LOSS) 1 else 0,
            draws = currentProgress.draws + if (gameResult == GameResult.DRAW) 1 else 0,
            bossesDefeated = currentProgress.bossesDefeated + if (isRankUp) 1 else 0
        )

        progressDao.updateProgress(updatedProgress)
        return getPlayerProgress(username).first()
    }
}

data class PlayerProgress(
    val currentElo: Int,
    val peakElo: Int,
    val gamesPlayed: Int,
    val wins: Int,
    val losses: Int,
    val draws: Int,
    val currentRank: PlayerRank,
    val rankProgress: Float,
    val bossesDefeated: Int
) 