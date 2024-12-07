package sectonone.droidsoft.ap.feature.list.data

import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.Difficulty
import sectonone.droidsoft.ap.model.Question
import sectonone.droidsoft.ap.model.schema.QuestionSchemaV2

class QuestionsMapper {

    fun map(questions: List<QuestionSchemaV2>): List<Question> =
        questions.map { questionSchema ->
            Question(
                id = questionSchema.id,
                answer = questionSchema.answer,
                question = questionSchema.question,
                difficulty = Difficulty.Intermediate,
                categories = questionSchema.categories.mapNotNull {
                    Category.getForKey(it)
                }
            )
        }
}