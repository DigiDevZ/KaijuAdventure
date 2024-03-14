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