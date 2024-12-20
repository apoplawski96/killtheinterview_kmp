package sectonone.droidsoft.ap.feature.home.data

import sectonone.droidsoft.ap.data.QuestionsRepository
import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.HomeScreenMenuItem
import sectonone.droidsoft.ap.model.Question
import sectonone.droidsoft.ap.model.UIHomeScreenSection
import sectonone.droidsoft.ap.screens.home.interviewsSummaryMock

class GetHomeFeed(private val questionsRepository: QuestionsRepository) {

    suspend fun get(): List<UIHomeScreenSection> {
        return listOf(
            UIHomeScreenSection.MenuItems(
                items = listOf(
                    HomeScreenMenuItem.QUESTIONS_CATEGORIES,
                    HomeScreenMenuItem.CHAT_INTERVIEW,
                )
            ),
            UIHomeScreenSection.PagerCarousel(
                items = listOf(
                    categoryWithRandomQuestion(Category.AndroidCore),
                    categoryWithRandomQuestion(Category.IOS),
                    categoryWithRandomQuestion(Category.ProgrammingParadigms),
                    categoryWithRandomQuestion(Category.AndroidAppArchitecture),
                    categoryWithRandomQuestion(Category.DesignPatterns),
                    categoryWithRandomQuestion(Category.Compose),
                    categoryWithRandomQuestion(Category.Coroutines),
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

    private suspend fun categoryWithRandomQuestion(category: Category): UIHomeScreenSection.PagerCarousel.CarouselItem.CategoryCard {
        val question = questionsRepository.getQuestions(listOf(category)).random()
        return UIHomeScreenSection.PagerCarousel.CarouselItem.CategoryCard(category, question)
    }
}