package sectonone.droidsoft.ap.model

data class Question(
    val id: Int = -1,
    val topCategoryId: Int = -1,
    val subCategoryId: Int = -1,
    val difficulty: Difficulty = Difficulty.Beginner,
    val question: String,
    val answer: String,
    val categories: List<Category> = emptyList()
)
