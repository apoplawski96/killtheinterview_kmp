package sectonone.droidsoft.ap.feature.list.data

import sectonone.droidsoft.ap.data.LocalQuestionsDataSource
import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.Question
import sectonone.droidsoft.ap.model.toDomainModel

class GetQuestionsList(
    private val questionsDataSource: LocalQuestionsDataSource,
) {

    sealed interface Result {
        data class Success(val questions: List<Question>) : Result
        data object Error : Result
    }

    suspend operator fun invoke(categories: List<Category>): Result = try {
        val questions = questionsDataSource.getQuestions(categories.map { it.fileWithQuestions })
        Result.Success(questions = questions.map { it.toDomainModel })
    } catch (e: Exception) {
        Result.Error
    }
}