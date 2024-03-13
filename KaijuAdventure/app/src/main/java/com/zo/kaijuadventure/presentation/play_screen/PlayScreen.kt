package com.zo.kaijuadventure.presentation.play_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.zo.kaijuadventure.data.Scenes
import com.zo.kaijuadventure.presentation.components.Background
import com.zo.kaijuadventure.presentation.scenes.IntroScene

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
            SceneDisplay(scene = state.scene, onIntroDone = {
                //TODO: Need to debug why this isn't triggering the scene transition
                viewModel.onSceneDone()
            })
        }
    }
}

@Composable
fun SceneDisplay(
    scene: Scenes,
    onIntroDone: () -> Unit,
) {
    when (scene) {
        Scenes.Intro -> IntroScene(onIntroDone = onIntroDone)
        Scenes.EnterKaiju -> {
            //TODO: Build out next scenes
            Text(text = "Enter Godzilla!!!")
        }
    }
}