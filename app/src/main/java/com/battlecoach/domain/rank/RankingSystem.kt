package com.battlecoach.domain.rank

import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.round

@Singleton
class RankingSystem @Inject constructor() {
    fun calculateEloChange(
        playerElo: Int,
        opponentElo: Int,
        result: GameResult,
        kFactor: Int = 32
    ): Int {
        val expectedScore = 1.0 / (1.0 + 10.0.pow((opponentElo - playerElo) / 400.0))
        val actualScore = when (result) {
            GameResult.WIN -> 1.0
            GameResult.LOSS -> 0.0
            GameResult.DRAW -> 0.5
        }
        
        return round(kFactor * (actualScore - expectedScore)).toInt()
    }

    fun getRankForElo(elo: Int): PlayerRank {
        return when {
            elo >= PlayerRank.S_RANK.minElo -> PlayerRank.S_RANK
            elo >= PlayerRank.A_RANK.minElo -> PlayerRank.A_RANK
            elo >= PlayerRank.B_RANK.minElo -> PlayerRank.B_RANK
            elo >= PlayerRank.C_RANK.minElo -> PlayerRank.C_RANK
            elo >= PlayerRank.D_RANK.minElo -> PlayerRank.D_RANK
            else -> PlayerRank.E_RANK
        }
    }

    fun isRankUp(oldElo: Int, newElo: Int): Boolean {
        return getRankForElo(oldElo) != getRankForElo(newElo) &&
               newElo > oldElo
    }

    fun getProgressInRank(elo: Int): Float {
        val currentRank = getRankForElo(elo)
        val nextRank = PlayerRank.values().getOrNull(currentRank.ordinal + 1)
        
        return if (nextRank != null) {
            (elo - currentRank.minElo).toFloat() / (nextRank.minElo - currentRank.minElo)
        } else {
            1f
        }
    }
} 