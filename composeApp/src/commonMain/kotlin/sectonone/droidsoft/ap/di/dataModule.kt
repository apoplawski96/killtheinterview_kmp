package sectonone.droidsoft.ap.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import sectonone.droidsoft.ap.data.InterviewHistoryRepository
import sectonone.droidsoft.ap.data.QuestionsDataSource
import sectonone.droidsoft.ap.data.QuestionsRepository
import sectonone.droidsoft.ap.data.openAi.OpenAIPrompter
import sectonone.droidsoft.ap.feature.interview.data.AIInterviewQuestionsPrompter
import sectonone.droidsoft.ap.json.ResourcesFileReader

val dataModule = module {
    singleOf(::QuestionsDataSource)
    singleOf(::QuestionsRepository)
    singleOf(::InterviewHistoryRepository)
    singleOf(::OpenAIPrompter)
    singleOf(::AIInterviewQuestionsPrompter)
    single { ResourcesFileReader(defaultDispatcher = Dispatchers.Default) }
}