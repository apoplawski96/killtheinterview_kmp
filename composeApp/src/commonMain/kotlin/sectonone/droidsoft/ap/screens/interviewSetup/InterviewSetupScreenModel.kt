package sectonone.droidsoft.ap.screens.interviewSetup

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import sectonone.droidsoft.ap.data.auth.UserSessionState
import sectonone.droidsoft.ap.data.model.Category
import sectonone.droidsoft.ap.screens.interviewSetup.model.SelectableCategory

internal class InterviewSetupScreenModel(
    private val userSessionState: UserSessionState,
) : ScreenModel {

    private val _viewState = MutableStateFlow<List<SelectableCategory>>(emptyList())
    val viewState = _viewState.asStateFlow()

    init {
        _viewState.value = Category.entries.map { category ->
            SelectableCategory(
                isSelected = false,
                category = category,
                isUnlocked = userSessionState.userHasPremium || category.isFreemium
            )
        }
    }

    fun toggleCategory(toggledCategory: SelectableCategory) {
        val currentState = viewState.value
        val stateModified = currentState.map { currentCategory ->
            if (currentCategory.category.ordinal == toggledCategory.category.ordinal) {
                currentCategory.copy(isSelected = !currentCategory.isSelected)
            } else {
                currentCategory
            }
        }
        _viewState.value = stateModified
    }
}