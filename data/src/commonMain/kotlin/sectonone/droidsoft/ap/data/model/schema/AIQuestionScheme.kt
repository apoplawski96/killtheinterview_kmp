package sectonone.droidsoft.ap.data.model.schema

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
value class AIQuestion(val content: String)

@Serializable
data class AIQuestionScheme(
    val question: String,
    val answer: String,
)