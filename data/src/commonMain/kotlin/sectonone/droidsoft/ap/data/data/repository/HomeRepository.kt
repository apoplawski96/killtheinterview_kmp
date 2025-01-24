package sectonone.droidsoft.ap.data.data.repository

import sectonone.droidsoft.ap.data.model.Category
import sectonone.droidsoft.ap.data.model.HomeScreenMenuItem
import sectonone.droidsoft.ap.data.model.UIHomeScreenSection
import sectonone.droidsoft.ap.data.model.interviewSummary
import sectonone.droidsoft.ap.data.data.getRandomUniqueEnumValues

class HomeRepository(private val questionsRepository: QuestionsRepository) {

    suspend fun get(): List<UIHomeScreenSection> = listOf(
        UIHomeScreenSection.MenuItems(
            listOf(
                HomeScreenMenuItem.LEARN_QUESTIONS,
                HomeScreenMenuItem.BOOKMARKS,
                HomeScreenMenuItem.CHAT_INTERVIEW,
            )
        ),
        UIHomeScreenSection.PagerCarousel(
            getRandomQuestions()
        ),
        UIHomeScreenSection.RecommendedCategoriesCarousel(
            listOf(
                Category.Android,
                Category.Compose,
                Category.AndroidSecurity,
                Category.DesignPatterns,
                Category.Kotlin,
            )
        ),
        UIHomeScreenSection.InterviewHistorySummaryUI(
            interviewsSummaryMock
        ),
    )

    private suspend fun getRandomQuestions(): List<UIHomeScreenSection.PagerCarousel.CarouselItem.CategoryCard> {
        val questions = questionsRepository.getQuestions()?.shuffled()?.take(10) ?: emptyList()
        return questions.map { question ->
            UIHomeScreenSection.PagerCarousel.CarouselItem.CategoryCard(
                item = question.categories.first(),
                question = question
            )
        }
    }
}

val interviewsSummaryMock = listOf(
    interviewSummary(answeredCount = 2, failedCount = 10, categories = getRandomUniqueEnumValues<Category>(3).map { it.displayName }),
    interviewSummary(answeredCount = 20, failedCount = 10, categories = getRandomUniqueEnumValues<Category>(2).map { it.displayName }),
    interviewSummary(answeredCount = 15, failedCount = 10, categories = getRandomUniqueEnumValues<Category>(3).map { it.displayName }),
    interviewSummary(answeredCount = 5, failedCount = 10, categories = getRandomUniqueEnumValues<Category>(4).map { it.displayName }),
    interviewSummary(answeredCount = 8, failedCount = 1, categories = getRandomUniqueEnumValues<Category>(2).map { it.displayName }),
    interviewSummary(answeredCount = 2, failedCount = 10, categories = getRandomUniqueEnumValues<Category>(2).map { it.displayName }),
)