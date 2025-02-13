package com.battlecoach.ui.theme

import androidx.compose.ui.graphics.Color

enum class PlayerRank(val title: String, val minElo: Int) {
    E_RANK("E-Rank Hunter", 0),
    D_RANK("D-Rank Hunter", 1000),
    C_RANK("C-Rank Hunter", 1400),
    B_RANK("B-Rank Hunter", 1700),
    A_RANK("A-Rank Hunter", 2000),
    S_RANK("S-Rank Hunter", 2300),
    NATIONAL("National Level Hunter", 2500),
    MONARCH("Shadow Monarch", 2700)
}

fun getRankTheme(elo: Int): ChessboardTheme {
    return when {
        elo >= PlayerRank.MONARCH.minElo -> MonarchTheme
        elo >= PlayerRank.NATIONAL.minElo -> NationalTheme
        elo >= PlayerRank.S_RANK.minElo -> SRankTheme
        elo >= PlayerRank.A_RANK.minElo -> ARankTheme
        elo >= PlayerRank.B_RANK.minElo -> BRankTheme
        elo >= PlayerRank.C_RANK.minElo -> CRankTheme
        elo >= PlayerRank.D_RANK.minElo -> DRankTheme
        else -> ERankTheme
    }
}

val MonarchTheme = ChessboardTheme(
    lightSquareColor = Color(0xFF2B2B3B),
    darkSquareColor = Color(0xFF1A1A2B),
    selectedSquareColor = Color(0xFF3E5E9C),
    validMoveColor = Color(0x803E5E9C),
    lastMoveColor = Color(0xFF5C4B3C),
    checkSquareColor = Color(0xFF8B2E2E)
)

val SRankTheme = ChessboardTheme(
    lightSquareColor = Color(0xFF2D3B2B),
    darkSquareColor = Color(0xFF1B2B1A),
    selectedSquareColor = Color(0xFF4B7E3E),
    validMoveColor = Color(0x804B7E3E),
    lastMoveColor = Color(0xFF6C4B3C),
    checkSquareColor = Color(0xFF8B2E2E)
)

val ARankTheme = ChessboardTheme(
    lightSquareColor = Color(0xFF3B2D2D),
    darkSquareColor = Color(0xFF2B1B1B),
    selectedSquareColor = Color(0xFF7E4B3E),
    validMoveColor = Color(0x807E4B3E),
    lastMoveColor = Color(0xFF4B3C6C),
    checkSquareColor = Color(0xFF8B2E2E)
)

val BRankTheme = ChessboardTheme(
    lightSquareColor = Color(0xFF2D2D3B),
    darkSquareColor = Color(0xFF1B1B2B),
    selectedSquareColor = Color(0xFF3E4B7E),
    validMoveColor = Color(0x803E4B7E),
    lastMoveColor = Color(0xFF6C4B3C),
    checkSquareColor = Color(0xFF8B2E2E)
)

// Define other rank themes... 