package sectonone.droidsoft.ap.screens.interviewCurated.model

data class InterviewPracticeScore(
    val questionsAnswered: Int,
    val questionsAsked: Int,
    val questionsTotal: Int,
    val scorePercent: Float = if (questionsTotal > 0) questionsAnswered.toFloat() / (questionsTotal) else 0f
)

val InterviewPracticeScore.percentageDisplay: String
    get() = if (questionsTotal > 0) {
        val percentage = (questionsAnswered.toFloat() / questionsTotal * 100).toInt()
        "$percentage%"
    } else {
        "0%"
    }

val InterviewPracticeScore.percentFLoat: Float
    get() = if (questionsTotal > 0) questionsAnswered.toFloat() / (questionsTotal) else 0f