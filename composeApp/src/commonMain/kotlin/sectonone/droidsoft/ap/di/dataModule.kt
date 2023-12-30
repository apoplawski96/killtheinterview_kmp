package sectonone.droidsoft.ap.di

import kotlinx.coroutines.Dispatchers
import sectonone.droidsoft.ap._legacy.QuestionsRepository
import sectonone.droidsoft.ap.data.QuestionsDataSource
import sectonone.droidsoft.ap.feature.categories.data.CategoriesRepository
import sectonone.droidsoft.ap.data.openAi.OpenAIPrompter
import sectonone.droidsoft.ap.feature.interview.data.AIInterviewQuestionsPrompter
import sectonone.droidsoft.ap.feature.subcategories.data.SubCategoriesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import sectonone.droidsoft.ap.json.ResourcesFileReader

val dataModule = module {
    singleOf(::QuestionsDataSource)
    singleOf(::SubCategoriesRepository)
    singleOf(::CategoriesRepository)
    singleOf(::QuestionsRepository)
    singleOf(::OpenAIPrompter)
    singleOf(::AIInterviewQuestionsPrompter)
    single { ResourcesFileReader(defaultDispatcher = Dispatchers.Default) }
}