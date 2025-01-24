package sectonone.droidsoft.ap.data.model

// IDEAS
// 1. Add interview history summary section with option to "View all" // DONE
// 2. Add search bar?
// 3. Random question
// 4. Add "About me" section
// 5. Add Onboarding
// 6. Random questions carousel with auto scroll and paging
// 7. Library, saved questions, saved categories
// 8. "Explore" under search?
// 9. Animated icons that move every x seconds
// 10. Start with UI & mock data, then code functionality
// 11. Daily challenge

enum class HomeScreenMenuItem(val displayName: String, val assetResourcePath: String) {
    LEARN_QUESTIONS(displayName = "Learn questions", assetResourcePath = "book.png"),
    CHAT_INTERVIEW(displayName = "Start interview", assetResourcePath = "student.png"),
    BOOKMARKS(displayName = "Bookmarks", assetResourcePath = "bookmarks.png"),
}

sealed interface UIHomeScreenSection {

    data class MenuItems(
        val items: List<HomeScreenMenuItem>
    ) : UIHomeScreenSection

    data class RandomBookmarkedQuestion(
        val question: Question
    ) : UIHomeScreenSection

    data class InterviewHistorySummaryUI(
        val items: List<InterviewHistorySummary>
    ) : UIHomeScreenSection

    data class RecommendedCategory(
        val category: Category,
        val headline: String,
        val paragraph: String,
    ) : UIHomeScreenSection

    data class RecommendedCategoriesCarousel(
        val items: List<Category>
    ) : UIHomeScreenSection

    data class RandomQuestionsCarousel(
        val items: List<Question>
    ) : UIHomeScreenSection

    data class DailyChallenge(
        val title: String,
    ) : UIHomeScreenSection

    data class BookmarkedCategories(
        val items: List<Category>
    ) : UIHomeScreenSection

    data class BookmarkedQuestions(
        val items: List<Question>
    ) : UIHomeScreenSection

    data class PagerCarousel(val items: List<CarouselItem>) : UIHomeScreenSection {

        sealed class CarouselItem(
            val imagePath: String,
            open val question: Question,
            open val categories: List<Category>,
        ) {
            data class QuestionCard(
                val item: Question
            ) : CarouselItem(
                question = item,
                categories = item.categories,
                imagePath = item.categories.firstOrNull()?.imageFile ?: "",
            )

            data class CategoryCard(
                val item: Category,
                override val question: Question
            ) : CarouselItem(
                imagePath = item.imageFile,
                question = question,
                categories = listOf(item),
            )
        }
    }
}
