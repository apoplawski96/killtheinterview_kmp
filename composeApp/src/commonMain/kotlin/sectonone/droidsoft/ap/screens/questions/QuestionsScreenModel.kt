package sectonone.droidsoft.ap.screens.questions

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sectonone.droidsoft.ap.data.repository.BookmarksRepository
import sectonone.droidsoft.ap.data.repository.QuestionsRepository
import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.Question

class QuestionsScreenModel(
    private val questionsRepository: QuestionsRepository,
    private val bookmarksRepository: BookmarksRepository,
) : ScreenModel {

    sealed interface ViewState {
        data object Loading : ViewState
        data object Error : ViewState
        data class QuestionsLoaded(val questions: List<Question>) : ViewState
    }

    sealed interface ViewEvent {
        data object ToggleBottomSheet : ViewEvent
    }

    enum class SortMode(val displayName: String) {
        BY_DIFFICULTY_ASCENDING(displayName = "By difficulty ascending"),
        BY_DIFFICULTY_DESCENDING(displayName = "By difficulty descending"),
        RANDOMIZED(displayName = "Randomize order");
    }

    data class Scoreboard(val answeredCount: Int, val totalCount: Int)

    private val _answeredQuestions = MutableStateFlow<List<Question>>(emptyList())
    private val _sortMode = MutableSharedFlow<SortMode>()

    private val _scoreboard = MutableStateFlow(Scoreboard(0, 0))
    val scoreboard = _scoreboard.asStateFlow()

    private val _viewEvents = MutableSharedFlow<ViewEvent>()
    val viewEvents = _viewEvents.asSharedFlow()

    private val _state = MutableStateFlow<ViewState>(ViewState.Loading)
    val state = _state.asStateFlow()

    fun initialize(categories: List<Category>) {
        screenModelScope.launch {
            questionsRepository.getQuestionsAsFlow(categories).collect { questions ->
                _state.update { ViewState.QuestionsLoaded(questions) }
            }
            collectSortModeUpdates()
            collectAnsweredQuestionsUpdates()
        }
    }

    fun markQuestionAsAnswered(question: Question) {
        if (_answeredQuestions.value.contains(question)) return

        val updatedList = _answeredQuestions.value + question
        _answeredQuestions.update { updatedList }
    }

    fun markQuestionAsUnanswered(question: Question) {
        if (_answeredQuestions.value.contains(question).not()) return

        val updatedList = _answeredQuestions.value.filterNot { it == question }
        _answeredQuestions.update { updatedList }
    }

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

    private fun collectSortModeUpdates() {
        screenModelScope.launch {
            _sortMode.collect { sortMode ->
                val currentViewState = state.value
                if (currentViewState is ViewState.QuestionsLoaded) {
                    val questions = currentViewState.questions
                    val sortedQuestions = when (sortMode) {
                        SortMode.BY_DIFFICULTY_ASCENDING -> questions
                        SortMode.BY_DIFFICULTY_DESCENDING -> questions
                        SortMode.RANDOMIZED -> questions.shuffled()
                    }
                    _state.update { ViewState.QuestionsLoaded(sortedQuestions) }
                }
            }
        }
    }

    private fun collectAnsweredQuestionsUpdates() {
        screenModelScope.launch {
            _answeredQuestions.collect { answeredQuestions ->
                _scoreboard.update { scoreboard.value.copy(answeredCount = answeredQuestions.count()) }
            }
        }
    }
}