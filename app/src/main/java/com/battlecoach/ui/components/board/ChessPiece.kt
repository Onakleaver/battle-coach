package com.battlecoach.ui.components.board

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.battlecoach.R
import com.battlecoach.domain.model.PieceType
import com.battlecoach.domain.model.PieceColor

@Composable
fun ChessPiece(
    type: PieceType,
    color: PieceColor,
    modifier: Modifier = Modifier
) {
    val resourceId = when (color) {
        PieceColor.WHITE -> when (type) {
            PieceType.KING -> R.drawable.white_king
            PieceType.QUEEN -> R.drawable.white_queen
            PieceType.ROOK -> R.drawable.white_rook
            PieceType.BISHOP -> R.drawable.white_bishop
            PieceType.KNIGHT -> R.drawable.white_knight
            PieceType.PAWN -> R.drawable.white_pawn
        }
        PieceColor.BLACK -> when (type) {
            PieceType.KING -> R.drawable.black_king
            PieceType.QUEEN -> R.drawable.black_queen
            PieceType.ROOK -> R.drawable.black_rook
            PieceType.BISHOP -> R.drawable.black_bishop
            PieceType.KNIGHT -> R.drawable.black_knight
            PieceType.PAWN -> R.drawable.black_pawn
        }
    }

    Image(
        painter = painterResource(id = resourceId),
        contentDescription = "${color.name.lowercase()} ${type.name.lowercase()}",
        modifier = modifier
    )
} 