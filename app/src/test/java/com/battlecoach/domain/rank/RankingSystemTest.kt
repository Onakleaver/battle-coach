package com.battlecoach.domain.rank

import org.junit.Test
import org.junit.Assert.*

class RankingSystemTest {
    private val rankingSystem = RankingSystem()

    @Test
    fun `test Elo calculation for equal strength players`() {
        val eloChange = rankingSystem.calculateEloChange(
            playerElo = 1500,
            opponentElo = 1500,
            result = GameResult.WIN
        )
        assertEquals(16, eloChange) // Expected K-factor/2 for equal players
    }

    @Test
    fun `test rank progression`() {
        // Test E to D rank progression
        val oldElo = PlayerRank.E_RANK.minElo + 100
        val newElo = PlayerRank.D_RANK.minElo + 50
        assertTrue(rankingSystem.isRankUp(oldElo, newElo))
        
        // Test no rank up within same rank
        assertFalse(rankingSystem.isRankUp(1500, 1600))
    }

    @Test
    fun `test rank progress calculation`() {
        val progress = rankingSystem.getProgressInRank(1100) // D-Rank
        assertTrue(progress in 0f..1f)
        
        // Test boundary conditions
        assertEquals(0f, rankingSystem.getProgressInRank(PlayerRank.D_RANK.minElo))
        assertTrue(rankingSystem.getProgressInRank(PlayerRank.C_RANK.minElo - 1) < 1f)
    }
} 