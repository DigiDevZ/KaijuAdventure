package com.zo.kaijuadventure.presentation.scenes

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.zo.kaijuadventure.data.model.StoryChoice
import com.zo.kaijuadventure.presentation.components.CenteredContentColumn
import com.zo.kaijuadventure.presentation.components.ChoicesPrompt
import com.zo.kaijuadventure.presentation.components.TypewriterAnimatedText
import com.zo.kaijuadventure.presentation.play_screen.SceneStates

@Composable
fun EncounterScene(
    storyText: String,
    choices: List<StoryChoice>,
    onSceneDone: (StoryChoice) -> Unit,
) {
    var typingAnimationDone by remember {
        mutableStateOf(false)
    }
    var sceneState by remember {
        mutableStateOf(SceneStates.Typing)
    }

    var choiceChosen by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = typingAnimationDone) {
        if (typingAnimationDone) {
            sceneState = SceneStates.AwaitingInput
            typingAnimationDone = false
        }
    }
    
    LaunchedEffect(key1 = storyText) {
        sceneState = SceneStates.Typing
        choiceChosen = false
    }

    AnimatedContent(
        sceneState, label = ""
    ) { enterKaijuSceneStates ->
        when (enterKaijuSceneStates) {
            SceneStates.Typing -> {
                CenteredContentColumn {
                    TypewriterAnimatedText(
                        text = storyText,
                        playAnimation = true,
                        onAnimationDone = {
                            typingAnimationDone = true
                        })
                }
            }
            SceneStates.AwaitingInput -> {
                CenteredContentColumn {
                    Spacer(modifier = Modifier.weight(0.5f))
                    TypewriterAnimatedText(
                        text = storyText,
                        playAnimation = false,
                        onAnimationDone = {}
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    AnimatedVisibility(visible = !choiceChosen) {
                        ChoicesPrompt(
                            choices = choices
                        ) { choice ->
                            choiceChosen = true
                            onSceneDone(choice)
                        }
                    }

                    Spacer(modifier = Modifier.weight(0.5f))
                }
            }
        }
    }
}