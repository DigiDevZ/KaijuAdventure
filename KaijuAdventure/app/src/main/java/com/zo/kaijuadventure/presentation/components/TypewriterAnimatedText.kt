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
    texts: List<String>,
    playAnimation: Boolean,
    onAnimationDone: () -> Unit
) {
    //Requirements
    // Cursor should be visible where text is being displayed
    var textIndex by remember {
        mutableIntStateOf(0)
    }
    var textToDisplay by remember {
        mutableStateOf(
            if (playAnimation) "" else texts.toString().replace(regex= """\[|\]""".toRegex(), replacement = ""))
    }

    LaunchedEffect(key1 = texts) {
        if (playAnimation) {
            delay(2000)
            texts.forEach { _ ->
                texts[textIndex].forEachIndexed { charIndex, _ ->
                    textToDisplay = texts[textIndex]
                        .substring(
                            startIndex = 0,
                            endIndex = charIndex + 1,
                        )
                    delay(160)
                }
                textIndex = textIndex++
                delay(1000)
            }
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
