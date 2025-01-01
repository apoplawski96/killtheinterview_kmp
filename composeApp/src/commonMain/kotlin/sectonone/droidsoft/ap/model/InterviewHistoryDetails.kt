package sectonone.droidsoft.ap.model

data class InterviewHistoryDetails(
    val summary: InterviewHistorySummary,
    val categories: List<Category>,
    val questionsHistory: List<Question>
)
