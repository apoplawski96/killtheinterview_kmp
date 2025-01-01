package sectonone.droidsoft.ap.data

import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.Question
import sectonone.droidsoft.ap.model.allQuestionsFiles
import sectonone.droidsoft.ap.model.toDomainModel

class QuestionsRepository(
    private val questionsDataSource: LocalQuestionsDataSource,
) {

    suspend fun getQuestions(
        categories: List<Category> = Category.entries,
        questionsLimit: Int? = null
    ): List<Question> {
        val questionsRaw = questionsDataSource.getQuestions(
            files = categories.map { it.fileWithQuestions }
        )
        return if (questionsLimit != null) {
            questionsRaw.take(questionsLimit)
        } else {
            questionsRaw
        }.let {
            it.map { question ->
                question.toDomainModel
            }
        }
    }

    suspend fun getQuestionsForIds(ids: List<Int>): List<Question> {
        val allQuestions = questionsDataSource.getQuestions(allQuestionsFiles)
        val questionsForGivenId = buildList {
            ids.forEach { questionId ->
                add(allQuestions.find { it.id == questionId })
            }
        }
        return questionsForGivenId.mapNotNull { it?.toDomainModel }
    }
}