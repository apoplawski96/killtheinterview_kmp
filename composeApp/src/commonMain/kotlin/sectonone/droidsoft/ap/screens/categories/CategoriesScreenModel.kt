package sectonone.droidsoft.ap.screens.categories

import cafe.adriel.voyager.core.model.ScreenModel
import sectonone.droidsoft.ap.feature.categories.data.CategoriesRepository
import sectonone.droidsoft.ap.model.TopCategory
import sectonone.droidsoft.ap.navigation.Navigator
import sectonone.droidsoft.ap.navigation.model.Destinations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CategoriesScreenModel(
    private val categoriesRepository: CategoriesRepository,
    private val navigator: Navigator,
) : ScreenModel {

    sealed interface ViewState {
        data object Loading : ViewState
        data class CategoriesLoaded(val categories: List<TopCategory>) : ViewState
    }

    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val state: StateFlow<ViewState> = _state

    fun initialize() {
        _state.update {
            ViewState.CategoriesLoaded(
                categories = categoriesRepository.getTopCategories()
            )
        }
    }

    fun categorySelected(category: TopCategory) {
        if (category.subCategories.isEmpty().not()) {
            navigator.navigate(Destinations.SubCategories.destination(category.id))
        } else {
            navigator.navigate(
                Destinations.QuestionsList.destination(
                    topCategoryId = category.id,
                    subCategoryId = null
                )
            )
        }
    }
}