package sectonone.droidsoft.ap.feature.categories.data

import sectonone.droidsoft.ap.model.TopCategory

class CategoriesRepository {

    fun getTopCategories(): List<TopCategory> = TopCategory.entries
}