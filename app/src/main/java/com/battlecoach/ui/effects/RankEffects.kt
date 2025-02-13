package com.battlecoach.ui.effects

import androidx.compose.animation.core.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

sealed class AuraEffect(
    val colors: List<Color>,
    val animationSpec: InfiniteRepeatableSpec<Float>
) {
    object NONE : AuraEffect(emptyList(), infiniteRepeatable(tween(0)))
    object E_RANK : AuraEffect(
        listOf(Color(0x40666666)),
        infiniteRepeatable(tween(2000))
    )
    object S_RANK : AuraEffect(
        listOf(
            Color(0x40FFD700),
            Color(0x40FF0000)
        ),
        infiniteRepeatable(
            animation = tween(3000),
            repeatMode = RepeatMode.Reverse
        )
    )
    // Add more rank effects...
}

fun DrawScope.drawAura(effect: AuraEffect, alpha: Float) {
    when (effect) {
        is AuraEffect.NONE -> Unit
        is AuraEffect.E_RANK -> {
            drawCircle(
                color = effect.colors.first(),
                radius = size.minDimension,
                alpha = alpha
            )
        }
        is AuraEffect.S_RANK -> {
            effect.colors.forEachIndexed { index, color ->
                drawCircle(
                    color = color,
                    radius = size.minDimension * (1f - index * 0.2f),
                    alpha = alpha
                )
            }
        }
    }
} 