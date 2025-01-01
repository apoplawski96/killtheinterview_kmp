package sectonone.droidsoft.ap.model.schema

import kotlinx.serialization.Serializable

@Serializable
data class QuestionSchemaV2(
    val id: Int,
    val question: String,
    val answer: String,
    val categories: List<String>
)