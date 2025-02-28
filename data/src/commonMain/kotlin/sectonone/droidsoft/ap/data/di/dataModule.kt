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
import sectonone.droidsoft.ap.data.repositories.AuthRepository
import sectonone.droidsoft.ap.data.repositories.BookmarksRepository
import sectonone.droidsoft.ap.data.repositories.CategoriesRepository
import sectonone.droidsoft.ap.data.repositories.HomeRepository
import sectonone.droidsoft.ap.data.repositories.InterviewRepository
import sectonone.droidsoft.ap.data.repositories.QuestionsRepository
import sectonone.droidsoft.ap.data.repositories.UserRepository
import sectonone.droidsoft.ap.data.source.CategoriesDataSource
import sectonone.droidsoft.ap.data.source.impl.BookmarksDataSource
import sectonone.droidsoft.ap.data.source.impl.FirebaseAuthDataSource
import sectonone.droidsoft.ap.data.source.impl.FirebaseFirestoreDataSource
import sectonone.droidsoft.ap.data.source.impl.InterviewHistoryDataSource
import sectonone.droidsoft.ap.data.source.impl.LocalQuestionsDataSource
import sectonone.droidsoft.ap.data.source.QuestionsDataSource
import sectonone.droidsoft.ap.data.source.impl.LocalCategoriesDataSource

val dataModule = module {
    // Core
    singleOf(::UserSessionState)
    // File
    singleOf(::ResourcesFileReader)
    // Data sources
    singleOf(::LocalQuestionsDataSource) bind QuestionsDataSource::class
    singleOf(::LocalCategoriesDataSource) bind CategoriesDataSource::class
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
    singleOf(::CategoriesRepository)
    // AI
    singleOf(::OpenAIPrompter)
    singleOf(::AIInterviewQuestionsPrompter)
    // SDK's
    single { Firebase.auth }
    single { Firebase.firestore }
    // Coroutines
    single { CoroutineScope(SupervisorJob()) }
}