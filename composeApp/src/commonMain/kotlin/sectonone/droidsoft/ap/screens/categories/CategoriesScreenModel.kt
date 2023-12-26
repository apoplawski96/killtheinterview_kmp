package sectonone.droidsoft.ap.screens.categories

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import sectonone.droidsoft.ap.feature.categories.data.CategoriesRepository
import sectonone.droidsoft.ap.model.TopCategory
import sectonone.droidsoft.ap.navigation.model.Destinations

class CategoriesScreenModel(
    private val categoriesRepository: CategoriesRepository,
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
}