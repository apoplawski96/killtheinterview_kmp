package sectonone.droidsoft.ap.screens.bookmarks

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sectonone.droidsoft.ap.data.repository.BookmarksRepository
import sectonone.droidsoft.ap.model.Question

internal class BookmarksScreenModel(private val bookmarksRepository: BookmarksRepository) : ScreenModel {

    val state = bookmarksRepository.getAllBookmarksAsFlow()
        .stateIn(
            screenModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    fun addBookmark(question: Question) {
        screenModelScope.launch {
            bookmarksRepository.addBookmark(question)
        }
    }

    fun removeBookmark(question: Question) {
        screenModelScope.launch {
            bookmarksRepository.deleteBookmark(question.id.toLong())
        }
    }
}