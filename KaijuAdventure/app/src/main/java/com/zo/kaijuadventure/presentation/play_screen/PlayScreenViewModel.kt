package com.zo.kaijuadventure.presentation.play_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zo.kaijuadventure.data.Choice
import com.zo.kaijuadventure.data.EnterKaijuChoices
import com.zo.kaijuadventure.data.KaijuEncounter2Choices
import com.zo.kaijuadventure.data.KaijuEncounter3Choices
import com.zo.kaijuadventure.data.KaijuEncounter4Choices
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
            is Scenes.Intro -> state.copy(scene = Scenes.EnterKaiju(""))
            is Scenes.EnterKaiju -> {
                state.copy(scene = determineSubsceneFromChoices(0))
            }
            is Scenes.KaijuEncounter2 -> {
                state.copy(scene = determineSubsceneFromChoices(1))
            }
            is Scenes.KaijuEncounter3 -> {
                state.copy(scene = determineSubsceneFromChoices(2))
            }
            is Scenes.KaijuEncounter4 -> {
                state.copy(scene = determineSubsceneFromChoices(3))
            }
            is Scenes.Ending -> state.copy(scene = Scenes.GameOver())
            else -> { state }
        }
    }

    private fun determineSubsceneFromChoices(choiceIndex: Int) =
        when (state.userChoices[choiceIndex]) {
            is EnterKaijuChoices.Run -> Scenes.KaijuEncounter2.Scene2A("You run")
            is EnterKaijuChoices.Hide -> Scenes.KaijuEncounter2.Scene2B("You hide")

            is KaijuEncounter2Choices.Explore -> Scenes.KaijuEncounter3.Scene2A("You Explore")
            is KaijuEncounter2Choices.Watch -> Scenes.KaijuEncounter3.Scene2B("You Watch")

            is KaijuEncounter3Choices.ScreamBack -> Scenes.KaijuEncounter4.Scene2A("You Scream Back")
            is KaijuEncounter3Choices.StaySilent -> Scenes.KaijuEncounter4.Scene2B("You Stay Silent")

            is KaijuEncounter4Choices.Acceptance -> Scenes.Ending("You Accept The Inevitable")
            is KaijuEncounter4Choices.Fight -> Scenes.Ending("You Fight")
        }


    fun onDispose() {}
}