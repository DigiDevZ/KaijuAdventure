package com.zo.kaijuadventure.presentation.play_screen

import com.zo.kaijuadventure.data.Choice
import com.zo.kaijuadventure.data.Scenes

data class PlayScreenState(
    val scene: Scenes = Scenes.Intro(""),
    val userChoices: List<Choice> = listOf()
)

sealed class SceneEvents {
    data object SceneDone : SceneEvents()
    //Can hold messages
    data object SceneAlert : SceneEvents()
    data object None : SceneEvents()
}