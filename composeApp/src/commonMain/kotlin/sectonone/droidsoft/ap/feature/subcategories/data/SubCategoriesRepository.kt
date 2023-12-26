package sectonone.droidsoft.ap.feature.subcategories.data

import sectonone.droidsoft.ap.model.AndroidSubCategory
import sectonone.droidsoft.ap.model.IOSSubCategory
import sectonone.droidsoft.ap.model.SubCategory
import sectonone.droidsoft.ap.model.TopCategory

class SubCategoriesRepository {

    fun getSubCategories(category: TopCategory): List<SubCategory> = when(category) {
        TopCategory.ANDROID -> AndroidSubCategory.entries
        TopCategory.IOS -> IOSSubCategory.entries
        else -> emptyList()
    }
}