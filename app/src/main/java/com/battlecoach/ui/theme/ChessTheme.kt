package com.battlecoach.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
data class ChessBoardTheme(
    val lightSquareColor: Color,
    val darkSquareColor: Color,
    val selectedSquareColor: Color,
    val validMoveIndicatorColor: Color
)

val DefaultChessTheme = ChessBoardTheme(
    lightSquareColor = Color(0xFFEEEED2),
    darkSquareColor = Color(0xFF769656),
    selectedSquareColor = Color(0xFFBBCB44),
    validMoveIndicatorColor = Color(0x80BBCB44)
)

val DarkChessTheme = ChessBoardTheme(
    lightSquareColor = Color(0xFF4B4B4B),
    darkSquareColor = Color(0xFF1E1E1E),
    selectedSquareColor = Color(0xFF3E5E9C),
    validMoveIndicatorColor = Color(0x803E5E9C)
) 