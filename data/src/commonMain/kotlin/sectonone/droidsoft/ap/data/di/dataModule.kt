package sectonone.droidsoft.ap.data.di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import sectonone.droidsoft.ap.data.auth.UserSessionState
import sectonone.droidsoft.ap.data.file.ResourcesFileReader
import sectonone.droidsoft.ap.data.openAi.AIInterviewQuestionsPrompter
import sectonone.droidsoft.ap.data.openAi.OpenAIPrompter
import sectonone.droidsoft.ap.data.repository.AuthRepository
import sectonone.droidsoft.ap.data.repository.BookmarksRepository
import sectonone.droidsoft.ap.data.repository.HomeRepository
import sectonone.droidsoft.ap.data.repository.InterviewRepository
import sectonone.droidsoft.ap.data.repository.QuestionsRepository
import sectonone.droidsoft.ap.data.repository.UserRepository
import sectonone.droidsoft.ap.data.source.BookmarksDataSource
import sectonone.droidsoft.ap.data.source.FirebaseAuthDataSource
import sectonone.droidsoft.ap.data.source.FirebaseFirestoreDataSource
import sectonone.droidsoft.ap.data.source.InterviewHistoryDataSource
import sectonone.droidsoft.ap.data.source.LocalQuestionsDataSource
import sectonone.droidsoft.ap.data.source.QuestionsDataSource

val dataModule = module {
    // Core
    singleOf(::UserSessionState)
    // File
    singleOf(::ResourcesFileReader)
    // Data sources
    singleOf(::LocalQuestionsDataSource) bind QuestionsDataSource::class
    singleOf(::InterviewHistoryDataSource)
    singleOf(::BookmarksDataSource)
    singleOf(::FirebaseAuthDataSource)
    singleOf(::FirebaseFirestoreDataSource)
    // Repositories
    singleOf(::QuestionsRepository)
    singleOf(::InterviewRepository)
    singleOf(::HomeRepository)
    singleOf(::BookmarksRepository)
    singleOf(::AuthRepository)
    singleOf(::UserRepository)
    // AI
    singleOf(::OpenAIPrompter)
    singleOf(::AIInterviewQuestionsPrompter)
    // SDK's
    single { Firebase.auth }
    single { Firebase.firestore }
    // Coroutines
    single { CoroutineScope(SupervisorJob()) }
}