package sectonone.droidsoft.ap.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import sectonone.droidsoft.ap.feature.home.data.GetHomeFeed
import sectonone.droidsoft.ap.feature.list.data.GetQuestionsList

val domainModule = module {
    singleOf(::GetQuestionsList)
    singleOf(::GetHomeFeed)
}