package sectonone.droidsoft.ap.screens.interviewsHistory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import sectonone.droidsoft.ap.data.di.getScreenModel
import sectonone.droidsoft.ap.data.model.InterviewHistorySummary
import sectonone.droidsoft.ap.screens.home.components.InterviewHistorySummaryVariant
import sectonone.droidsoft.ap.screens.interviewDetails.InterviewDetailsScreen
import sectonone.droidsoft.ap.theme.ktiColors
import sectonone.droidsoft.ap.ui.components.InterviewSummaryCard
import sectonone.droidsoft.ap.ui.components.KTICircularProgressIndicator
import sectonone.droidsoft.ap.ui.components.KTITextNew
import sectonone.droidsoft.ap.ui.components.KTITopAppBar

internal object InterviewHistoryScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<InterviewHistoryScreenModel>()
        val history by screenModel.interviewsHistory.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        InterviewHistoryScreenContent(
            interviewsHistory = history.interviews,
            isLoading = history.isLoading,
            onInterviewClick = {
                navigator.push(InterviewDetailsScreen(interviewId = it.id))
            },
        )
    }
}

@Composable
private fun InterviewHistoryScreenContent(
    interviewsHistory: List<InterviewHistorySummary>,
    onInterviewClick: (InterviewHistorySummary) -> Unit,
    isLoading: Boolean,
) {
    Scaffold(
        topBar = { KTITopAppBar(isNested = true, title = "Interviews History") },
        backgroundColor = ktiColors.backgroundSurface,
    ) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                KTICircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            if (interviewsHistory.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    KTITextNew(modifier = Modifier.align(Alignment.Center), text = "No interview history")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(interviewsHistory) { summaryItem ->
                        InterviewSummaryCard(
                            item = summaryItem,
                            onClick = onInterviewClick,
                            variant = InterviewHistorySummaryVariant.Column
                        )
                    }
                }
            }
        }
    }
}