package sectonone.droidsoft.ap.data.dataSource

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import sectonone.droidsoft.ap.db.KTIDatabase
import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.QuestionHistory
import sectonone.droidsoft.ap.model.toDomainModel

class InterviewHistoryDataSource(
    private val database: KTIDatabase
) {
    private val queries = database.interviewSummaryQueries

    fun getAllInterviewsSummaryAsFlow() =
        queries.selectWholeInterviewSummaryHistory()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { history ->
                history.map { summary -> summary.toDomainModel }
            }

    fun getInterviewSummaryAsFlow(interviewId: Long) =
        queries.selectInterviewSummaryById(interviewId)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)

    fun getInterviewCategoriesAsFlow(interviewId: Long) =
        queries.selectCategoriesByInterviewId(interviewId)
            .asFlow()
            .mapToList(Dispatchers.IO)

    fun getInterviewQuestionsHistoryAsFlow(interviewId: Long) =
        queries.selectQuestionHistoryByInterviewId(interviewId)
            .asFlow()
            .mapToList(Dispatchers.IO)

    suspend fun saveInterview(
        answeredCount: Int,
        failedCount: Number,
        categories: List<Category>,
        questionsHistory: List<QuestionHistory>
    ) = withContext(Dispatchers.IO) {
        database.transaction {
            queries.insertInterviewSummary(
                answeredCount = answeredCount.toLong(),
                failedCount = failedCount.toLong(),
                categoriesSummary = categories.joinToString(", ")
            )

            val interviewId = database.interviewSummaryQueries.lastInsertedId().executeAsOne()

            categories.forEach { category ->
                queries.insertCategory(
                    interviewId = interviewId,
                    name = category.name,
                    key = category.key,
                )
            }

            questionsHistory.forEach { questionHistory ->
                queries.insertQuestionHistory(
                    interviewId = interviewId,
                    question = questionHistory.question.question,
                    answer = questionHistory.question.answer,
                    isCorrect = questionHistory.userKnewTheAnswer,
                    questionId = questionHistory.question.id.toLong(),
                )
            }
        }
    }
}