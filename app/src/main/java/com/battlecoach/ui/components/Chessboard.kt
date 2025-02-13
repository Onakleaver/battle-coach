package com.battlecoach.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import com.battlecoach.ui.theme.ChessBoardTheme
import com.battlecoach.ui.theme.DefaultChessTheme

@Composable
fun Chessboard(
    modifier: Modifier = Modifier,
    theme: ChessBoardTheme = DefaultChessTheme,
    onSquareSelected: (String) -> Unit = {},
    selectedSquare: String? = null,
    validMoves: List<String> = emptyList(),
    pieces: List<ChessPiece> = emptyList()
) {
    var boardSize by remember { mutableStateOf(IntSize.Zero) }
    val squareSize = boardSize.width / 8f

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .onSizeChanged { boardSize = it }
            .background(MaterialTheme.colorScheme.surface)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val file = ('a'.code + (offset.x / squareSize).toInt()).toChar()
                    val rank = 8 - (offset.y / squareSize).toInt()
                    val square = "$file$rank"
                    onSquareSelected(square)
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Draw board squares
            drawBoard(theme)
            
            // Draw selected square highlight
            selectedSquare?.let { square ->
                drawSelectedSquare(square, theme.selectedSquareColor, squareSize)
            }
            
            // Draw valid moves indicators
            validMoves.forEach { square ->
                drawValidMoveIndicator(square, theme.validMoveIndicatorColor, squareSize)
            }
            
            // Draw pieces
            pieces.forEach { piece ->
                drawPiece(piece, squareSize)
            }
        }
    }
}

private fun DrawScope.drawBoard(theme: ChessBoardTheme) {
    for (rank in 0..7) {
        for (file in 0..7) {
            val isLightSquare = (rank + file) % 2 == 0
            val color = if (isLightSquare) theme.lightSquareColor else theme.darkSquareColor
            
            drawRect(
                color = color,
                topLeft = Offset(file * size.width / 8, rank * size.height / 8),
                size = Size(size.width / 8, size.height / 8)
            )
        }
    }
}

private fun DrawScope.drawSelectedSquare(square: String, color: Color, squareSize: Float) {
    val file = square[0] - 'a'
    val rank = 8 - square[1].digitToInt()
    
    drawRect(
        color = color,
        topLeft = Offset(file * squareSize, rank * squareSize),
        size = Size(squareSize, squareSize)
    )
}

private fun DrawScope.drawValidMoveIndicator(square: String, color: Color, squareSize: Float) {
    val file = square[0] - 'a'
    val rank = 8 - square[1].digitToInt()
    val center = Offset(
        (file * squareSize) + (squareSize / 2),
        (rank * squareSize) + (squareSize / 2)
    )
    
    drawCircle(
        color = color,
        radius = squareSize / 4,
        center = center
    )
} 