package sectonone.droidsoft.ap.model.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestionSchema(
    val id: Int,
    val question: String,
    val answer: String,
    @SerialName("topCategory")
    val topCategory: String = "",
    @SerialName("topCategoryId")
    val topCategoryId: Int = -1,
    @SerialName("subCategory")
    val subCategory: String = "",
    @SerialName("subCategoryId")
    val subCategoryId: Int = -1,
    val difficulty: String? = null,
    val categories: List<String>? = null
)

@Serializable
data class QuestionSchemaV2(
    val id: Int,
    val question: String,
    val answer: String,
    val categories: List<String>
)