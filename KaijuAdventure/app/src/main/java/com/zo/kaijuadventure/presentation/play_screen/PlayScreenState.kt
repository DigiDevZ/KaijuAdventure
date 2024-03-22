package com.zo.kaijuadventure.presentation.play_screen

import com.zo.kaijuadventure.data.model.BaseError
import com.zo.kaijuadventure.data.model.StoryChoice
import com.zo.kaijuadventure.data.model.StoryNode

data class PlayScreenState(
    val gameState: GameState = GameState.Menu,
    val currentStoryNode: StoryNode? = null,
    val storyState: StoryState = StoryState.Intro,
    val userChoices: List<StoryChoice> = listOf(),
    val uiError: BaseError? = null,
    val loading: Boolean = false,
)

sealed class SceneEvents {
    data object SceneChoiceSubmitted : SceneEvents()
    data class KaijuEvent(val event: KaijuEvents) : SceneEvents()
    data object None : SceneEvents()
}

enum class GameState { Menu, Game }
enum class StoryState { Intro, Story, Ending, GameOver }
enum class SceneStates { Typing, AwaitingInput }
enum class KaijuEvents { Introduce, Jump, Stomp, Exit, DisplaySpecial }