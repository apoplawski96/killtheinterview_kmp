package sectonone.droidsoft.ap.data

import sectonone.droidsoft.ap.feature.list.data.QuestionsMapper
import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.Question

class QuestionsRepository(
    private val questionsDataSource: QuestionsDataSource,
    private val questionsMapper: QuestionsMapper,
) {

    suspend fun getQuestions(categories: List<Category>): List<Question> =
        questionsMapper.mapV2(questionsDataSource.getQuestions(categories.map { it.fileName }))
            .filter { question ->
                categories.any { inputCategory ->
                    question.categories.any { it.name == inputCategory.name }
                }
            }.distinct()
}