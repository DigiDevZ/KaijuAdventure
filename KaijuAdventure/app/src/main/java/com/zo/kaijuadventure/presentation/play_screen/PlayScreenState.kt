package com.zo.kaijuadventure.presentation.play_screen

import com.zo.kaijuadventure.data.StoryChoice
import com.zo.kaijuadventure.data.StoryNode

data class PlayScreenState(
    val currentStoryNode: StoryNode? = null,
    val storyState: StoryState = StoryState.Intro,
    val userChoices: List<StoryChoice> = listOf()
)

sealed class SceneEvents {
    data object SceneChoiceSubmitted : SceneEvents()
    data class KaijuEvent(val event: KaijuEvents) : SceneEvents()
    data object None : SceneEvents()
}

enum class KaijuEvents { Introduce, Jump, Stomp, Exit, DisplaySpecial }

enum class SceneStates { Typing, AwaitingInput }

enum class StoryState { Intro, Story, Ending, GameOver }

