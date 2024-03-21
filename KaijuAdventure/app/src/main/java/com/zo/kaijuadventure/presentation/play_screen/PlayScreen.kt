package com.zo.kaijuadventure.presentation.play_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zo.kaijuadventure.R
import com.zo.kaijuadventure.data.model.StoryChoice
import com.zo.kaijuadventure.data.model.StoryNode
import com.zo.kaijuadventure.data.model.mapBaseError
import com.zo.kaijuadventure.presentation.components.AnimatedWaveText
import com.zo.kaijuadventure.presentation.components.Background
import com.zo.kaijuadventure.presentation.scenes.EncounterScene
import com.zo.kaijuadventure.presentation.scenes.IntroScene
import com.zo.kaijuadventure.presentation.scenes.SimpleScene

@Composable
fun PlayScreen(
    viewModel: PlayScreenViewModel
) {
    val state = viewModel.state.collectAsState().value
    val sceneEvents = viewModel.sceneEvents.collectAsState().value

    DisposableEffect(key1 = Unit) {
        viewModel.onStart()
        onDispose {  }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.DarkGray)
    ) {
        when (state.gameState) {
            GameState.Menu -> MenuContent(
                onPlayGame = viewModel::onPlayGame,
            )
            GameState.Game -> GameContent(
                sceneEvents = sceneEvents,
                currentStoryNode = state.currentStoryNode,
                storyState = state.storyState,
                onKaijuIntroduced = viewModel::onKaijuIntroduced,
                onSceneFinished = viewModel::onSceneFinished,
                onSceneChoiceSubmitted = viewModel::onSceneChoiceSubmitted,
                onGameRestart = viewModel::onGameRestart
            )
        }

        if (state.uiError != null) {
            Column(
                modifier = Modifier
                    //A full screen blurred background would be nicer here
                    .background(
                        Color.Black,
                        shape = RoundedCornerShape(corner = CornerSize(20.dp))
                    )
                    .padding(48.dp)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = state.uiError.mapBaseError(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = viewModel::onClearUIError) {
                    Text(text = stringResource(R.string.retry))
                }
            }
        }
    }
}

@Composable
fun MenuContent(
    onPlayGame: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedWaveText(text = stringResource(R.string.kaiju_adventure))
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            modifier = Modifier
                .clickable {
                    onPlayGame()
                }
                .background(
                    Color.Green.copy(alpha = 0.75f),
                    shape = RoundedCornerShape(corner = CornerSize(20.dp))
                )
                .padding(16.dp),
            text = stringResource(R.string.start),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun GameContent(
    sceneEvents: SceneEvents,
    currentStoryNode: StoryNode?,
    storyState: StoryState,
    onKaijuIntroduced: () -> Unit,
    onSceneFinished: () -> Unit,
    onSceneChoiceSubmitted: (StoryChoice?) -> Unit,
    onGameRestart: () -> Unit
) {
    Background(
        shakeScreen = sceneEvents == SceneEvents.SceneChoiceSubmitted,
        kaijuEvent = sceneEvents as? SceneEvents.KaijuEvent,
        onKaijuIntroduced = onKaijuIntroduced,
        onScreenShakeFinished = onSceneFinished
    )

    //Game Content
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SceneDisplay(
            storyNode = currentStoryNode,
            storyState = storyState,
            onSceneChoiceSubmitted = onSceneChoiceSubmitted,
            onGameRestart = onGameRestart
        )
    }
}

@Composable
fun SceneDisplay(
    storyNode: StoryNode?,
    storyState: StoryState,
    onSceneChoiceSubmitted: (StoryChoice?) -> Unit,
    onGameRestart: () -> Unit,
) {
    when (storyState) {
        StoryState.Intro -> IntroScene(onIntroDone = { onSceneChoiceSubmitted(null) })
        StoryState.Story -> storyNode?.let {
            EncounterScene(
                storyText = it.storyText,
                choices = it.choices
            ) { storyChoice ->
                onSceneChoiceSubmitted(storyChoice)
            }
        }

        StoryState.Ending -> SimpleScene(text = "${storyNode?.storyText}\n\n${stringResource(R.string.godzilla_ending)}", onSceneDone = { onSceneChoiceSubmitted(null) })

        StoryState.GameOver -> SimpleScene(
            text = stringResource(R.string.game_over_message),
            onClick = onGameRestart
        )
    }
}