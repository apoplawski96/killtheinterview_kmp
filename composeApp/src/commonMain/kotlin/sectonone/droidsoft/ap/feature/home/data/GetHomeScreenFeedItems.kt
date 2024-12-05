package sectonone.droidsoft.ap.feature.home.data

import sectonone.droidsoft.ap.model.HomeScreenFeedItem
import sectonone.droidsoft.ap.model.HomeScreenMenuItem

class GetHomeScreenFeedItems() {

    fun get(): List<HomeScreenFeedItem> {
        return listOf(
            HomeScreenFeedItem.MenuItems(
                items = listOf(
                    HomeScreenMenuItem.AI_INTERVIEW,
                    HomeScreenMenuItem.QUESTIONS_CATEGORIES
                )
            ),
        )
    }
}