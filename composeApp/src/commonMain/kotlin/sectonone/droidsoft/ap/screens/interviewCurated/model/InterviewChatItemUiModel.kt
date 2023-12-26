package sectonone.droidsoft.ap.screens.interviewCurated.model

import sectonone.droidsoft.ap.model.Question

sealed class InterviewChatItemUiModel {

    sealed class InterviewerMessage : InterviewChatItemUiModel() {
        data class QuestionAsked(val question: Question) : InterviewerMessage()
        data class OtherMessage(val message: String) : InterviewerMessage()
        object Writing : InterviewerMessage(), ProgressObject
    }

    sealed class CandidateMessage : InterviewChatItemUiModel() {
        object GoodAnswer : CandidateMessage()
        object BadAnswer: CandidateMessage()
        object Writing : CandidateMessage(), ProgressObject
    }
}

interface ProgressObject