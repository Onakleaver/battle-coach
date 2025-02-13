package com.battlecoach.ui.screens.battlefield

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BattlefieldScreen(
    viewModel: BattlefieldViewModel = hiltViewModel()
) {
    val gameState by viewModel.gameState.collectAsState()
    val aiResponse by viewModel.aiResponse.collectAsState()

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        // Left panel - Opponent info
        OpponentPanel(
            opponent = gameState.opponent,
            aiResponse = aiResponse,
            modifier = Modifier
                .width(250.dp)
                .fillMaxHeight()
        )

        // Center - Chessboard
        Box(
            modifier = Modifier.weight(1f)
        ) {
            Chessboard(
                pieces = gameState.pieces,
                theme = gameState.boardTheme,
                flipped = gameState.isFlipped,
                selectedSquare = gameState.selectedSquare,
                validMoves = gameState.validMoves,
                lastMove = gameState.lastMove,
                checkSquare = gameState.checkSquare,
                onSquareClick = viewModel::onSquareClick
            )
        }

        // Right panel - Move history
        MoveHistory(
            moves = gameState.moves,
            currentMoveIndex = gameState.currentMoveIndex,
            onMoveSelected = viewModel::onMoveSelected,
            modifier = Modifier
                .width(250.dp)
                .fillMaxHeight()
        )
    }
}

@Composable
private fun OpponentPanel(
    opponent: AiPersonality,
    aiResponse: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        PlayerAvatar(
            avatarData = opponent.avatarData,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = opponent.name,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = opponent.title,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // AI Response bubble with animation
        aiResponse?.let { response ->
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = response,
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
} 