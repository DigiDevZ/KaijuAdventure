package com.zo.kaijuadventure.presentation.scenes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.zo.kaijuadventure.presentation.components.TypewriterAnimatedText

@Composable
fun SimpleScene(
    text: String,
    onSceneDone: (() -> Unit)? = null
) {
    val message = listOf(text)
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TypewriterAnimatedText(
            texts = message,
            playAnimation = true,
            onAnimationDone = {
                onSceneDone?.invoke()
            }
        )
    }
}