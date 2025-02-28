package sectonone.droidsoft.ap.data.repositories

import sectonone.droidsoft.ap.data.model.Question
import sectonone.droidsoft.ap.data.model.parseCategoriesToDb
import sectonone.droidsoft.ap.data.source.impl.BookmarksDataSource


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