package sectonone.droidsoft.ap.model

data class InterviewConfiguration(
    val categories: List<Category> = Category.entries,
    val questionsCount: Int = 15,
)
