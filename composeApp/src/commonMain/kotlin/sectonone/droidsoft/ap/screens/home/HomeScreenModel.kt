package sectonone.droidsoft.ap.screens.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import sectonone.droidsoft.ap.model.HomeScreenFeedItem
import sectonone.droidsoft.ap.model.HomeScreenMenuItem
import sectonone.droidsoft.ap.navigation.Navigator
import sectonone.droidsoft.ap.navigation.model.Destinations
import sectonone.droidsoft.ap.feature.home.data.GetHomeScreenFeedItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenModel(
    private val getHomeScreenFeedItems: GetHomeScreenFeedItems,
    private val navigator: Navigator,
) : ScreenModel {

    sealed interface ViewState {
        data object Loading : ViewState
        data class HomeItems(val items: List<HomeScreenFeedItem>) : ViewState
    }

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState = _viewState.asStateFlow()

    fun initialize() {
        screenModelScope.launch {
            _viewState.update {
                ViewState.HomeItems(items = getHomeScreenFeedItems.get())
            }
        }
    }

    fun onItemClicked(item: HomeScreenMenuItem) {
        val destination = when(item) {
            HomeScreenMenuItem.AI_INTERVIEW -> Destinations.AIInterview
            HomeScreenMenuItem.QUESTIONS_CATEGORIES -> Destinations.Categories
        }
        navigator.navigate(destination = destination)
    }
}