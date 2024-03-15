package com.zo.kaijuadventure.presentation.scenes

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.zo.kaijuadventure.data.Choice
import com.zo.kaijuadventure.data.SceneStates
import com.zo.kaijuadventure.data.Scenes
import com.zo.kaijuadventure.presentation.components.ChoicesPrompt
import com.zo.kaijuadventure.presentation.components.TypewriterAnimatedText

@Composable
fun EncounterScene(
    scene: Scenes,
    choices: List<Choice>,
    onSceneDone: (Choice) -> Unit,
) {
    val message = listOf(scene.dialogue)
    var typingAnimationDone by remember {
        mutableStateOf(false)
    }
    var sceneState by remember {
        mutableStateOf(SceneStates.Typing)
    }

    LaunchedEffect(key1 = typingAnimationDone) {
        if (typingAnimationDone) {
            sceneState = SceneStates.AwaitingInput
        }
    }

    AnimatedContent(
        sceneState, label = ""
    ) { enterKaijuSceneStates ->
        when (enterKaijuSceneStates) {
            SceneStates.Typing -> {
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
                            typingAnimationDone = true
                        })
                }
            }
            SceneStates.AwaitingInput -> {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Transparent),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(0.5f))
                    TypewriterAnimatedText(
                        texts = message,
                        playAnimation = false,
                        onAnimationDone = {}
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    ChoicesPrompt(
                        choices = choices
                    ) { choice ->
                        onSceneDone(choice)
                    }

                    Spacer(modifier = Modifier.weight(0.5f))
                }
            }
        }
    }
}