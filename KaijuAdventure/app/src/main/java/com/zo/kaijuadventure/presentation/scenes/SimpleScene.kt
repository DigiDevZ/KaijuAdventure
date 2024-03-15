package com.zo.kaijuadventure.presentation.scenes

import androidx.compose.runtime.Composable
import com.zo.kaijuadventure.presentation.components.CenteredContentColumn
import com.zo.kaijuadventure.presentation.components.TypewriterAnimatedText

@Composable
fun SimpleScene(
    text: String,
    onSceneDone: (() -> Unit)? = null
) {
    CenteredContentColumn {
        TypewriterAnimatedText(
            text = text,
            playAnimation = true,
            onAnimationDone = { onSceneDone?.invoke() }
        )
    }
}