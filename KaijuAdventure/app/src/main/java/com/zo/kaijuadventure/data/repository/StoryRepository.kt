package com.zo.kaijuadventure.data.repository

import arrow.core.Either
import com.google.firebase.firestore.FirebaseFirestore
import com.zo.kaijuadventure.data.model.QueryError
import com.zo.kaijuadventure.data.model.StoryNode
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json

interface StoryRepository {
    suspend fun queryStory(id: String = "") : Either<QueryError, StoryNode>
}

class StoryRepositoryImpl(
    private val firestore: FirebaseFirestore
) : StoryRepository {

    companion object {
        const val STORY_COLLECTION_PATH = "story"
        const val GODZILLA_STORY = "godzilla_story"
        const val STORY_NODES_FIELD = "storyNodes"
    }

    override suspend fun queryStory(id: String): Either<QueryError, StoryNode> =
        Either.catch {
            val result=
                firestore.collection(STORY_COLLECTION_PATH).document(GODZILLA_STORY).get().await()
            Json.decodeFromString<StoryNode>(result.data?.get(STORY_NODES_FIELD).toString())
        }.mapLeft {
            QueryError(it.message ?: "Failed to load story, please check internet and try again.")
        }

}