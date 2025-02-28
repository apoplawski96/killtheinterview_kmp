package sectonone.droidsoft.ap.data.source.impl

import sectonone.droidsoft.ap.data.model.Category
import sectonone.droidsoft.ap.data.source.CategoriesDataSource

class LocalCategoriesDataSource : CategoriesDataSource {
    override suspend fun getCategories(): List<Category>? {
        return Category.entries
    }
}