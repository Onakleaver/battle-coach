package com.battlecoach.domain.game

data class TimeControl(
    val initialTime: Int, // in seconds
    val increment: Int, // in seconds
    val type: TimeControlType
) {
    companion object {
        val BLITZ_5_0 = TimeControl(300, 0, TimeControlType.BLITZ)
        val BLITZ_3_2 = TimeControl(180, 2, TimeControlType.BLITZ)
        val BLITZ_3_0 = TimeControl(180, 0, TimeControlType.BLITZ)
        
        val RAPID_10_0 = TimeControl(600, 0, TimeControlType.RAPID)
        val RAPID_15_10 = TimeControl(900, 10, TimeControlType.RAPID)
        val RAPID_30_0 = TimeControl(1800, 0, TimeControlType.RAPID)
        
        val CLASSICAL_45_15 = TimeControl(2700, 15, TimeControlType.CLASSICAL)
        val CLASSICAL_60_0 = TimeControl(3600, 0, TimeControlType.CLASSICAL)
    }
}

enum class TimeControlType {
    BLITZ, RAPID, CLASSICAL
} 