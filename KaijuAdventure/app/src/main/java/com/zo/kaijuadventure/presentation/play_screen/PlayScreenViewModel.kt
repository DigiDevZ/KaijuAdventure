package com.zo.kaijuadventure.presentation.play_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zo.kaijuadventure.data.Choice
import com.zo.kaijuadventure.data.Scenes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlayScreenViewModel : ViewModel() {

    private val _sceneEvents = MutableStateFlow<SceneEvents>(SceneEvents.None)
    val sceneEvents: StateFlow<SceneEvents> = _sceneEvents

    var state by mutableStateOf(PlayScreenState())
        private set

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
            is Scenes.Intro -> state.copy(scene = Scenes.EnterKaiju("What were those rumbles!!!\n\nA Kaiju...oh..no..it's....\n\nGodzilla!!!!!!"))
            is Scenes.EnterKaiju -> {
                state.copy(scene = determineSubsceneFromChoices(0))
            }
            is Scenes.Encounter2 -> {
                state.copy(scene = determineSubsceneFromChoices(1))
            }
            is Scenes.Encounter3 -> {
                state.copy(scene = determineSubsceneFromChoices(2))
            }
            is Scenes.Encounter4 -> {
                state.copy(scene = determineSubsceneFromChoices(3))
            }
            is Scenes.Ending -> state.copy(scene = Scenes.GameOver())
            else -> { state }
        }
    }

    private fun determineSubsceneFromChoices(choiceIndex: Int) =
        when (state.userChoices[choiceIndex]) {
            Choice.Run -> Scenes.Encounter2("You run\n\nGodzilla continues moving through the city\n\nAs if he is chasing you through the city streets\n\nYou make it to a building plaza")
            Choice.Hide -> Scenes.Encounter2("You hide\n\nGodzilla continues moving through the city\n\nYou can feel the tremblings of a falling building\n\nYou are still in your office")

            Choice.Explore -> Scenes.Encounter3("You Explore\n\nThe rumbling continues as you search your surroundings to see where Godzilla went\n\nSuddenly..he is right in front of you...and roars")
            Choice.Watch -> Scenes.Encounter3("You Watch\n\nAs Godzilla moves through the city...he stops and then turns directly towards you...and roars")

            Choice.ScreamBack -> Scenes.Encounter4("You Scream Back\n\nGodzilla doesn't budge...he watches you..then leans closer\n\nYou see a blue aura form around Godzilla..his beam is charging")
            Choice.StaySilent -> Scenes.Encounter4("You Stay Silent\n\nGodzilla leans in closer to examine you...\n\nYou see a blue aura form around Godzilla..his beam is charging")

            Choice.Acceptance -> Scenes.Ending("You Accept The Inevitable...\n\nThe aura grows stronger, until suddenly you hear his roar...then silence\n\n")
            Choice.Fight -> Scenes.Ending("You Fight\n\nWhile charging forward towards the behemoth...the blue aura disappears..silence\n\nGodzilla roars and the shockwave sends you flying back\n\n")
        }
}