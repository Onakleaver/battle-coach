package com.battlecoach.ui.components.board

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.battlecoach.domain.model.ChessMove

@Composable
fun MoveHistory(
    moves: List<ChessMove>,
    currentMoveIndex: Int,
    onMoveSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
            .width(200.dp)
            .padding(8.dp)
    ) {
        items(moves.chunked(2)) { movePair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val moveNumber = (moves.indexOf(movePair[0]) / 2) + 1
                Text(
                    text = "$moveNumber.",
                    modifier = Modifier.width(32.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
                
                movePair.forEachIndexed { index, move ->
                    val moveIndex = (moveNumber - 1) * 2 + index
                    TextButton(
                        onClick = { onMoveSelected(moveIndex) },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = if (moveIndex == currentMoveIndex) 
                                MaterialTheme.colorScheme.primary 
                            else 
                                MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text(move.notation)
                    }
                }
            }
        }
    }
} 