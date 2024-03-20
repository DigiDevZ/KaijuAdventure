package com.zo.kaijuadventure.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Serializable
class StoryNode(
    val id: String,
    val storyText: String,
    val choices: MutableList<StoryChoice> = mutableListOf()
) {
    companion object {
        fun fromJson(json: String) : StoryNode = Json.decodeFromString(json)
    }

    fun addChoice(choice: StoryChoice) {
        choices.add(choice)
    }

    fun toJson() = Json.encodeToString(this)
}

@Serializable
data class StoryChoice(
    val choiceText: String,
    val nextNode: StoryNode?
) {
    companion object {
        fun fromJson(json: String) : StoryChoice = Json.decodeFromString(json)
    }

    fun toJson() = Json.encodeToString(this)
}