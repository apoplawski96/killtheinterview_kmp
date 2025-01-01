package sectonone.droidsoft.ap.screens.interviewDetails

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sectonone.droidsoft.ap.data.InterviewHistoryRepository
import sectonone.droidsoft.ap.model.InterviewHistoryDetails

class InterviewDetailsScreenModel(private val interviewHistoryRepository: InterviewHistoryRepository) : ScreenModel {

    data class State(
        val details: InterviewHistoryDetails?,
        val isLoading: Boolean,
    )

    private val _state = MutableStateFlow(State(details = null, isLoading = true))
    val state = _state.asStateFlow()

    fun loadDetails(id: Int) {
        screenModelScope.launch {
            _state.value = State(
                details = interviewHistoryRepository.getInterviewSummary(id.toLong()),
                isLoading = false
            )
        }
    }
}