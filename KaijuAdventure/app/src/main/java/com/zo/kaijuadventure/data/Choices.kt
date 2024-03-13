package com.zo.kaijuadventure.data


sealed interface Choice

enum class EggChoices : Choice {
    Uninitialized, Mechanical, Natural, Irradiated
}

enum class Movement1Choices : Choice {
    Uninitialized, GoOutside, StayIn, ExploreDeeper
}

