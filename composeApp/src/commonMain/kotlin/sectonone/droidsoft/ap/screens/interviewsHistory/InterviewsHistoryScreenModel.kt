package sectonone.droidsoft.ap.screens.interviewsHistory

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import sectonone.droidsoft.ap.data.InterviewHistoryRepository
import sectonone.droidsoft.ap.model.InterviewHistorySummaryUI

internal class InterviewsHistoryScreenModel(
    interviewHistoryRepository: InterviewHistoryRepository,
) : ScreenModel {

    data class State(
        val interviews: List<InterviewHistorySummaryUI>,
        val isLoading: Boolean,
    )

    val interviewsHistory = interviewHistoryRepository.getAllInterviewsSummaryAsFlow()
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