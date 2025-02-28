package sectonone.droidsoft.ap.screens.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sectonone.droidsoft.ap.Hejka
import sectonone.droidsoft.ap.data.model.UIHomeScreenSection
import sectonone.droidsoft.ap.data.repositories.HomeRepository

internal class HomeScreenModel(
    private val getHomeScreenFeedItems: HomeRepository,
) : ScreenModel {

    sealed interface ViewState {
        data object Loading : ViewState
        data class HomeItems(val items: List<UIHomeScreenSection>) : ViewState
    }

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState = _viewState.asStateFlow()

    fun initialize() {
        println("2137 - ${Hejka.accessMe}")
        screenModelScope.launch {
            _viewState.update {
                ViewState.HomeItems(items = getHomeScreenFeedItems.get())
            }
        }
    }
}