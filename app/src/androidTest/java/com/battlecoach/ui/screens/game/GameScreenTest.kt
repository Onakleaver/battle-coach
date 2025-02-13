package com.battlecoach.ui.screens.game

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.battlecoach.domain.game.TimeControl
import org.junit.Rule
import org.junit.Test

class GameScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testGameClockDisplay() {
        composeTestRule.setContent {
            GameClock(
                timeMillis = 300000, // 5 minutes
                isActive = true
            )
        }

        composeTestRule.onNodeWithText("5:00").assertExists()
    }

    @Test
    fun testChessboardInteraction() {
        composeTestRule.setContent {
            GameScreen()
        }

        // Test piece selection
        composeTestRule.onNodeWithTag("square_e2")
            .performClick()
        
        // Verify valid moves are highlighted
        composeTestRule.onNodeWithTag("square_e3")
            .assertHasClickAction()
            .assertIsEnabled()
    }
} 