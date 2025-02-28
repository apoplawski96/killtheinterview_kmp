package sectonone.droidsoft.ap.screens.interviewCurated

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sectonone.droidsoft.ap.data.model.Category
import sectonone.droidsoft.ap.data.model.Question
import sectonone.droidsoft.ap.data.model.QuestionHistory
import sectonone.droidsoft.ap.data.repositories.InterviewRepository
import sectonone.droidsoft.ap.data.repositories.QuestionsRepository
import sectonone.droidsoft.ap.screens.interviewCurated.model.InterviewChatItemUiModel
import sectonone.droidsoft.ap.screens.interviewCurated.model.ProgressObject
import kotlin.random.Random

private const val interval = 200L

internal class InterviewChatScreenModel(
    private val questionsRepository: QuestionsRepository,
    private val interviewRepository: InterviewRepository,
) : ScreenModel {

    data class ScoreboardState(
        val questionsAnswered: Int,
        val questionsAsked: Int
    )

    sealed interface ScreenState {
        data class InterviewActive(val chatItems: List<InterviewChatItemUiModel>) : ScreenState
        data class InterviewFinished(val scoreboard: ScoreboardState) : ScreenState
    }

    private val _questionsBase = mutableListOf<Question>()
    private val _questionsHistory = mutableListOf<QuestionHistory>()

    private var _categories = listOf<Category>()

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.InterviewActive(chatItems = emptyList()))
    val screenState = _screenState.asStateFlow()

    private val _scoreboardState = MutableStateFlow(ScoreboardState(questionsAnswered = 0, questionsAsked = 0))
    val scoreboardState = _scoreboardState.asStateFlow()

    val inputEnabled = screenState.map { screenState ->
        if (screenState is ScreenState.InterviewActive) {
            screenState.chatItems.lastOrNull() is InterviewChatItemUiModel.CandidateMessage.Writing
        } else {
            false
        }
    }.stateIn(
        scope = screenModelScope,
        started = SharingStarted.Lazily,
        initialValue = false
    )

    val currentQuestion = screenState.map { screenState ->
        if (screenState is ScreenState.InterviewActive) {
            val question = screenState.chatItems.lastOrNull {
                it is InterviewChatItemUiModel.InterviewerMessage.QuestionAsked
            } as? InterviewChatItemUiModel.InterviewerMessage.QuestionAsked
            question?.question
        } else {
            null
        }
    }.stateIn(
        scope = screenModelScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )

    fun initQuestions(categories: List<Category>) {
        screenModelScope.launch {
            val questions = questionsRepository.getQuestions(categories, questionsLimit = 5) ?: return@launch // todo: handle better
            _categories = categories
            _questionsBase.clear()
            _questionsBase.addAll(questions)
            emitInterviewerProgress()
            delay(interval)
            emitMessageItemAndUpdateTheState(InterviewChatItemUiModel.InterviewerMessage.OtherMessage("Hello candidate."))
            dropNextQuestion()
        }
    }

    fun questionAnsweredWithPoint() {
        val question = currentQuestion.value ?: return

        _scoreboardState.value.let { scoreboard ->
            _scoreboardState.value = scoreboard.copy(
                questionsAsked = scoreboard.questionsAsked + 1,
                questionsAnswered = scoreboard.questionsAnswered + 1
            )
        }

        _questionsHistory.add(
            QuestionHistory(question = question, userKnewTheAnswer = true)
        )

        screenModelScope.launch {
            emitMessageItemAndUpdateTheState(InterviewChatItemUiModel.CandidateMessage.GoodAnswer)
            emitInterviewerPositiveResponse()
            dropNextQuestion()
        }
    }

    fun questionAnsweredNoPoint() {
        val question = currentQuestion.value ?: return

        _scoreboardState.value.let { scoreboard ->
            _scoreboardState.value = scoreboard.copy(questionsAsked = scoreboard.questionsAsked + 1)
        }

        _questionsHistory.add(
            QuestionHistory(question = question, userKnewTheAnswer = true)
        )

        screenModelScope.launch {
            emitMessageItemAndUpdateTheState(InterviewChatItemUiModel.CandidateMessage.BadAnswer)
            emitInterviewerNegativeResponse()
            dropNextQuestion()
        }
    }

    private suspend fun dropNextQuestion() {
        if (_questionsBase.size > 1) {
            println("2137 - questionsBase, size: ${_questionsBase.size}, count: ${_questionsBase.count()}, lastIndex: ${_questionsBase.lastIndex}, size: ${_questionsBase.size}")
            val randomIndex = Random.nextInt(from = 0, until = _questionsBase.size)
            val randomQuestion = _questionsBase.removeAt(randomIndex)

            delay(interval)
            emitInterviewerProgress()
            delay(interval)
            emitMessageItemAndUpdateTheState(InterviewChatItemUiModel.InterviewerMessage.QuestionAsked(randomQuestion))
            delay(interval)
            emitCandidateProgress()
        } else {
            println("2137 - we are in else")
            _screenState.value = ScreenState.InterviewFinished(scoreboardState.value)
            val scoreboard = scoreboardState.value
            interviewRepository.saveInterview(
                answeredCount = scoreboard.questionsAnswered,
                failedCount = scoreboard.questionsAsked - scoreboard.questionsAnswered,
                categories = _categories,
                questionsHistory = _questionsHistory
            )
        }
    }

    private fun emitMessageItemAndUpdateTheState(item: InterviewChatItemUiModel) {
        val screenState = screenState.value
        if (screenState is ScreenState.InterviewActive) {
            val updatedItems = screenState.chatItems.toMutableList().apply { add(item) }.filterNot { it is ProgressObject }
            _screenState.value = ScreenState.InterviewActive(updatedItems)
        }
    }

    private suspend fun emitInterviewerPositiveResponse() {
        delay(interval)
        emitInterviewerProgress()
        delay(interval)
        emitMessageItemAndUpdateTheState(InterviewChatItemUiModel.InterviewerMessage.OtherMessage("That's a great answer!"))
    }

    private suspend fun emitInterviewerNegativeResponse() {
        delay(interval)
        emitInterviewerProgress()
        delay(interval)
        emitMessageItemAndUpdateTheState(InterviewChatItemUiModel.InterviewerMessage.OtherMessage("No worries. Let's try with another question."))
    }

    private fun emitInterviewerProgress() {
        addProgressObjectAndUpdateTheState(InterviewChatItemUiModel.InterviewerMessage.Writing)
    }

    private fun emitCandidateProgress() {
        addProgressObjectAndUpdateTheState(InterviewChatItemUiModel.CandidateMessage.Writing)
    }

    private fun addProgressObjectAndUpdateTheState(progressObject: InterviewChatItemUiModel) {
        val screenState = screenState.value
        if (screenState is ScreenState.InterviewActive) {
            val updatedItems = screenState.chatItems.toMutableList().apply { add(progressObject) }
            _screenState.value = ScreenState.InterviewActive(updatedItems)
        }
    }
}