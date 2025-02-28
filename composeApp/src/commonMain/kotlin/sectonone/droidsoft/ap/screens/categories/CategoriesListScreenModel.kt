package sectonone.droidsoft.ap.screens.categories

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sectonone.droidsoft.ap.data.model.Category
import sectonone.droidsoft.ap.data.model.PremiumItem
import sectonone.droidsoft.ap.data.repositories.CategoriesRepository

data class CategoryListItem(
    val category: PremiumItem<Category>,
    val questionsTotalCount: Int = -1,
    val questionsFreeCount: Int = -1,
)

class CategoriesListScreenModel(
    private val categoriesRepository: CategoriesRepository,
) : ScreenModel {

    private val _state = MutableStateFlow<List<CategoryListItem>?>(emptyList())
    val state = _state.asStateFlow()

    init {
        screenModelScope.launch {
            _state.value = categoriesRepository.getCategories()?.map {
                CategoryListItem(it)
            }
        }
    }
}