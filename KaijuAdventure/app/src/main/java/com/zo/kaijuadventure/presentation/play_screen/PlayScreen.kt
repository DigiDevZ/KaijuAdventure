package com.zo.kaijuadventure.presentation.play_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.zo.kaijuadventure.R
import com.zo.kaijuadventure.data.Choice
import com.zo.kaijuadventure.data.Scenes
import com.zo.kaijuadventure.data.enterKaijuChoices
import com.zo.kaijuadventure.data.kaijuEncounter2Choices
import com.zo.kaijuadventure.data.kaijuEncounter3Choices
import com.zo.kaijuadventure.data.kaijuEncounter4Choices
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
            SceneDisplay(scene = state.scene, onSceneDone = { choice ->
                viewModel.onSceneDone(choice)
            })
        }
    }
}

@Composable
fun SceneDisplay(
    scene: Scenes,
    onSceneDone: (Choice?) -> Unit,
) {
    when (scene) {
        is Scenes.Intro -> IntroScene(onIntroDone = { onSceneDone(null) })

        is Scenes.EnterKaiju -> EncounterScene(
            scene = scene,
            choices = enterKaijuChoices()
        ) { onSceneDone(it) }

        is Scenes.Encounter2 -> EncounterScene(
            scene = scene,
            choices = kaijuEncounter2Choices()
        ) { onSceneDone(it) }

        is Scenes.Encounter3 -> EncounterScene(
            scene = scene,
            choices = kaijuEncounter3Choices()
        ) { onSceneDone(it) }

        is Scenes.Encounter4 -> EncounterScene(
            scene = scene,
            choices = kaijuEncounter4Choices()
        ) { onSceneDone(it) }

        is Scenes.Ending -> SimpleScene(text = "${scene.dialogue}\n\n${stringResource(R.string.godzilla_ending)}") {
            onSceneDone(null)
        }

        is Scenes.GameOver -> SimpleScene(text = stringResource(R.string.game_over_message))
    }
}