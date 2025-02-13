package com.battlecoach.domain.model

enum class PieceType {
    KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN
}

enum class PieceColor {
    WHITE, BLACK
}

data class ChessPiece(
    val type: PieceType,
    val color: PieceColor,
    val position: String // e.g., "e4"
)

data class ChessMove(
    val from: String,
    val to: String,
    val piece: ChessPiece,
    val capturedPiece: ChessPiece? = null,
    val isCheck: Boolean = false,
    val isCheckmate: Boolean = false,
    val notation: String // e.g., "e4", "Nxf6+", "O-O"
) 