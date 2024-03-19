package com.zo.kaijuadventure.presentation.play_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.zo.kaijuadventure.R
import com.zo.kaijuadventure.data.StoryChoice
import com.zo.kaijuadventure.data.StoryNode
import com.zo.kaijuadventure.presentation.components.Background
import com.zo.kaijuadventure.presentation.scenes.EncounterScene
import com.zo.kaijuadventure.presentation.scenes.IntroScene
import com.zo.kaijuadventure.presentation.scenes.SimpleScene

@Composable
fun PlayScreen(
    viewModel: PlayScreenViewModel
) {
    val state = viewModel.state
    val sceneEvents = viewModel.sceneEvents.collectAsState().value

    Box(modifier = Modifier.fillMaxSize()) {
        Background(
            shakeScreen = sceneEvents == SceneEvents.SceneDone,
            kaijuEvent = sceneEvents as? SceneEvents.KaijuEvent,
            onKaijuIntroduced = viewModel::onKaijuIntroduced,
        ) {
            viewModel.onSceneFinished()
        }

        //Game Content
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SceneDisplay(
                storyNode = state.currentStoryNode,
                storyState = state.storyState,
                onSceneDone = { choice ->
                    viewModel.onSceneDone(choice)
                })
        }
    }
}

@Composable
fun SceneDisplay(
    storyNode: StoryNode?,
    storyState: StoryState,
    onSceneDone: (StoryChoice?) -> Unit,
) {
    when (storyState) {
        StoryState.Intro -> IntroScene(onIntroDone = { onSceneDone(null) })
        StoryState.Story -> storyNode?.let {
            EncounterScene(
                storyText = it.storyText,
                choices = it.choices
            ) { storyChoice ->
                onSceneDone(storyChoice)
            }
        } ?: Text(text = "Null StoryNode")

        StoryState.Ending -> SimpleScene(text = "${storyNode?.storyText}\n\n${stringResource(R.string.godzilla_ending)}") {
            onSceneDone(null)
        }

        StoryState.GameOver -> SimpleScene(text = stringResource(R.string.game_over_message))
    }
}