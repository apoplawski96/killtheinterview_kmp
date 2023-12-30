package sectonone.droidsoft.ap._legacy

import sectonone.droidsoft.ap.data.QuestionsDataSource
import sectonone.droidsoft.ap.feature.list.data.QuestionsMapper
import sectonone.droidsoft.ap.model.Question
import sectonone.droidsoft.ap.model.TopCategory

class QuestionsRepository(
    private val questionsDataSource: QuestionsDataSource,
    private val questionsMapper: QuestionsMapper,
) {

    suspend fun getQuestionsForCategories(categories: List<TopCategory>): List<Question> {
        return questionsMapper.map(questionsDataSource.getAll()).filter { question ->
            categories.any { it == question.topCategory }
        }
    }
}