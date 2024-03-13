package com.zo.kaijuadventure.presentation.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun ContinousRadiatingRings(
    modifier: Modifier = Modifier,
    targetValue: Float
) {
    val rings = remember { mutableStateListOf<Animatable<Float, AnimationVector1D>>() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (true) {
                delay(650)
                val newRing = Animatable(0f)
                if (Random.nextInt(0, 10) >= 7) {
                    rings.add(newRing)
                    delay(500)
                }
                rings.add(newRing)

                launch {
                    newRing.animateTo(
                        targetValue = targetValue,
                        animationSpec = tween(1750, easing = LinearEasing)
                    )
                }

                rings.removeAll { it.value >= targetValue * 0.95 }
            }
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val center = Offset(size.width / 2, size.height / 2 )
        rings.forEach { animatable ->
            drawCircle(
                color = Color.LightGray.copy(alpha = (1f - (animatable.value / targetValue)).coerceAtLeast(0.01f)),// Fading effect
                center = center,
                radius = animatable.value,
                style = Stroke(width = 4.dp.toPx()) // Defines the ring thickness
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewContinousRadiatingRings() {
    ContinousRadiatingRings(targetValue = 250f)
}