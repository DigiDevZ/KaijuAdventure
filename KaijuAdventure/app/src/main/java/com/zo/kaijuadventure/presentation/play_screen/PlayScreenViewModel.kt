package com.zo.kaijuadventure.presentation.play_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zo.kaijuadventure.data.StoryChoice
import com.zo.kaijuadventure.data.StoryNode
import com.zo.kaijuadventure.util.baseLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlayScreenViewModel : ViewModel() {

    private val _sceneEvents = MutableStateFlow<SceneEvents>(SceneEvents.None)
    val sceneEvents: StateFlow<SceneEvents> = _sceneEvents

    var state by mutableStateOf(PlayScreenState())
        private set

    fun onSceneDone(choice: StoryChoice? = null) {
        _sceneEvents.value = SceneEvents.SceneDone
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
        val userChoices = state.userChoices.toMutableList()
        userChoices.add(choice)
        state = state.copy(userChoices = userChoices)
    }

    private fun transitionStoryState() {
        when (state.storyState) {
            StoryState.Intro -> {
                state = state.copy(
                    storyState = StoryState.Story,
                    currentStoryNode = setupStoryFlowWithNodes()
                )
            }
            StoryState.Story -> { transitionNextStoryNode() }
            StoryState.Ending -> state = state.copy(storyState = StoryState.GameOver)
            StoryState.GameOver -> state = state.copy(storyState = StoryState.Intro)
        }
    }

    private fun transitionNextStoryNode() {
        state.userChoices.lastOrNull()?.let { lastChoice ->
            lastChoice.nextNode?.let { nextStoryNode ->
                state = state.copy(
                    currentStoryNode = nextStoryNode,
                    storyState = if (nextStoryNode.choices.isEmpty()) StoryState.Ending else state.storyState
                )
            }
        } ?: { baseLog(message = "User choices are null") }
    }

    //Only keeping this in the codebase until we get remote data + repository setup
    private fun setupStoryFlowWithNodes() : StoryNode {
        //Intro is more of a game start, can be abstract
        //EnterKaiju -> Ending is all unique to the story relevant
        //GameOver is abstract like Intro

        //Level 1
        val enterKaijuNode = StoryNode("1", "What were those rumbles!!!\n" +
                "\n" +
                "A Kaiju...oh..no..it's....\n" +
                "\n" +
                "Godzilla!!!!!!")

        //Level 2
        val runNode = StoryNode("2", "You run\n" +
                "\n" +
                "Godzilla continues moving through the city\n" +
                "\n" +
                "As if he is chasing you through the city streets\n" +
                "\n" +
                "You make it to an intersection")
        val hideNode = StoryNode("3", "You hide\n" +
                "\n" +
                "Godzilla continues moving through the city\n" +
                "\n" +
                "You can feel the tremblings of a falling building\n" +
                "\n" +
                "You are still in your office")

        //Level 3
        //region Run Path
        val turnLeftNode = StoryNode("4", "You turn left\n" +
                "\n" +
                "Godzilla continues moving forward\n" +
                "\n" +
                "His path of destruction will continue in a different direction\n" +
                "\n" +
                "You make it to a BART station")

        val turnRightNode = StoryNode("5", "You turn right\n" +
                "\n" +
                "Godzilla continues moving through the city\n" +
                "\n" +
                "He gets closer..thud..THUD\n" +
                "\n" +
                "You don't make it")
        //endregion Run Path

        //region Hide Path
        //Start from 6
        val exploreNode = StoryNode("6", "You explore the office\n" +
                "\n" +
                "You can still feel the massive beast moving through the city\n" +
                "\n" +
                "You find a working desktop to try and get more information\n\n" +
                "Godzilla is leaving the city...you'll live!")

        val watchNode = StoryNode("7", "You watch the brute\n" +
                "\n" +
                "Godzilla starts destroying buildings around him\n" +
                "\n" +
                "Your building starts shaking..as you look back out the window..you see a massive tail coming towards the window\n" +
                "\n" +
                "You did not make it")

        val checkNewsNode = StoryNode("8", "You check your phone for alerts\n" +
                "\n" +
                "Massive Unidentified Terrestrial Organism destroying the city!\n" +
                "\n" +
                "The following alerts are military evacuation routes\n" +
                "\n" +
                "You decide to follow the evacuation routes..you will live")
        //endregion Hide Path

        //Level 2
        runNode.addChoice(StoryChoice("Turn Left", turnLeftNode))
        runNode.addChoice(StoryChoice("Turn Right", turnRightNode))

        hideNode.addChoice(StoryChoice("Explore", exploreNode))
        hideNode.addChoice(StoryChoice("Watch", watchNode))
        hideNode.addChoice(StoryChoice("Check News", checkNewsNode))

        //Highest level, node 1
        enterKaijuNode.addChoice(StoryChoice("Run", runNode))
        enterKaijuNode.addChoice(StoryChoice("Hide", hideNode))

        return enterKaijuNode
    }
}