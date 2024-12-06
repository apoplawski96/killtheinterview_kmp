package sectonone.droidsoft.ap.model

data class QuestionHistory(
    val question: Question,
    val userKnewTheAnswer: Boolean,
)

data class InterviewSummary(
    val id: Int,
    val categories: List<Category>,
    val questionsHistory: List<QuestionHistory>,
    val configuration: InterviewConfiguration,
    val answeredCount: Int,
    val failedCount: Int,
    val interviewDate: String,
    val mainCategory: Category = categories.first(),
    val scorePercent: Float = (if (answeredCount + failedCount > 0)
        answeredCount.toFloat() / (answeredCount + failedCount)
    else 0f).also {
        println("2137 - progress calculated: $it")
    },
    val scorePercentDisplay: String = "${(scorePercent * 100).toInt()}%",
    val successSummary: SuccessSummary = when {
        scorePercent < 0.33f -> SuccessSummary.Failed
        scorePercent < 0.66f -> SuccessSummary.Average
        else -> SuccessSummary.Success
    }
) {
    enum class SuccessSummary { Failed, Average, Success; }
}

fun interviewSummary(
    id: Int = 1, categories: List<Category> = listOf(Category.Android, Category.Compose),
    answeredCount: Int = 2,
    failedCount: Int = 10,
    configuration: InterviewConfiguration = InterviewConfiguration(),
    interviewDate: String = "27 Dec 2024",
    questionsHistory: List<QuestionHistory> = emptyList()
) = InterviewSummary(id, categories, questionsHistory, configuration, answeredCount, failedCount, interviewDate)