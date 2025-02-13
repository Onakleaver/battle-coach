package com.battlecoach.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
data class ChessboardTheme(
    val lightSquareColor: Color,
    val darkSquareColor: Color,
    val selectedSquareColor: Color,
    val validMoveColor: Color,
    val lastMoveColor: Color,
    val checkSquareColor: Color
)

val DefaultChessboardTheme = ChessboardTheme(
    lightSquareColor = Color(0xFFEEEED2),
    darkSquareColor = Color(0xFF769656),
    selectedSquareColor = Color(0xFFBBCB44),
    validMoveColor = Color(0x80BBCB44),
    lastMoveColor = Color(0xFFCDA963),
    checkSquareColor = Color(0xFFDB4242)
)

val DarkChessboardTheme = ChessboardTheme(
    lightSquareColor = Color(0xFF4B4B4B),
    darkSquareColor = Color(0xFF1E1E1E),
    selectedSquareColor = Color(0xFF3E5E9C),
    validMoveColor = Color(0x803E5E9C),
    lastMoveColor = Color(0xFF5C4B3C),
    checkSquareColor = Color(0xFF8B2E2E)
) 