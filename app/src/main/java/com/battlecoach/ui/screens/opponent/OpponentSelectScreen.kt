package com.battlecoach.ui.screens.opponent

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OpponentSelectScreen(
    onOpponentSelected: (AiPersonality) -> Unit,
    playerElo: Int
) {
    val availableOpponents = remember(playerElo) {
        listOf(
            AiPersonality.SungJinWoo(),
            AiPersonality.ChaeHaeIn(),
            AiPersonality.GoGunHee(),
            AiPersonality.ThomasAndre()
        ).filter { it.baseElo <= playerElo + 500 }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Choose Your Opponent",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        availableOpponents.forEach { opponent ->
            OpponentCard(
                opponent = opponent,
                onClick = { onOpponentSelected(opponent) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun OpponentCard(
    opponent: AiPersonality,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = opponent.name,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = opponent.title,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Elo: ${opponent.baseElo}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
} 