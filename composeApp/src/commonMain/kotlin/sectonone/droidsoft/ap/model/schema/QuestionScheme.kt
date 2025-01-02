package sectonone.droidsoft.ap.model.schema

import kotlinx.serialization.Serializable

@Serializable
data class QuestionScheme(
    val id: Int,
    val question: String,
    val answer: String,
    val categories: List<String>
)