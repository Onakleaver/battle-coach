package com.battlecoach.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalBattleCoachColors = staticCompositionLocalOf { BattleCoachColors() }
private val LocalBattleCoachEffects = staticCompositionLocalOf { BattleCoachEffects() }

@Composable
fun BattleCoachTheme(
    darkTheme: Boolean = true,
    playerRank: PlayerRank = PlayerRank.E_RANK,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme
    val effects = getRankEffects(playerRank)

    CompositionLocalProvider(
        LocalBattleCoachColors provides colors,
        LocalBattleCoachEffects provides effects
    ) {
        MaterialTheme(
            colorScheme = colors.toMaterialColors(),
            typography = Typography,
            content = content
        )
    }
}

data class BattleCoachColors(
    val background: Color = Color(0xFF1A1A1A),
    val surface: Color = Color(0xFF2D2D2D),
    val rankAccent: Color = Color(0xFF3E5E9C),
    val aura: Color = Color(0x403E5E9C),
    val victory: Color = Color(0xFF4CAF50),
    val defeat: Color = Color(0xFFE53935)
)

data class BattleCoachEffects(
    val auraEffect: AuraEffect = AuraEffect.NONE,
    val victoryEffect: VictoryEffect = VictoryEffect.NONE,
    val boardStyle: BoardStyle = BoardStyle.STANDARD
) 