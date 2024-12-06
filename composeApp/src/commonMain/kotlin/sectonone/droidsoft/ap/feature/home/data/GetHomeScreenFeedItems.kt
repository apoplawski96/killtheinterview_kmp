package sectonone.droidsoft.ap.feature.home.data

import sectonone.droidsoft.ap.compose.getRandomUniqueEnumValues
import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.UIHomeScreenSection
import sectonone.droidsoft.ap.model.HomeScreenMenuItem
import sectonone.droidsoft.ap.model.InterviewConfiguration
import sectonone.droidsoft.ap.model.InterviewSummary
import sectonone.droidsoft.ap.model.interviewSummary
import sectonone.droidsoft.ap.screens.home.HomeScreenModel
import sectonone.droidsoft.ap.screens.home.interviewsSummaryMock

class GetHomeScreenFeedItems() {

    fun get(): List<UIHomeScreenSection> {
        return listOf(
            UIHomeScreenSection.MenuItems(
                items = listOf(
                    HomeScreenMenuItem.CHAT_INTERVIEW,
                    HomeScreenMenuItem.QUESTIONS_CATEGORIES
                )
            ),
            UIHomeScreenSection.InterviewHistorySummary(
                items = interviewsSummaryMock
            )
        )
    }
}