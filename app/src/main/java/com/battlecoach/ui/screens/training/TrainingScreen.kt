package com.battlecoach.ui.screens.training

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TrainingScreen(
    viewModel: TrainingViewModel = hiltViewModel()
) {
    var selectedSection by remember { mutableStateOf(TrainingSection.OPENINGS) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TrainingNavigation(
            selectedSection = selectedSection,
            onSectionSelected = { selectedSection = it }
        )

        AnimatedContent(
            targetState = selectedSection,
            transitionSpec = {
                fadeIn() + slideInHorizontally() with 
                fadeOut() + slideOutHorizontally()
            }
        ) { section ->
            when (section) {
                TrainingSection.OPENINGS -> OpeningsTraining()
                TrainingSection.TACTICS -> TacticsTraining()
                TrainingSection.ENDGAMES -> EndgamesTraining()
                TrainingSection.ANALYSIS -> GameAnalysis()
            }
        }
    }
}

@Composable
private fun TrainingNavigation(
    selectedSection: TrainingSection,
    onSectionSelected: (TrainingSection) -> Unit
) {
    NavigationRail {
        TrainingSection.values().forEach { section ->
            NavigationRailItem(
                selected = section == selectedSection,
                onClick = { onSectionSelected(section) },
                icon = { Icon(section.icon, contentDescription = section.title) },
                label = { Text(section.title) }
            )
        }
    }
} 