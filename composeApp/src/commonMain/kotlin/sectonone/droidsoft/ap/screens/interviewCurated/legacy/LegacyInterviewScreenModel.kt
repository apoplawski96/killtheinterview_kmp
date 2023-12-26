package sectonone.droidsoft.ap.screens.interviewCurated.legacy//package com.example.myapplication.screens.interviewCurated.legacy
//
//import cafe.adriel.voyager.core.model.ScreenModel
//import sectonone.droidsoft.ap._legacy.QuestionsRepository
//import sectonone.droidsoft.ap.model.Question
//import sectonone.droidsoft.ap.model.TopCategory
//import sectonone.droidsoft.ap.screens.interviewCurated.model.InterviewChatItemUiModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlin.random.Random
//
//class LegacyInterviewScreenModel(
//    private val questionsRepository: QuestionsRepository,
//) : ScreenModel {
//
//    data class Scoreboard(val questionsAnswered: Int, val questionsAsked: Int)
//
//    sealed interface ViewStateSimple {
//        object Idle : ViewStateSimple
//        object NoQuestionsLeft : ViewStateSimple
//        data class QuestionDropped(val question: Question) : ViewStateSimple
//    }
//
//    private val _viewState = MutableStateFlow<ViewStateSimple>(ViewStateSimple.Idle)
//    val viewState = _viewState.asStateFlow()
//
//    sealed interface ViewStateChat {
//        data class InterviewActive(val chatItems: List<InterviewChatItemUiModel>) : ViewStateChat
//        object InterviewFinished : ViewStateChat
//    }
//
//    private val _viewStateNew = MutableStateFlow<ViewStateChat>(ViewStateChat.InterviewActive(chatItems = emptyList()))
//    val viewStateNew = _viewStateNew.asStateFlow()
//
//    private val questionsBase = mutableListOf<Question>()
//
//    private val _scoreboardState = MutableStateFlow(Scoreboard(questionsAnswered = 0, questionsAsked = 0))
//    val scoreboardState = _scoreboardState.asStateFlow()
//
//    fun initQuestions(categories: List<TopCategory>) {
//        val questions = questionsRepository.getQuestionsForCategories(categories)
//
//        questionsBase.clear()
//        questionsBase.addAll(questions)
//
//        dropNextQuestion()
//        dropNextQuestionNew()
//    }
//
//    fun questionAnsweredWithPoint() {
//        val screenState = viewStateNew.value
//        val currentScore = scoreboardState.value
//
//        if (screenState !is ViewStateChat.InterviewActive) return
//
//        _scoreboardState.value = currentScore.copy(
//            questionsAsked = currentScore.questionsAsked + 1,
//            questionsAnswered = currentScore.questionsAnswered + 1
//        )
//
//        val updatedChatItems = screenState.chatItems.toMutableList().apply {
//            add(InterviewChatItemUiModel.CandidateMessage.GoodAnswer)
//        }
//
//        _viewStateNew.value = screenState.copy(updatedChatItems)
//
//        dropNextQuestion()
//        dropNextQuestionNew()
//    }
//
//    fun questionAnsweredNoPoint() {
//        val screenState = viewStateNew.value
//        val currentScore = scoreboardState.value
//
//        if (screenState !is ViewStateChat.InterviewActive) return
//
//        _scoreboardState.value = currentScore.copy(questionsAsked = currentScore.questionsAsked + 1)
//
//        val updatedChatItems = screenState.chatItems.toMutableList().apply {
//            add(InterviewChatItemUiModel.CandidateMessage.BadAnswer)
//        }
//
//        _viewStateNew.value = screenState.copy(updatedChatItems)
//
//        dropNextQuestion()
//        dropNextQuestionNew()
//    }
//
//    private fun dropNextQuestion() {
//        if (questionsBase.isNotEmpty()) {
//            val randomIndex = Random.nextInt(from = 0, until = questionsBase.lastIndex)
//            val randomQuestion = questionsBase.removeAt(randomIndex)
//
//            _viewState.value = ViewStateSimple.QuestionDropped(randomQuestion)
//        } else {
//            _viewState.value = ViewStateSimple.NoQuestionsLeft
//        }
//    }
//
//    private fun dropNextQuestionNew() {
//        val screenState = viewStateNew.value
//        if (questionsBase.isNotEmpty() && screenState is ViewStateChat.InterviewActive) {
//            val randomIndex = Random.nextInt(from = 0, until = questionsBase.lastIndex)
//            val randomQuestion = questionsBase.removeAt(randomIndex)
//
//            val updatedChatItems = screenState.chatItems.toMutableList().apply {
//                add(InterviewChatItemUiModel.InterviewerMessage.QuestionAsked(randomQuestion))
//            }
//
//            _viewStateNew.value = ViewStateChat.InterviewActive(updatedChatItems)
//        } else {
//            _viewStateNew.value = ViewStateChat.InterviewFinished
//        }
//    }
//}