package sectonone.droidsoft.ap.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import sectonone.droidsoft.ap.data.data.file.ResourcesFileReader
import sectonone.droidsoft.ap.data.data.openAi.AIInterviewQuestionsPrompter
import sectonone.droidsoft.ap.data.data.openAi.OpenAIPrompter
import sectonone.droidsoft.ap.data.data.repository.BookmarksRepository
import sectonone.droidsoft.ap.data.data.repository.HomeRepository
import sectonone.droidsoft.ap.data.data.repository.InterviewRepository
import sectonone.droidsoft.ap.data.data.repository.QuestionsRepository
import sectonone.droidsoft.ap.data.data.source.BookmarksDataSource
import sectonone.droidsoft.ap.data.data.source.InterviewHistoryDataSource
import sectonone.droidsoft.ap.data.data.source.LocalQuestionsDataSource
import sectonone.droidsoft.ap.data.data.source.QuestionsDataSource

val dataModule = module {
    // Data sources
    singleOf(::LocalQuestionsDataSource) bind QuestionsDataSource::class
    singleOf(::InterviewHistoryDataSource)
    singleOf(::BookmarksDataSource)
    // Repositories
    singleOf(::QuestionsRepository)
    singleOf(::InterviewRepository)
    singleOf(::HomeRepository)
    singleOf(::BookmarksRepository)
    // AI
    singleOf(::OpenAIPrompter)
    singleOf(::AIInterviewQuestionsPrompter)
    // File
    singleOf(::ResourcesFileReader)
}