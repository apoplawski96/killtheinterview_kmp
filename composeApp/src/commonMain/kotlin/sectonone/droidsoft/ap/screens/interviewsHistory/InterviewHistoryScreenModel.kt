package sectonone.droidsoft.ap.screens.interviewsHistory

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import sectonone.droidsoft.ap.data.model.InterviewHistorySummary
import sectonone.droidsoft.ap.data.repositories.InterviewRepository

internal class InterviewHistoryScreenModel(
    interviewRepository: InterviewRepository,
) : ScreenModel {

    data class State(
        val interviews: List<InterviewHistorySummary>,
        val isLoading: Boolean,
    )

    val interviewsHistory = interviewRepository.getAllInterviewsSummaryAsFlow()
        .map {
            State(interviews = it, isLoading = false)
        }
        .stateIn(
            scope = screenModelScope,
            started = SharingStarted.Lazily,
            initialValue = State(
                interviews = emptyList(),
                isLoading = true
            )
        )
}