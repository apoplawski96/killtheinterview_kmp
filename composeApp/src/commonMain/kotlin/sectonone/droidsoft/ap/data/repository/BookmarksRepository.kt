package sectonone.droidsoft.ap.data.repository

import sectonone.droidsoft.ap.data.source.BookmarksDataSource
import sectonone.droidsoft.ap.model.Question
import sectonone.droidsoft.ap.model.parseCategoriesToDb

class BookmarksRepository(private val dataSource: BookmarksDataSource) {

    fun getAllBookmarksAsFlow() = dataSource.getAllBookmarksAsFlow()

    fun getBookmarkById(id: Long) = dataSource.getBookmarkByIdAsFlow(id)

    suspend fun addBookmark(question: Question) {
        dataSource.insertBookmark(
            questionId = question.id.toLong(),
            question = question.question,
            answer = question.answer,
            categories = question.categories.parseCategoriesToDb
        )
    }

    suspend fun deleteBookmark(id: Long) {
        dataSource.deleteBookmarkById(id)
    }
}