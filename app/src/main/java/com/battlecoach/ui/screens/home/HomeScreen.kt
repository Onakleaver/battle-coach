package com.battlecoach.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.battlecoach.ui.navigation.Screen

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Battle Coach",
            style = MaterialTheme.typography.headlineLarge
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(onClick = { navController.navigate(Screen.Game.route) }) {
            Text("Play Game")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = { navController.navigate(Screen.Training.route) }) {
            Text("Training Ground")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = { navController.navigate(Screen.Profile.route) }) {
            Text("Profile")
        }
    }
} 