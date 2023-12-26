package sectonone.droidsoft.ap.model.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestionSchema(
    val id: Int,
    val question: String,
    val answer: String,
    @SerialName("topCategory")
    val topCategory: String,
    @SerialName("topCategoryId")
    val topCategoryId: Int,
    @SerialName("subCategory")
    val subCategory: String,
    @SerialName("subCategoryId")
    val subCategoryId: Int,
    val difficulty: String? = null,
)