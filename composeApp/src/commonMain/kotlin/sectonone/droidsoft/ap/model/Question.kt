package sectonone.droidsoft.ap.model

data class Question(
    val id: Int = -1,
    val question: String,
    val answer: String,
    val categories: List<Category> = emptyList(),
    val isBookmarked: Boolean = false
)
