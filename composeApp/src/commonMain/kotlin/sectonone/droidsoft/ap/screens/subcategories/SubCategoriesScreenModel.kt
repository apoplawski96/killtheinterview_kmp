package sectonone.droidsoft.ap.screens.subcategories

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import sectonone.droidsoft.ap.feature.subcategories.data.SubCategoriesRepository
import sectonone.droidsoft.ap.model.SubCategory
import sectonone.droidsoft.ap.model.TopCategory

class SubCategoriesScreenModel(
    private val subCategoriesRepository: SubCategoriesRepository,
) : ScreenModel {

    sealed interface ViewState {
        object Loading : ViewState
        data class SubCategoriesLoaded(val categories: List<SubCategory>) : ViewState
    }

    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val state: StateFlow<ViewState> = _state

    fun initialize(category: TopCategory) {
        _state.update {
            val subCategories = subCategoriesRepository.getSubCategories(category)
            ViewState.SubCategoriesLoaded(subCategories)
        }
    }
}