package com.zo.kaijuadventure.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.zo.kaijuadventure.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun Background(
    shakeScreen: Boolean,
    onScreenShakeFinished: () -> Unit
) {
    val translationX = remember {
        Animatable(initialValue = 0f)
    }

    LaunchedEffect(key1 = shakeScreen) {
        if (shakeScreen) {
            val shakeAnimation: AnimationSpec<Float> = keyframes {
                durationMillis = 500
                -20f at 100
                20f at 200
                -20f at 300
                20f at 400
                0f at 500
            }

            launch {
                repeat(5) {
                    translationX.animateTo(
                        targetValue = 0f,
                        animationSpec = shakeAnimation
                    )
                    delay(500)
                }
            }

            translationX.snapTo(0f)

            onScreenShakeFinished()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset(x = translationX.value.roundToInt().dp)
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.background_art),
            contentDescription = "Night time City",
            contentScale = ContentScale.FillBounds
        )
    }
}

@Preview
@Composable
fun PreviewBackground() {
    Box(modifier = Modifier.fillMaxSize()) {
        Background(false) {}
    }
}