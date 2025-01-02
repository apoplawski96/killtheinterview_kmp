package sectonone.droidsoft.ap.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import sectonone.droidsoft.ap.screens.home.HomeScreenModel
import sectonone.droidsoft.ap.screens.interviewAi.AIInterviewScreenModel
import sectonone.droidsoft.ap.screens.interviewCurated.InterviewChatScreenModel
import sectonone.droidsoft.ap.screens.interviewDetails.InterviewDetailsScreenModel
import sectonone.droidsoft.ap.screens.interviewSetup.InterviewSetupScreenModel
import sectonone.droidsoft.ap.screens.interviewsHistory.InterviewHistoryScreenModel
import sectonone.droidsoft.ap.screens.questionsList.QuestionsListScreenModel

val presentationModule = module {
    factoryOf(::AIInterviewScreenModel)
    factoryOf(::HomeScreenModel)
    factoryOf(::QuestionsListScreenModel)
    factoryOf(::InterviewSetupScreenModel)
    factoryOf(::InterviewChatScreenModel)
    factoryOf(::InterviewHistoryScreenModel)
    factoryOf(::InterviewDetailsScreenModel)
}