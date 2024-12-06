package sectonone.droidsoft.ap.feature.list.data

import sectonone.droidsoft.ap.data.QuestionsDataSource
import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.Question

class GetQuestionsList(
    private val questionsDataSource: QuestionsDataSource,
    private val questionsMapper: QuestionsMapper,
) {

    sealed interface Result {
        data class Success(val questions: List<Question>) : Result
        data object Error : Result
    }

    suspend operator fun invoke(categories: List<Category>): Result = try {
        val questions = questionsDataSource.getQuestions(categories.map { it.questionsFile })
        Result.Success(questions = questionsMapper.mapV2(questions))
    } catch (e: Exception) {
        Result.Error
    }
}