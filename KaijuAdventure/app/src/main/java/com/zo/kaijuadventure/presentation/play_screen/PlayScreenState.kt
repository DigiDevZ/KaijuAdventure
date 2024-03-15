package com.zo.kaijuadventure.presentation.play_screen

import com.zo.kaijuadventure.data.Choice
import com.zo.kaijuadventure.data.Scenes

data class PlayScreenState(
    val scene: Scenes = Scenes.Intro(""),
    val userChoices: List<Choice> = listOf()
)

sealed class SceneEvents {
    data object SceneDone : SceneEvents()
    data class KaijuEvent(val event: KaijuEvents) : SceneEvents()
    data object None : SceneEvents()
}

enum class KaijuEvents {
    Introduce, Jump, Stomp, Exit, DisplaySpecial
}