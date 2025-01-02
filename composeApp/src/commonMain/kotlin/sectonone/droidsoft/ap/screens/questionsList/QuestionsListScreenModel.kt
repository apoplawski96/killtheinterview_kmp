package sectonone.droidsoft.ap.screens.questionsList

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sectonone.droidsoft.ap.data.repository.QuestionsRepository
import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.Question

class QuestionsListScreenModel(
    private val questionsRepository: QuestionsRepository,
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

    private val _answeredQuestions: MutableStateFlow<List<Question>> = MutableStateFlow(emptyList())
    private val answeredQuestions: StateFlow<List<Question>> = _answeredQuestions

    private val _sortMode: MutableSharedFlow<SortMode> = MutableSharedFlow()
    private val sortMode: SharedFlow<SortMode> = _sortMode

    private val _scoreboard: MutableStateFlow<Scoreboard> = MutableStateFlow(Scoreboard(0, 0))
    val scoreboard: StateFlow<Scoreboard> = _scoreboard

    private val _viewEvents: MutableSharedFlow<ViewEvent> = MutableSharedFlow()
    val viewEvents: SharedFlow<ViewEvent> = _viewEvents

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val viewState: StateFlow<ViewState> = _viewState

    fun initialize(categories: List<Category>) {
        screenModelScope.launch {
            val resultNew = questionsRepository.getQuestions(categories) ?: return@launch // TODO: Handle better

            _viewState.update { ViewState.QuestionsLoaded(resultNew) }

            collectSortModeUpdates()
            collectAnsweredQuestionsUpdates()
        }
    }

    fun markQuestionAsAnswered(question: Question) {
        if (answeredQuestions.value.contains(question)) return

        val updatedList = answeredQuestions.value + question
        _answeredQuestions.update { updatedList }
    }

    fun markQuestionAsUnanswered(question: Question) {
        if (answeredQuestions.value.contains(question).not()) return

        val updatedList = answeredQuestions.value.filterNot { it == question }
        _answeredQuestions.update { updatedList }
    }

    private fun collectSortModeUpdates() {
        screenModelScope.launch {
            sortMode.collect { sortMode ->
                val currentViewState = viewState.value
                if (currentViewState is ViewState.QuestionsLoaded) {
                    val questions = currentViewState.questions
                    val sortedQuestions = when (sortMode) {
                        SortMode.BY_DIFFICULTY_ASCENDING -> questions
                        SortMode.BY_DIFFICULTY_DESCENDING -> questions
                        SortMode.RANDOMIZED -> questions.shuffled()
                    }
                    _viewState.update { ViewState.QuestionsLoaded(sortedQuestions) }
                }
            }
        }
    }

    private fun collectAnsweredQuestionsUpdates() {
        screenModelScope.launch {
            answeredQuestions.collect { answeredQuestions ->
                _scoreboard.update { scoreboard.value.copy(answeredCount = answeredQuestions.count()) }
            }
        }
    }
}