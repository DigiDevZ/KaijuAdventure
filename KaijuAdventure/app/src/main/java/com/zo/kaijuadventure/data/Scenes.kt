package com.zo.kaijuadventure.data

sealed class Scenes(open val dialogue: String) {
    class Intro(override val dialogue: String) : Scenes(dialogue)
    class EnterKaiju(override val dialogue: String) : Scenes(dialogue)

    sealed class KaijuEncounter2(override val dialogue: String) : Scenes(dialogue) {
        class Scene2A(override val dialogue: String) : KaijuEncounter2(dialogue)
        class Scene2B(override val dialogue: String) : KaijuEncounter2(dialogue)
    }

    sealed class KaijuEncounter3(override val dialogue: String) : Scenes(dialogue) {
        class Scene2A(override val dialogue: String) : KaijuEncounter3(dialogue)
        class Scene2B(override val dialogue: String) : KaijuEncounter3(dialogue)
    }

    sealed class KaijuEncounter4(override val dialogue: String) : Scenes(dialogue) {
        class Scene2A(override val dialogue: String) : KaijuEncounter4(dialogue)
        class Scene2B(override val dialogue: String) : KaijuEncounter4(dialogue)
    }

    class Ending(override val dialogue: String) : Scenes(dialogue)

    class GameOver(override val dialogue: String = "Game Over") : Scenes(dialogue)
}

enum class SceneStates {
    Typing, AwaitingInput,
}
