package com.zo.kaijuadventure.data

sealed class Scenes(open val dialogue: String = "") {
    class Intro(override val dialogue: String) : Scenes(dialogue)
    class EnterKaiju(override val dialogue: String) : Scenes(dialogue)
    class Encounter2(override val dialogue: String) : Scenes(dialogue)
    class Encounter3(override val dialogue: String) : Scenes(dialogue)
    class Encounter4(override val dialogue: String) : Scenes(dialogue)
    class Ending(override val dialogue: String) : Scenes(dialogue)
    class GameOver : Scenes()
}

enum class SceneStates {
    Typing, AwaitingInput,
}
