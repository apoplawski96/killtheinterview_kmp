package sectonone.droidsoft.ap.screens.interviewSetup

import cafe.adriel.voyager.core.model.ScreenModel
import sectonone.droidsoft.ap.feature.categories.data.CategoriesRepository
import sectonone.droidsoft.ap.screens.interviewSetup.model.SelectableCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InterviewSetupScreenModel(categoriesRepository: CategoriesRepository) : ScreenModel {

    private val _viewState = MutableStateFlow<List<SelectableCategory>>(emptyList())
    val viewState = _viewState.asStateFlow()

    init {
        _viewState.value = categoriesRepository.getTopCategories().map { category ->
            SelectableCategory(category = category, isSelected = false)
        }
    }

    fun toggleCategory(toggledCategory: SelectableCategory) {
        val currentState = viewState.value
        val stateModified = currentState.map { currentCategory ->
            if (currentCategory.category.id == toggledCategory.category.id) {
                currentCategory.copy(isSelected = !currentCategory.isSelected)
            } else {
                currentCategory
            }
        }
        _viewState.value = stateModified
    }
}