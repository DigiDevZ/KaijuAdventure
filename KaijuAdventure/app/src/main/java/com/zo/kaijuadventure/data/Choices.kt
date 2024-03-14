package com.zo.kaijuadventure.data

import com.zo.kaijuadventure.R

sealed class Choice(open val stringResId: Int)

sealed class EnterKaijuChoices(override val stringResId: Int) : Choice(stringResId = stringResId) {
    companion object {
        fun toList() = listOf(Run(), Hide())
    }
    class Run(override val stringResId: Int = R.string.enter_kaiju_choice_1) : EnterKaijuChoices(stringResId = stringResId)
    class Hide(override val stringResId: Int = R.string.enter_kaiju_choice_2) : EnterKaijuChoices(stringResId = stringResId)
}

sealed class KaijuEncounter2Choices(override val stringResId: Int) : Choice(stringResId = stringResId) {
    companion object {
        fun toList() = listOf(Watch(), Explore())
    }
    class Watch(override val stringResId: Int = R.string.kaiju_encounter_2_choice_1) : KaijuEncounter2Choices(stringResId = stringResId)
    class Explore(override val stringResId: Int = R.string.kaiju_encounter_2_choice_2) : KaijuEncounter2Choices(stringResId = stringResId)
}

sealed class KaijuEncounter3Choices(override val stringResId: Int) : Choice(stringResId = stringResId) {
    companion object {
        fun toList() = listOf(ScreamBack(), StaySilent())
    }
    class ScreamBack(override val stringResId: Int = R.string.kaiju_encounter_3_choice_1) : KaijuEncounter3Choices(stringResId = stringResId)
    class StaySilent(override val stringResId: Int = R.string.kaiju_encounter_3_choice_2) : KaijuEncounter3Choices(stringResId = stringResId)
}

sealed class KaijuEncounter4Choices(override val stringResId: Int) : Choice(stringResId = stringResId) {
    companion object {
        fun toList() = listOf(Fight(), Acceptance())
    }
    class Fight(override val stringResId: Int = R.string.kaiju_encounter_4_choice_1) : KaijuEncounter4Choices(stringResId = stringResId)
    class Acceptance(override val stringResId: Int = R.string.kaiju_encounter_4_choice_2) : KaijuEncounter4Choices(stringResId = stringResId)
}