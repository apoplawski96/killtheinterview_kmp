package sectonone.droidsoft.ap.model

import sectonone.droidsoft.ap.db.InterviewSummary
import sectonone.droidsoft.ap.model.schema.QuestionSchemaV2

val InterviewSummary.toDomainModel
    get() = InterviewHistorySummary(
        id = id.toInt(),
        answeredCount = answeredCount.toInt(),
        failedCount = failedCount.toInt(),
        categoriesSummary = categoriesSummary.split(", "),
        interviewDate = interviewDate
    )

val QuestionSchemaV2.toDomainModel
    get() = Question(
        id = id,
        answer = answer,
        question = question,
        difficulty = Difficulty.Intermediate,
        categories = categories.mapNotNull {
            Category.getForKey(it)
        }
    )