package com.zo.kaijuadventure.data

import arrow.core.Either
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json

interface StoryRepository {
    suspend fun queryStory(id: String = "") : Either<QueryError, StoryNode?>
}

class StoryRepositoryImpl(
    private val firestore: FirebaseFirestore
) : StoryRepository {

    companion object {
        const val STORY_COLLECTION_PATH = "story"
        const val GODZILLA_STORY = "godzilla_story"
    }


    override suspend fun queryStory(id: String): Either<QueryError, StoryNode?> =
        Either.catch {
            val result=
                firestore.collection(STORY_COLLECTION_PATH).document(GODZILLA_STORY).get().await()
            Json.decodeFromString<StoryNode>(result.data?.get("storyNodes").toString())
        }.mapLeft {
            QueryError(it.message ?: "Story query failed with no error message")
        }

}