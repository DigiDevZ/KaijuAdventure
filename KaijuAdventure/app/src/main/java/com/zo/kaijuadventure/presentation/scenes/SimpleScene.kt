package com.zo.kaijuadventure.presentation.scenes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.zo.kaijuadventure.presentation.components.CenteredContentColumn
import com.zo.kaijuadventure.presentation.components.ContinousRadiatingRings
import com.zo.kaijuadventure.presentation.components.TypewriterAnimatedText

@Composable
fun SimpleScene(
    text: String,
    onSceneDone: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    CenteredContentColumn {
        TypewriterAnimatedText(
            text = text,
            playAnimation = true,
            onAnimationDone = { onSceneDone?.invoke() }
        )


        onClick?.let {
            Spacer(modifier = Modifier.weight(1f))

            val interactionSource = remember { MutableInteractionSource() }
            ContinousRadiatingRings(
                targetValue = 300f,
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) { onClick() }
            )

            Spacer(modifier = Modifier.weight(0.5f))
        }
    }
}