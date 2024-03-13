package com.zo.kaijuadventure.data

sealed class Scenes(open val dialogue: String) {
    companion object {
        fun Intro() : Intro = Intro()
        fun EnterKaiju(): EnterKaiju = EnterKaiju()
        fun KaijuEncounter2(): KaijuEncounter2 = KaijuEncounter2()
        fun KaijuEncounter3(): KaijuEncounter3 = KaijuEncounter3()
        fun KaijuEncounter4(): KaijuEncounter4 = KaijuEncounter4()
        fun Ending() : Ending = Ending()
    }

    sealed class Intro(override val dialogue: String) : Scenes(dialogue)
    sealed class EnterKaiju(override val dialogue: String) : Scenes(dialogue)

    sealed class KaijuEncounter2(override val dialogue: String) : Scenes(dialogue) {
        class Scene2A(override val dialogue: String) : KaijuEncounter2(dialogue)
        class Scene2B(override val dialogue: String) : KaijuEncounter2(dialogue)
    }

    sealed class KaijuEncounter3(override val dialogue: String) : Scenes(dialogue)

    sealed class KaijuEncounter4(override val dialogue: String) : Scenes(dialogue)

    sealed class Ending(override val dialogue: String) : Scenes(dialogue)


}