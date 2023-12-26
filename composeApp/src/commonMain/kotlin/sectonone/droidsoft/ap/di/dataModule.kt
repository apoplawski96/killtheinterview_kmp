package sectonone.droidsoft.ap.di

import sectonone.droidsoft.ap._legacy.QuestionsRepository
import sectonone.droidsoft.ap.data.QuestionsDataSource
import sectonone.droidsoft.ap.feature.categories.data.CategoriesRepository
import sectonone.droidsoft.ap.data.openAi.OpenAIPrompter
import sectonone.droidsoft.ap.feature.interview.data.AIInterviewQuestionsPrompter
import sectonone.droidsoft.ap.feature.subcategories.data.SubCategoriesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::QuestionsDataSource)
    singleOf(::SubCategoriesRepository)
    singleOf(::CategoriesRepository)
    singleOf(::QuestionsRepository)
    singleOf(::OpenAIPrompter)
    singleOf(::AIInterviewQuestionsPrompter)
}