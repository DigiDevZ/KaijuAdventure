package com.zo.kaijuadventure.presentation.play_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zo.kaijuadventure.data.model.QueryError
import com.zo.kaijuadventure.data.model.StoryChoice
import com.zo.kaijuadventure.data.repository.StoryRepository
import com.zo.kaijuadventure.util.baseLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlayScreenViewModel(
    private val storyRepository: StoryRepository
) : ViewModel() {

    private val _sceneEvents = MutableStateFlow<SceneEvents>(SceneEvents.None)
    val sceneEvents: StateFlow<SceneEvents> = _sceneEvents

    private val _state = MutableStateFlow(PlayScreenState())
    val state: StateFlow<PlayScreenState> = _state

    fun onStart() {
        loadStory()
    }

    fun onClearUIError() {
        when (_state.value.uiError) {
            is QueryError -> {
                _state.value = _state.value.copy(uiError = null)
                loadStory()
            }
            else -> {
                _state.value = _state.value.copy(uiError = null)
            }
        }
    }

    private fun loadStory() {
        viewModelScope.launch {
            storyRepository.queryStory().onLeft {
                _state.value = _state.value.copy(uiError = it)
            }.onRight { queriedStoryNode ->
                _state.value = _state.value.copy(currentStoryNode = queriedStoryNode)
            }
        }
    }

    fun onPlayGame() {
        _state.value = _state.value.copy(gameState = GameState.Game)
    }

    fun onSceneChoiceSubmitted(choice: StoryChoice? = null) {
        _sceneEvents.value = SceneEvents.SceneChoiceSubmitted
        choice?.let { recordChoice(it) }
    }

    fun onSceneFinished() {
        _sceneEvents.value = SceneEvents.None
        transitionStoryState()
    }

    fun onKaijuIntroduced() {
        _sceneEvents.value = SceneEvents.None
    }

    private fun recordChoice(choice: StoryChoice) {
        _state.value = _state.value.copy(userChoices = _state.value.userChoices.toMutableList()
            .apply { add(choice) })
    }

    private fun transitionStoryState() {
        when (_state.value.storyState) {
            StoryState.Intro -> { _state.value = _state.value.copy(storyState = StoryState.Story) }
            StoryState.Story -> { transitionNextStoryNode() }
            StoryState.Ending ->  _state.value =  _state.value.copy(storyState = StoryState.GameOver)
            StoryState.GameOver ->  _state.value =  _state.value.copy(storyState = StoryState.Intro)
        }
    }

    private fun transitionNextStoryNode() {
        _state.value.userChoices.lastOrNull()?.let { lastChoice ->
            lastChoice.nextNode?.let { nextStoryNode ->
                _state.value = _state.value.copy(
                    currentStoryNode = nextStoryNode,
                    storyState = nextStoryNode.choices.isEmpty().let { leafNode ->
                        if (leafNode) StoryState.Ending else  _state.value.storyState
                    }
                )
            }
        } ?: { baseLog(message = "User choices are null") }
    }
}