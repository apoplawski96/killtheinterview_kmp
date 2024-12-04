package sectonone.droidsoft.ap.feature.list.data

import sectonone.droidsoft.ap.model.schema.QuestionSchema
import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.Difficulty
import sectonone.droidsoft.ap.model.Question
import sectonone.droidsoft.ap.model.SubCategory
import sectonone.droidsoft.ap.model.TopCategory
import sectonone.droidsoft.ap.model.allSubCategoriesFlatten
import sectonone.droidsoft.ap.model.schema.QuestionSchemaV2

class QuestionsMapper {

    fun map(questions: List<QuestionSchema>): List<Question> =
        questions.mapNotNull { questionSchema ->
            Question(
                id = questionSchema.id,
                answer = questionSchema.answer,
                question = questionSchema.question,
                difficulty = Difficulty.getForName(questionSchema.difficulty) ?: Difficulty.Intermediate,
                topCategory = TopCategory.getForName(questionSchema.topCategory) ?: return@mapNotNull null,
                subCategory = getSubCategoryForName(questionSchema.subCategory),
                topCategoryId = questionSchema.topCategoryId,
                subCategoryId = questionSchema.subCategoryId,
                categories = questionSchema.categories?.mapNotNull {
                    Category.getForKey(it)
                } ?: emptyList()
            )
        }

    fun mapV2(questions: List<QuestionSchemaV2>): List<Question> =
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

    private fun getSubCategoryForName(name: String): SubCategory? =
        allSubCategoriesFlatten.firstOrNull { subCategory ->
            name == subCategory.keyName
        }
}