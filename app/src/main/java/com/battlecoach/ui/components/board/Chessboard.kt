package com.battlecoach.ui.components.board

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.battlecoach.domain.model.ChessPiece
import com.battlecoach.ui.theme.ChessboardTheme
import com.battlecoach.ui.theme.DefaultChessboardTheme

@Composable
fun Chessboard(
    pieces: Map<String, ChessPiece>,
    modifier: Modifier = Modifier,
    theme: ChessboardTheme = DefaultChessboardTheme,
    flipped: Boolean = false,
    selectedSquare: String? = null,
    validMoves: Set<String> = emptySet(),
    lastMove: Pair<String, String>? = null,
    checkSquare: String? = null,
    onSquareClick: (String) -> Unit
) {
    Column(modifier = modifier) {
        for (rank in if (flipped) 1..8 else 8 downTo 1) {
            Row {
                for (file in if (flipped) 'h' downTo 'a' else 'a'..'h') {
                    val square = "$file$rank"
                    val isLight = (file.code - 'a'.code + rank) % 2 == 0
                    
                    ChessboardSquare(
                        square = square,
                        isLight = isLight,
                        theme = theme,
                        modifier = Modifier.weight(1f),
                        isSelected = square == selectedSquare,
                        isValidMove = square in validMoves,
                        isLastMove = lastMove?.let { square in listOf(it.first, it.second) } ?: false,
                        isCheck = square == checkSquare,
                        onClick = { onSquareClick(square) }
                    ) {
                        pieces[square]?.let { piece ->
                            ChessPiece(
                                type = piece.type,
                                color = piece.color,
                                modifier = Modifier.fillMaxSize(0.8f)
                            )
                        }
                    }
                }
            }
        }
    }
} 