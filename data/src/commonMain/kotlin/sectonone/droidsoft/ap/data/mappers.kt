package sectonone.droidsoft.ap.data

import dev.gitlive.firebase.auth.FirebaseUser
import sectonone.droidsoft.ap.data.model.Category
import sectonone.droidsoft.ap.data.model.InterviewHistorySummary
import sectonone.droidsoft.ap.data.model.Question
import sectonone.droidsoft.ap.data.model.UserAuth
import sectonone.droidsoft.ap.data.model.schema.QuestionScheme
import sectonone.droidsoft.ap.db.InterviewSummary
import sectonone.droidsoft.ap.db.QuestionBookmark

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
        isBookmarked = true,
        categories = categories
            .split(",")
            .mapNotNull {
                println("2137 - mapNotNull, $it")
                Category.getForKey(it)
            }
    )

fun FirebaseUser?.mapUser(): UserAuth? {
    if (this == null) return null
    return UserAuth(
        uid = uid,
        email = email,
        displayName = displayName,
        photoUrl = photoURL,
    )
}