package sectonone.droidsoft.ap.data.source.impl

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import sectonone.droidsoft.ap.data.db.KTIDatabase2
import sectonone.droidsoft.ap.data.toDomainModel

// TODO: Rename to BookmarksLocalDb
class BookmarksDataSource(database: KTIDatabase2) {

    private val queries = database.questionBookmarkQueries

    fun getAllBookmarksAsFlow() =
        queries.getAllQuestionBookmarks()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { bookmarks ->
                bookmarks.map { it.toDomainModel }
            }

    fun getBookmarkByIdAsFlow(id: Long) =
        queries.getQuestionBookmarkById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .mapNotNull { it?.toDomainModel }

    suspend fun insertBookmark(
        questionId: Long,
        question: String,
        answer: String,
        categories: String
    ) = withContext(Dispatchers.IO) {
        queries.insertQuestionBookmark(
            questionId = questionId,
            question = question,
            answer = answer,
            categories = categories,
        )
    }

    suspend fun deleteBookmarkById(id: Long) = withContext(Dispatchers.IO) {
        queries.deleteQuestionBookmarkById(id)
    }
}