package com.battlecoach.ui.screens.game

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.battlecoach.ui.components.board.Chessboard
import com.battlecoach.ui.components.board.MoveHistory
import com.battlecoach.ui.theme.DefaultChessboardTheme

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = hiltViewModel()
) {
    val gameState by viewModel.gameState.collectAsState()
    val settings by viewModel.settings.collectAsState()
    val timeControl by viewModel.timeControl.collectAsState()

    Column {
        // Time controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GameClock(
                timeMillis = gameState.blackTimeMillis,
                isActive = gameState.sideToMove == PieceColor.BLACK
            )
            
            GameClock(
                timeMillis = gameState.whiteTimeMillis,
                isActive = gameState.sideToMove == PieceColor.WHITE
            )
        }

        // Game board and controls
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Chessboard(
                pieces = gameState.pieces,
                modifier = Modifier.weight(1f),
                theme = if (settings.isDarkTheme) DarkChessboardTheme else DefaultChessboardTheme,
                flipped = settings.boardFlipped,
                selectedSquare = gameState.selectedSquare,
                validMoves = gameState.validMoves,
                lastMove = gameState.lastMove,
                checkSquare = gameState.checkSquare,
                onSquareClick = viewModel::onSquareClick
            )
            
            MoveHistory(
                moves = gameState.moves,
                currentMoveIndex = gameState.currentMoveIndex,
                onMoveSelected = viewModel::onMoveSelected
            )
        }
    }
}

@Composable
private fun GameClock(
    timeMillis: Long,
    isActive: Boolean
) {
    val minutes = timeMillis / 60000
    val seconds = (timeMillis % 60000) / 1000

    Surface(
        color = if (isActive) 
            MaterialTheme.colorScheme.primaryContainer
        else 
            MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = "%d:%02d".format(minutes, seconds),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
} 