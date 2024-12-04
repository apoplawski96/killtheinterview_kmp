package sectonone.droidsoft.ap.data

import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.TopCategory

class CategoriesRepository {

    fun getTopCategories(): List<TopCategory> = TopCategory.entries

    fun getCategories(): List<Category> = Category.entries
}