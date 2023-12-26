package sectonone.droidsoft.ap.di

import sectonone.droidsoft.ap.feature.list.data.GetQuestionsList
import sectonone.droidsoft.ap.feature.list.data.QuestionsMapper
import sectonone.droidsoft.ap.feature.home.data.GetHomeScreenFeedItems
import sectonone.droidsoft.ap.feature.home.data.GetRandomSubCategories
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val domainModule = module {
    singleOf(::GetQuestionsList)
    singleOf(::QuestionsMapper)
    singleOf(::GetHomeScreenFeedItems)
    singleOf(::GetRandomSubCategories)
}