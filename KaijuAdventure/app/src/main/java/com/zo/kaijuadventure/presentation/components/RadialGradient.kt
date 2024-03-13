package com.zo.kaijuadventure.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RadialGradientPulse(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    radius: Float
) {
    Canvas(modifier = modifier) {

        drawCircle(
            brush = Brush.radialGradient(
                colors = colors,
                center = center,
                radius = radius
            ),
            center = center,
            radius = radius
        )
    }
}

@Composable
fun AnimatedRadialGradientPulse() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val radius by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 300f, // Adjust this value based on the size you want
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing), // Duration and easing
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    RadialGradientPulse(
        modifier = Modifier.size(400.dp),// Adjust size as needed
        colors = listOf(Color.LightGray, Color.Gray, Color.DarkGray),
        radius = radius
    )
}

@Preview
@Composable
fun PreviewAnimatedRadialGradientPulse() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AnimatedRadialGradientPulse()
    }
}