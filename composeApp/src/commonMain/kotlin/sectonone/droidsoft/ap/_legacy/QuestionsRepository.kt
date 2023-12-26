package sectonone.droidsoft.ap._legacy

import sectonone.droidsoft.ap.data.QuestionsDataSource
import sectonone.droidsoft.ap.feature.list.data.QuestionsMapper
import sectonone.droidsoft.ap.model.Question
import sectonone.droidsoft.ap.model.TopCategory

class QuestionsRepository(
    private val questionsDataSource: QuestionsDataSource,
    private val questionsMapper: QuestionsMapper,
) {

    fun getAllQuestions(): List<Question> {
        return fetchAndMapQuestions()
    }

    fun getQuestionsForCategories(categories: List<TopCategory>): List<Question> {

        return fetchAndMapQuestions().filter { question ->
//            categories.contains(question.topCategory)
            categories.any { it == question.topCategory }
        }
    }

    private fun fetchAndMapQuestions(): List<Question> {
        return questionsMapper.map(questionsDataSource.getAll())
    }
}