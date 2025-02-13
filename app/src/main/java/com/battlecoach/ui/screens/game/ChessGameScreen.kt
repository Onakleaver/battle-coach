package com.battlecoach.ui.screens.game

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.battlecoach.ui.components.Chessboard
import com.battlecoach.ui.components.MoveHistory
import com.battlecoach.ui.theme.DefaultChessTheme

@Composable
fun ChessGameScreen(
    modifier: Modifier = Modifier,
    viewModel: ChessGameViewModel
) {
    var selectedSquare by remember { mutableStateOf<String?>(null) }
    
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Chessboard(
            modifier = Modifier.weight(1f),
            theme = DefaultChessTheme,
            selectedSquare = selectedSquare,
            validMoves = viewModel.validMoves,
            pieces = viewModel.pieces,
            onSquareSelected = { square ->
                if (selectedSquare == null) {
                    selectedSquare = square
                    viewModel.calculateValidMoves(square)
                } else {
                    viewModel.makeMove(selectedSquare!!, square)
                    selectedSquare = null
                }
            }
        )
        
        MoveHistory(
            moves = viewModel.moveHistory,
            currentMoveIndex = viewModel.currentMoveIndex,
            onMoveSelected = viewModel::jumpToMove
        )
    }
} 