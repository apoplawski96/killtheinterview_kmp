package sectonone.droidsoft.ap.data.repository

import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.HomeScreenMenuItem
import sectonone.droidsoft.ap.model.UIHomeScreenSection
import sectonone.droidsoft.ap.screens.home.interviewsSummaryMock

class HomeRepository(private val questionsRepository: QuestionsRepository) {

    suspend fun get(): List<UIHomeScreenSection> = listOf(
        UIHomeScreenSection.MenuItems(
            listOf(
                HomeScreenMenuItem.QUESTIONS_CATEGORIES,
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