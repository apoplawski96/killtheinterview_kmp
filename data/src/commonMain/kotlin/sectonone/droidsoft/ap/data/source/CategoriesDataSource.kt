package sectonone.droidsoft.ap.data.source

import sectonone.droidsoft.ap.data.model.Category

interface CategoriesDataSource {
    suspend fun getCategories(): List<Category>?
}