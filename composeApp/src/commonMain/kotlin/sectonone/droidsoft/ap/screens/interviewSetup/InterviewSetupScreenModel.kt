package sectonone.droidsoft.ap.screens.interviewSetup

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sectonone.droidsoft.ap.data.auth.UserSessionState
import sectonone.droidsoft.ap.data.model.Category
import sectonone.droidsoft.ap.data.repositories.CategoriesRepository
import sectonone.droidsoft.ap.screens.interviewSetup.model.SelectableCategory

internal class InterviewSetupScreenModel(
    private val categoriesRepository: CategoriesRepository,
) : ScreenModel {

    private val _viewState = MutableStateFlow<List<SelectableCategory>?>(emptyList())
    val viewState = _viewState.asStateFlow()

    init {
        screenModelScope.launch {
            _viewState.value = categoriesRepository.getCategories()?.map { category ->
                SelectableCategory(
                    isSelected = false,
                    category = category,
                )
            }
        }
    }

    fun toggleCategory(toggledCategory: SelectableCategory) {
        val currentState = viewState.value
        val stateModified = currentState?.map { currentCategory ->
            if (toggledCategory.category.unlocked && currentCategory.category.item.ordinal == toggledCategory.category.item.ordinal) {
                currentCategory.copy(isSelected = !currentCategory.isSelected)
            } else {
                currentCategory
            }
        }
        _viewState.value = stateModified
    }
}