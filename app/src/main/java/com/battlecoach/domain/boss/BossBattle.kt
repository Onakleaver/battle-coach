package com.battlecoach.domain.boss

import com.battlecoach.domain.bot.AiPersonality
import com.battlecoach.domain.rank.PlayerRank

sealed class BossBattle(
    val rank: PlayerRank,
    val boss: AiPersonality,
    val requiredElo: Int,
    val description: String
) {
    object DRankBoss : BossBattle(
        rank = PlayerRank.D_RANK,
        boss = AiPersonality.SungJinWoo(),
        requiredElo = PlayerRank.D_RANK.minElo,
        description = "Prove yourself worthy of becoming a D-rank hunter!"
    )

    object CRankBoss : BossBattle(
        rank = PlayerRank.C_RANK,
        boss = AiPersonality.ChaeHaeIn(),
        requiredElo = PlayerRank.C_RANK.minElo,
        description = "Face the White Tiger Guild Master to advance!"
    )

    object BRankBoss : BossBattle(
        rank = PlayerRank.B_RANK,
        boss = AiPersonality.GoGunHee(),
        requiredElo = PlayerRank.B_RANK.minElo,
        description = "The Chairman will test your strength!"
    )

    object ARankBoss : BossBattle(
        rank = PlayerRank.A_RANK,
        boss = AiPersonality.ThomasAndre(),
        requiredElo = PlayerRank.A_RANK.minElo,
        description = "Defeat America's strongest hunter!"
    )

    companion object {
        fun getBossForRank(rank: PlayerRank): BossBattle? {
            return when (rank) {
                PlayerRank.D_RANK -> DRankBoss
                PlayerRank.C_RANK -> CRankBoss
                PlayerRank.B_RANK -> BRankBoss
                PlayerRank.A_RANK -> ARankBoss
                else -> null
            }
        }
    }
} 