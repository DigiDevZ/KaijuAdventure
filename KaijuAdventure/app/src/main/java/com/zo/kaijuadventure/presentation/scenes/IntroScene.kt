package com.zo.kaijuadventure.presentation.scenes

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.zo.kaijuadventure.presentation.components.CenteredContentColumn
import com.zo.kaijuadventure.presentation.components.ContinousRadiatingRings
import com.zo.kaijuadventure.presentation.components.TypewriterAnimatedText
import com.zo.kaijuadventure.presentation.play_screen.SceneStates

@Composable
fun IntroScene(
    onIntroDone: () -> Unit,
) {
    val message = "Welcome to the game.\n\nAre you ready?\n\nTap below."

    var animationDone by remember {
        mutableStateOf(false)
    }

    var sceneState by remember {
        mutableStateOf(SceneStates.Typing)
    }

    LaunchedEffect(key1 = animationDone) {
        if (animationDone) {
            sceneState = SceneStates.AwaitingInput
        }
    }

    AnimatedContent(
        sceneState, label = ""
    ) {
        when (it) {
            SceneStates.AwaitingInput -> {
                CenteredContentColumn {
                    Spacer(modifier = Modifier.weight(0.5f))
                    TypewriterAnimatedText(
                        text = message,
                        playAnimation = false,
                        onAnimationDone = {}
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    val interactionSource = remember { MutableInteractionSource() }
                    ContinousRadiatingRings(
                        targetValue = 300f,
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = interactionSource
                        ) { onIntroDone() }
                    )

                    Spacer(modifier = Modifier.weight(0.5f))
                }
            }

            SceneStates.Typing -> {
                CenteredContentColumn {
                    TypewriterAnimatedText(
                        text = message,
                        playAnimation = true,
                        onAnimationDone = {
                            animationDone = true
                        }
                    )
                }
            }
        }
    }

}