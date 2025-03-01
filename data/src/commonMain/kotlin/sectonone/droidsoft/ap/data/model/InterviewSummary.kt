package sectonone.droidsoft.ap.data.model

data class QuestionHistory(
    val question: Question,
    val userKnewTheAnswer: Boolean,
)

data class InterviewHistorySummary(
    val id: Int,
    val categoriesSummary: List<String>,
    val answeredCount: Int,
    val failedCount: Int,
    val interviewDate: String,
    val mainCategory: String = categoriesSummary.first(),
    val scorePercent: Float = if (answeredCount + failedCount > 0) answeredCount.toFloat() / (answeredCount + failedCount) else 0f,
    val scorePercentDisplay: String = "${(scorePercent * 100).toInt()}%",
    val practiceResult: PracticeResult = when {
        scorePercent < 0.33f -> PracticeResult.Failed
        scorePercent < 0.66f -> PracticeResult.Average
        else -> PracticeResult.Goat
    }
)

fun interviewSummary(
    id: Int = 1,
    categories: List<String> = listOf("Android", "Compose"),
    answeredCount: Int = 2,
    failedCount: Int = 10,
    interviewDate: String = "27 Dec 2024",
) = InterviewHistorySummary(id, categories, answeredCount, failedCount, interviewDate)