package sectonone.droidsoft.ap.data.model

data class InterviewDetails(
    val summary: InterviewHistorySummary,
    val categories: List<Category>,
    val questionsHistory: List<Question>,
)
