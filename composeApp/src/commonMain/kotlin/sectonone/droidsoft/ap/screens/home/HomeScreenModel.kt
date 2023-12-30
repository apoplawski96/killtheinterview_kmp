package sectonone.droidsoft.ap.screens.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sectonone.droidsoft.ap.feature.home.data.GetHomeScreenFeedItems
import sectonone.droidsoft.ap.json.OtherFileReader
import sectonone.droidsoft.ap.model.HomeScreenFeedItem

class HomeScreenModel(
    private val getHomeScreenFeedItems: GetHomeScreenFeedItems,
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

            val fileReader = OtherFileReader()
            fileReader.loadJsonFile("questions_git.json")
        }
    }
}