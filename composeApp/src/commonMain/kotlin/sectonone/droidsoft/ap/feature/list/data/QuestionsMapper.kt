package sectonone.droidsoft.ap.feature.list.data

import sectonone.droidsoft.ap.model.schema.QuestionSchema
import sectonone.droidsoft.ap._legacy.DeprecatedCategory
import sectonone.droidsoft.ap.model.Difficulty
import sectonone.droidsoft.ap.model.Question
import sectonone.droidsoft.ap.model.SubCategory
import sectonone.droidsoft.ap.model.TopCategory
import sectonone.droidsoft.ap.model.allSubCategoriesFlatten

class QuestionsMapper {

    fun map(questions: List<QuestionSchema>): List<Question> =
        questions.mapNotNull { questionSchema ->
            Question(
                id = questionSchema.id,
                answer = questionSchema.answer,
                question = questionSchema.question,
                category = DeprecatedCategory.Android,
                difficulty = Difficulty.getForName(questionSchema.difficulty) ?: Difficulty.Intermediate,
                topCategory = TopCategory.getForName(questionSchema.topCategory) ?: return@mapNotNull null,
                subCategory = getSubCategoryForName(questionSchema.subCategory),
                topCategoryId = questionSchema.topCategoryId,
                subCategoryId = questionSchema.subCategoryId,
            )
        }

    private fun getSubCategoryForName(name: String): SubCategory? =
        allSubCategoriesFlatten.firstOrNull { subCategory ->
            name == subCategory.keyName
        }
}