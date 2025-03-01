package sectonone.droidsoft.ap.screens.interviewCurated.model

import sectonone.droidsoft.ap.data.model.Category
import sectonone.droidsoft.ap.data.model.QuestionHistory
import sectonone.droidsoft.ap.data.model.PracticeResult

data class InterviewPracticeSummary(
    val score: InterviewPracticeScore,
    val questionsHistory: List<QuestionHistory>,
    val categories: List<Category>,
    val practiceResult: PracticeResult = when {
        score.percentFLoat < 0.33f -> PracticeResult.Failed
        score.percentFLoat < 0.5f -> PracticeResult.Average
        score.percentFLoat < 0.6f -> PracticeResult.Bronze
        score.percentFLoat < 0.8f -> PracticeResult.Silver
        score.percentFLoat < 0.9f -> PracticeResult.Golden
        else -> PracticeResult.Goat
    }.also {
        println("2137 calculated result from ${score.scorePercent} to $it")
    }
)
