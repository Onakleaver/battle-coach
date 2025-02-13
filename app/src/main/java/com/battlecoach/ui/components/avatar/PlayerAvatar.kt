package com.battlecoach.ui.components.avatar

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp

@Composable
fun PlayerAvatar(
    avatarData: AvatarData,
    modifier: Modifier = Modifier,
    showAura: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition()
    val auraAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier
            .size(120.dp)
            .drawBehind {
                if (showAura) {
                    drawAura(avatarData.auraEffect, auraAlpha)
                }
            }
    ) {
        AvatarImage(
            baseImage = avatarData.baseImage,
            clothing = avatarData.clothing,
            accessories = avatarData.accessories
        )
    }
}

data class AvatarData(
    val baseImage: Int,
    val clothing: Int,
    val accessories: List<Int>,
    val auraEffect: AuraEffect,
    val rankBadge: Int
) 