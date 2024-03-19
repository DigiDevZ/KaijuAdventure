package com.zo.kaijuadventure.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun TypewriterAnimatedText(
    text: String,
    playAnimation: Boolean,
    onAnimationDone: () -> Unit
) {
    var textToDisplay by remember {
        mutableStateOf(
            if (playAnimation) "" else text.replace(regex= """[\[\]]""".toRegex(), replacement = ""))
    }

    LaunchedEffect(key1 = text) {
        if (playAnimation) {
            delay(1250)
            text.forEachIndexed { charIndex, _ ->
                textToDisplay = text.substring(
                    startIndex = 0,
                    endIndex = charIndex + 1,
                )
                delay(120)
            }
            delay(500)
            onAnimationDone()
        }
    }

    Text(
        modifier = Modifier
            .background(
                Color.DarkGray.copy(alpha = 0.85f),
                shape = RoundedCornerShape(corner = CornerSize(20.dp))
            )
            .padding(16.dp),
        text = textToDisplay,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        textAlign = TextAlign.Center
    )
}
