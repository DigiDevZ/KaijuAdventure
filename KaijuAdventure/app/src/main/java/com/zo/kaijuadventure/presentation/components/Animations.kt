package com.zo.kaijuadventure.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector
import androidx.compose.animation.core.AnimationVector1D
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class KaijuAnimation(
    val offsetY: Animatable<Float, AnimationVector1D> = Animatable(initialValue = 0f),
    val offsetX: Animatable<Float, AnimationVector1D> = Animatable(initialValue = 0f),
) {
    suspend fun animation() {
        coroutineScope {
            launch {
                offsetY.animateTo(
                    targetValue = 0f,
                    animationSpec = jumpAnimation
                )
                delay(200)
            }
        }
    }
}