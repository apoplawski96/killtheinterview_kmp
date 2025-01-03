package sectonone.droidsoft.ap.util

import sectonone.droidsoft.ap.db.InterviewSummary
import sectonone.droidsoft.ap.db.QuestionBookmark
import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.InterviewHistorySummary
import sectonone.droidsoft.ap.model.Question
import sectonone.droidsoft.ap.model.schema.QuestionScheme

val InterviewSummary.toDomainModel
    get() = InterviewHistorySummary(
        id = id.toInt(),
        answeredCount = answeredCount.toInt(),
        failedCount = failedCount.toInt(),
        categoriesSummary = categoriesSummary.split(", "),
        interviewDate = interviewDate
    )

val QuestionScheme.toDomainModel
    get() = Question(
        id = id,
        answer = answer,
        question = question,
        categories = categories
            .mapNotNull {
                Category.getForKey(it)
            }
    )

fun QuestionScheme.toDomainModelWithBookmark(isBookmark: Boolean) = Question(
    id = id,
    answer = answer,
    question = question,
    isBookmarked = isBookmark,
    categories = categories
        .mapNotNull {
            Category.getForKey(it)
        },
)

val QuestionBookmark.toDomainModel
    get() = Question(
        id = questionId.toInt(),
        answer = answer,
        question = question,
        categories = categories
            .split("")
            .map { it.trim() }
            .mapNotNull {
                Category.getForKey(it)
            }
    )