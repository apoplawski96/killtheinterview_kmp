package sectonone.droidsoft.ap.screens.interviewDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import sectonone.droidsoft.ap.ui.components.KTITextNew
import sectonone.droidsoft.ap.ui.components.KTITopAppBar
import sectonone.droidsoft.ap.di.getScreenModel
import sectonone.droidsoft.ap.theme.ktiColors

internal class InterviewDetailsScreen(private val interviewId: Int) : Screen {

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<InterviewDetailsScreenModel>()
        val state by screenModel.state.collectAsState()

        LaunchedEffect(null) {
            screenModel.loadDetails(interviewId)
        }

        InterviewDetailsScreenContent(state)
    }
}

@Composable
private fun InterviewDetailsScreenContent(state: InterviewDetailsScreenModel.State) {
    Scaffold(
        topBar = { KTITopAppBar(isNested = true, title = "Interview Details") },
        backgroundColor = ktiColors.backgroundSurface,
    ) {
        if (state.details != null) {
            Box(Modifier.fillMaxSize()) {
                KTITextNew(state.details.toString())
            }
        }
    }
}