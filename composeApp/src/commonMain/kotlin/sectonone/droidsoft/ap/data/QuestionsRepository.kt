package sectonone.droidsoft.ap.data

import sectonone.droidsoft.ap.feature.list.data.QuestionsMapper
import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.Question

class QuestionsRepository(
    private val questionsDataSource: QuestionsDataSource,
    private val questionsMapper: QuestionsMapper,
) {

    suspend fun getQuestions(categories: List<Category>): List<Question> {
        return questionsMapper.map(
            questions = questionsDataSource.getQuestions(
                files = categories.map { it.questionsFile }
            )
        )
    }
}