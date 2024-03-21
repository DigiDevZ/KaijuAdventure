package com.zo.kaijuadventure.presentation.play_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zo.kaijuadventure.data.model.QueryError
import com.zo.kaijuadventure.data.model.StoryChoice
import com.zo.kaijuadventure.data.repository.StoryRepository
import com.zo.kaijuadventure.util.baseLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PlayScreenViewModel(
    private val storyRepository: StoryRepository
) : ViewModel() {

    var sceneEvents = MutableStateFlow<SceneEvents>(SceneEvents.None)
        private set
    var state = MutableStateFlow(PlayScreenState())
        private set

    fun onStart() {
        loadStory()
    }

    fun onClearUIError() {
        when (state.value.uiError) {
            is QueryError -> {
                state.value = state.value.copy(uiError = null)
                loadStory()
            }
            else -> {
                state.value = state.value.copy(uiError = null)
            }
        }
    }

    private fun loadStory() {
        viewModelScope.launch {
            storyRepository.queryStory().onLeft {
                state.value = state.value.copy(uiError = it)
            }.onRight { queriedStoryNode ->
                state.value = state.value.copy(currentStoryNode = queriedStoryNode)
            }
        }
    }

    fun onPlayGame() {
        state.value = state.value.copy(gameState = GameState.Game)
    }

    fun onSceneChoiceSubmitted(choice: StoryChoice? = null) {
        sceneEvents.value = SceneEvents.SceneChoiceSubmitted
        choice?.let { recordChoice(it) }
    }

    fun onSceneFinished() {
        sceneEvents.value = SceneEvents.None
        transitionStoryState()
    }

    fun onKaijuIntroduced() {
        sceneEvents.value = SceneEvents.None
    }

    private fun recordChoice(choice: StoryChoice) {
        state.value = state.value.copy(userChoices = state.value.userChoices.toMutableList()
            .apply { add(choice) })
    }

    private fun transitionStoryState() {
        when (state.value.storyState) {
            StoryState.Intro -> {
                sceneEvents.value = SceneEvents.KaijuEvent(KaijuEvents.Introduce)
                state.value = state.value.copy(storyState = StoryState.Story)
            }
            StoryState.Story -> transitionNextStoryNode()
            StoryState.Ending ->  state.value =  state.value.copy(storyState = StoryState.GameOver)
            StoryState.GameOver ->  state.value =  state.value.copy(storyState = StoryState.Intro)
        }
    }

    private fun transitionNextStoryNode() {
        state.value.userChoices.lastOrNull()?.let { lastChoice ->
            lastChoice.nextNode?.let { nextStoryNode ->
                state.value = state.value.copy(
                    currentStoryNode = nextStoryNode,
                    storyState = nextStoryNode.choices.isEmpty().let { leafNode ->
                        if (leafNode) {
                            sceneEvents.value = SceneEvents.KaijuEvent(KaijuEvents.Exit)
                            StoryState.Ending
                        } else {
                            sceneEvents.value = SceneEvents.KaijuEvent(KaijuEvents.Jump)
                            state.value.storyState
                        }
                    }
                )
            }
        } ?: { baseLog(message = "User choices are null") }
    }

    fun onGameRestart() {
        state.value = state.value.copy(gameState = GameState.Menu, userChoices = listOf())
    }
}