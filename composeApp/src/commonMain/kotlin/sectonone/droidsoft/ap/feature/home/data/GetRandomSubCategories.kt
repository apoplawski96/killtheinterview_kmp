package sectonone.droidsoft.ap.feature.home.data

import sectonone.droidsoft.ap.model.SubCategory
import sectonone.droidsoft.ap.model.TopCategory
import sectonone.droidsoft.ap.feature.subcategories.data.SubCategoriesRepository

class GetRandomSubCategories(private val subCategoriesRepository: SubCategoriesRepository) {

    data class Result(
        val topCategory: TopCategory,
        val subCategories: List<SubCategory>
    )

    fun invoke(): Result {
        val randomTopCategory =
            TopCategory.entries.filter { topCategory ->
                topCategory.subCategories.isNotEmpty()
            }.random()

        val subCategoriesForCategory =
            subCategoriesRepository.getSubCategories(randomTopCategory).shuffled()

        return Result(
            topCategory = randomTopCategory,
            subCategories = subCategoriesForCategory,
        )
    }
}