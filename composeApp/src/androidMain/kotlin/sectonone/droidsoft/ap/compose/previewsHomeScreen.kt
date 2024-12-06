package sectonone.droidsoft.ap.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import sectonone.droidsoft.ap.model.HomeScreenMenuItem
import sectonone.droidsoft.ap.model.UIHomeScreenSection
import sectonone.droidsoft.ap.screens.home.HomeScreenContent
import sectonone.droidsoft.ap.screens.home.HomeScreenModel
import sectonone.droidsoft.ap.screens.home.homeScreenMock

@Preview
@Composable
private fun PreviewHomeScreenLight() = PreviewTheme(isDark = false) {
    HomeScreenContent(state = homeScreenMock, onMenuItemClicked = {})
}

@Preview
@Composable
private fun PreviewHomeScreenDark() = PreviewTheme(isDark = true) {
    HomeScreenContent(state = homeScreenMock, onMenuItemClicked = {})
}