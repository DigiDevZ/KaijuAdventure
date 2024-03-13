package com.zo.kaijuadventure.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun AnimatedWaveText(text: String, modifier: Modifier = Modifier) {
    Row(modifier) {
        //List will hold the animatable Y-offsets for each char
       val charOffsets = remember {
           List(text.length) { Animatable(0f) }
       }

        LaunchedEffect(key1 = text) {
            while (true) {
                charOffsets.forEachIndexed { index, animatable ->
                    delay(index * 5L)//Delay affects the speed of the wave
                    animatable.animateTo(
                        targetValue = -20f,
                        animationSpec = tween(durationMillis = 250)
                    )
                    animatable.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = 250)
                    )
                }
            }
        }

        text.forEachIndexed { index, c ->
            BasicText(
                text = c.toString(),
                modifier = Modifier.offset(y = charOffsets[index].value.dp),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}