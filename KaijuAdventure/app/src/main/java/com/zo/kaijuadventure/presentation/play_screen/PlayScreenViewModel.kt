package com.zo.kaijuadventure.presentation.play_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zo.kaijuadventure.data.Scenes
import com.zo.kaijuadventure.util.baseLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlayScreenViewModel : ViewModel() {

    private val _sceneEvents = MutableStateFlow<SceneEvents>(SceneEvents.None)
    val sceneEvents: StateFlow<SceneEvents> = _sceneEvents

    var state by mutableStateOf(PlayScreenState())
        private set

    fun onStart() {

    }

    fun onSceneDone() {
        _sceneEvents.value = SceneEvents.SceneDone
    }

    fun onSceneFinished() {
        _sceneEvents.value = SceneEvents.None

        transitionNextScene()
    }

    private fun transitionNextScene() {
        when (state.scene) {
            Scenes.Intro -> Scenes.EnterKaiju
            Scenes.EnterKaiju -> { }
        }
    }

    fun onDispose() {

    }
}