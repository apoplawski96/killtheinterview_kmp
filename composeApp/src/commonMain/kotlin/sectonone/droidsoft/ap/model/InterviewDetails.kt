package sectonone.droidsoft.ap.model

data class InterviewDetails(
    val summary: InterviewHistorySummary,
    val categories: List<Category>,
    val questionsHistory: List<Question>,
)
