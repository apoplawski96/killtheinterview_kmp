package sectonone.droidsoft.ap.di

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import sectonone.droidsoft.ap.screens.categories.CategoriesScreenModel
import sectonone.droidsoft.ap.screens.home.HomeScreenModel
import sectonone.droidsoft.ap.screens.interviewAi.AIInterviewScreenModel
import sectonone.droidsoft.ap.screens.interviewCurated.InterviewChatScreenModel
import sectonone.droidsoft.ap.screens.interviewSetup.InterviewSetupScreenModel
import sectonone.droidsoft.ap.screens.list.QuestionsListScreenModel
import sectonone.droidsoft.ap.screens.subcategories.SubCategoriesScreenModel
import sectonone.droidsoft.ap.screens.welcome.WelcomeScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin

@Composable
inline fun <reified T : ScreenModel> Screen.getScreenModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
): T {
    val koin = getKoin()
    return rememberScreenModel(tag = qualifier?.value) { koin.get(qualifier, parameters) }
}

val screenModelModule = module {
    factoryOf(::WelcomeScreenModel)
    factoryOf(::AIInterviewScreenModel)
    factoryOf(::HomeScreenModel)
    factoryOf(::SubCategoriesScreenModel)
    factoryOf(::QuestionsListScreenModel)
    factoryOf(::CategoriesScreenModel)
    factoryOf(::InterviewSetupScreenModel)
    factoryOf(::InterviewChatScreenModel)
}