package com.zo.kaijuadventure.presentation.play_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zo.kaijuadventure.data.Choice
import com.zo.kaijuadventure.data.EnterKaijuChoices
import com.zo.kaijuadventure.data.Scenes
import com.zo.kaijuadventure.util.baseLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlayScreenViewModel : ViewModel() {

    private val _sceneEvents = MutableStateFlow<SceneEvents>(SceneEvents.None)
    val sceneEvents: StateFlow<SceneEvents> = _sceneEvents

    var state by mutableStateOf(PlayScreenState())
        private set

    fun onStart() {}

    fun onSceneDone(choice: Choice? = null) {
        _sceneEvents.value = SceneEvents.SceneDone
        choice?.let { recordChoice(it) }
    }

    fun onSceneFinished() {
        _sceneEvents.value = SceneEvents.None

        transitionNextScene()
    }

    private fun recordChoice(choice: Choice) {
        val userChoices = state.userChoices.toMutableList()
        userChoices.add(choice)
        state = state.copy(userChoices = userChoices)
    }

    private fun transitionNextScene() {
        state = when (state.scene) {
            is Scenes.Intro -> state.copy(scene = Scenes.EnterKaiju())
            is Scenes.EnterKaiju -> {
                state.copy(scene = determineSubsceneFromChoices(0))
            }
            is Scenes.KaijuEncounter2 -> TODO()
            is Scenes.KaijuEncounter3 -> TODO()
            is Scenes.KaijuEncounter4 -> TODO()
            is Scenes.Ending -> TODO()
        }
    }

    private fun determineSubsceneFromChoices(choiceIndex: Int) =
        when (state.userChoices[choiceIndex]) {
            is EnterKaijuChoices.Run -> Scenes.KaijuEncounter2.Scene2A("You run")
            is EnterKaijuChoices.Hide -> Scenes.KaijuEncounter2.Scene2B("You hide")
        }


    fun onDispose() {}
}