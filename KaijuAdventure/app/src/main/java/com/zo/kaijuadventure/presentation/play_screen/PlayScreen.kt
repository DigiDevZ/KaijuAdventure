package com.zo.kaijuadventure.presentation.play_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.zo.kaijuadventure.data.Choice
import com.zo.kaijuadventure.data.Scenes
import com.zo.kaijuadventure.presentation.components.Background
import com.zo.kaijuadventure.presentation.scenes.Ending
import com.zo.kaijuadventure.presentation.scenes.EnterKaijuScene
import com.zo.kaijuadventure.presentation.scenes.GameOver
import com.zo.kaijuadventure.presentation.scenes.IntroScene
import com.zo.kaijuadventure.presentation.scenes.KaijuEncounter2Scene
import com.zo.kaijuadventure.presentation.scenes.KaijuEncounter3Scene
import com.zo.kaijuadventure.presentation.scenes.KaijuEncounter4Scene

@Composable
fun PlayScreen(
    viewModel: PlayScreenViewModel
) {
    val state = viewModel.state
    val sceneEvents = viewModel.sceneEvents.collectAsState().value

    DisposableEffect(key1 = viewModel) {
        viewModel.onStart()
        onDispose { viewModel.onDispose() }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        Background(shakeScreen = sceneEvents == SceneEvents.SceneDone) {
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
        is Scenes.EnterKaiju -> EnterKaijuScene(onSceneDone = onSceneDone)
        is Scenes.KaijuEncounter2 -> KaijuEncounter2Scene(scene = scene) { onSceneDone(it) }
        is Scenes.KaijuEncounter3 -> KaijuEncounter3Scene(scene = scene) { onSceneDone(it) }
        is Scenes.KaijuEncounter4 -> KaijuEncounter4Scene(scene = scene) { onSceneDone(it) }
        is Scenes.Ending -> Ending { onSceneDone(null) }
        is Scenes.GameOver -> GameOver()
    }
}