package sectonone.droidsoft.ap.model

sealed interface HomeScreenFeedItem {
    data class MenuItems(
        val items: List<HomeScreenMenuItem>
    )  : HomeScreenFeedItem
    data class LastLearnedSubCategory(
        val subCategory: SubCategory
    ) : HomeScreenFeedItem
    data class LastLearnedSubCategoriesCarousel(
        val subCategories: List<SubCategory>,
    ) : HomeScreenFeedItem
    data class RandomSubCategoriesCarousel(
        val subCategories: List<SubCategory>,
        val topCategory: TopCategory
    ) : HomeScreenFeedItem
    data class RandomBookmarkedQuestion(
        val question: Question
    ) : HomeScreenFeedItem
}
