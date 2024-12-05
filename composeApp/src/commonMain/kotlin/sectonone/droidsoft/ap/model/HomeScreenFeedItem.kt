package sectonone.droidsoft.ap.model

sealed interface HomeScreenFeedItem {
    data class MenuItems(
        val items: List<HomeScreenMenuItem>
    )  : HomeScreenFeedItem
    data class RandomBookmarkedQuestion(
        val question: Question
    ) : HomeScreenFeedItem
}
