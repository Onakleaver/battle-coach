package com.battlecoach.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.core.view.WindowCompat
import com.battlecoach.ui.navigation.BattleCoachNavigation
import com.battlecoach.ui.theme.BattleCoachTheme
import dagger.hilt.android.AndroidEntryPoint
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContent {
            val systemUiController = rememberSystemUiController()
            val isDarkTheme = isSystemInDarkTheme()

            // Update system bars
            DisposableEffect(systemUiController, isDarkTheme) {
                systemUiController.setSystemBarsColor(
                    color = android.graphics.Color.TRANSPARENT,
                    darkIcons = !isDarkTheme
                )
                onDispose {}
            }

            BattleCoachTheme {
                BattleCoachNavigation()
            }
        }
    }
} 