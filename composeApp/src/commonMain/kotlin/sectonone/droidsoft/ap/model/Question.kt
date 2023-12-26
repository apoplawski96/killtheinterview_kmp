package sectonone.droidsoft.ap.model

import sectonone.droidsoft.ap._legacy.DeprecatedCategory

data class Question(
    val id: Int = -1,
    val topCategoryId: Int = -1,
    val subCategoryId: Int = -1,
    val category: DeprecatedCategory,
    val topCategory: TopCategory = TopCategory.ANDROID,
    val subCategory: SubCategory? = AndroidSubCategory.Basics,
    val difficulty: Difficulty = Difficulty.Beginner,
    val question: String,
    val answer: String,
)
