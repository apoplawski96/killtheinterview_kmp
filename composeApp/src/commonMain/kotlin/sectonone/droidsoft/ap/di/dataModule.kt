package sectonone.droidsoft.ap.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import sectonone.droidsoft.ap.data.repository.InterviewRepository
import sectonone.droidsoft.ap.data.source.LocalQuestionsDataSource
import sectonone.droidsoft.ap.data.repository.QuestionsRepository
import sectonone.droidsoft.ap.data.source.InterviewHistoryDataSource
import sectonone.droidsoft.ap.data.openAi.OpenAIPrompter
import sectonone.droidsoft.ap.data.repository.HomeRepository
import sectonone.droidsoft.ap.data.source.QuestionsDataSource
import sectonone.droidsoft.ap.feature.interview.data.AIInterviewQuestionsPrompter
import sectonone.droidsoft.ap.json.ResourcesFileReader

val dataModule = module {
    // Data sources
    singleOf(::LocalQuestionsDataSource) bind QuestionsDataSource::class
    singleOf(::InterviewHistoryDataSource)
    // Repositories
    singleOf(::QuestionsRepository)
    singleOf(::InterviewRepository)
    singleOf(::HomeRepository)
    // Miscellaneous
    singleOf(::OpenAIPrompter)
    singleOf(::AIInterviewQuestionsPrompter)
    // IO
    single { ResourcesFileReader(defaultDispatcher = Dispatchers.Default) }
}