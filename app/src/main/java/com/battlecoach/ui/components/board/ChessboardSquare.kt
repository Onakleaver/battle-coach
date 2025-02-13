package com.battlecoach.ui.components.board

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.battlecoach.ui.theme.ChessboardTheme

@Composable
fun ChessboardSquare(
    square: String,
    isLight: Boolean,
    theme: ChessboardTheme,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    isValidMove: Boolean = false,
    isLastMove: Boolean = false,
    isCheck: Boolean = false,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    val backgroundColor = when {
        isSelected -> theme.selectedSquareColor
        isValidMove -> theme.validMoveColor
        isLastMove -> theme.lastMoveColor
        isCheck -> theme.checkSquareColor
        isLight -> theme.lightSquareColor
        else -> theme.darkSquareColor
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
} 