package sectonone.droidsoft.ap.data

import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import sectonone.droidsoft.ap.data.dataSource.InterviewHistoryDataSource
import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.InterviewHistoryDetails
import sectonone.droidsoft.ap.model.InterviewHistorySummary
import sectonone.droidsoft.ap.model.QuestionHistory

class InterviewHistoryRepository(
    private val dataSource: InterviewHistoryDataSource,
    private val questionsRepository: QuestionsRepository,
) {

    fun getAllInterviewsSummaryAsFlow() = dataSource.getAllInterviewsSummaryAsFlow()

    suspend fun getInterviewSummary(interviewId: Long) =
        combine(
            dataSource.getInterviewSummaryAsFlow(interviewId),
            dataSource.getInterviewCategoriesAsFlow(interviewId),
            dataSource.getInterviewQuestionsHistoryAsFlow(interviewId)
        ) { interview, categories, questions ->
            if (interview == null) return@combine null

            InterviewHistoryDetails(
                categories = categories.mapNotNull {
                    Category.getForKey(it.key)
                },
                questionsHistory = questionsRepository.getQuestionsForIds(
                    questions.map { it.questionId.toInt() }
                ),
                summary = InterviewHistorySummary(
                    id = interview.id.toInt(),
                    answeredCount = interview.answeredCount.toInt(),
                    failedCount = interview.failedCount.toInt(),
                    categoriesSummary = categories.map { it.name },
                    interviewDate = interview.interviewDate
                )
            )
        }.first()

    suspend fun saveInterview(
        answeredCount: Int,
        failedCount: Number,
        categories: List<Category>,
        questionsHistory: List<QuestionHistory>
    ) {
        dataSource.saveInterview(
            answeredCount,
            failedCount,
            categories,
            questionsHistory
        )
    }
}