package com.zo.kaijuadventure.data

import com.zo.kaijuadventure.R

enum class Choice(val stringResId: Int) {
    Run(R.string.enter_kaiju_choice_1),
    Hide(R.string.enter_kaiju_choice_2),
    Watch(R.string.kaiju_encounter_2_choice_1),
    Explore(R.string.kaiju_encounter_2_choice_2),
    ScreamBack(R.string.kaiju_encounter_3_choice_1),
    StaySilent(R.string.kaiju_encounter_3_choice_2),
    Fight(R.string.kaiju_encounter_4_choice_1),
    Acceptance(R.string.kaiju_encounter_4_choice_2)
}

fun enterKaijuChoices() = listOf(Choice.Run, Choice.Hide)
fun kaijuEncounter2Choices() = listOf(Choice.Watch, Choice.Explore)
fun kaijuEncounter3Choices() = listOf(Choice.ScreamBack, Choice.StaySilent)
fun kaijuEncounter4Choices() = listOf(Choice.Fight, Choice.Acceptance)
