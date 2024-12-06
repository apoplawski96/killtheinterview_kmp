package sectonone.droidsoft.ap.feature.home.data

import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.HomeScreenMenuItem
import sectonone.droidsoft.ap.model.UIHomeScreenSection
import sectonone.droidsoft.ap.screens.home.interviewsSummaryMock

class GetHomeFeed {

    fun get(): List<UIHomeScreenSection> {
        return listOf(
            UIHomeScreenSection.MenuItems(
                items = listOf(
                    HomeScreenMenuItem.QUESTIONS_CATEGORIES,
                    HomeScreenMenuItem.CHAT_INTERVIEW,
                )
            ),
            UIHomeScreenSection.RecommendedCategoriesCarousel(
                listOf(
                    Category.Android, Category.Compose, Category.AndroidSecurity, Category.DesignPatterns, Category.Kotlin
                )
            ),
            UIHomeScreenSection.InterviewHistorySummary(
                items = interviewsSummaryMock
            ),
        )
    }
}