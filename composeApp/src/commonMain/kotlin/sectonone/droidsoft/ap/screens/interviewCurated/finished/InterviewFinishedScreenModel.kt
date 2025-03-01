package sectonone.droidsoft.ap.screens.interviewCurated.finished

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import sectonone.droidsoft.ap.data.model.PracticeResult
import sectonone.droidsoft.ap.data.resources.Res
import sectonone.droidsoft.ap.data.resources.exam
import sectonone.droidsoft.ap.data.resources.medal_blue
import sectonone.droidsoft.ap.data.resources.medal_bronze
import sectonone.droidsoft.ap.data.resources.medal_gold
import sectonone.droidsoft.ap.data.resources.medal_silver
import sectonone.droidsoft.ap.data.resources.trophy
import sectonone.droidsoft.ap.screens.interviewCurated.model.InterviewPracticeSummary
import sectonone.droidsoft.ap.screens.interviewCurated.model.percentageDisplay
import sectonone.droidsoft.ap.theme.kti_green
import sectonone.droidsoft.ap.theme.kti_red_wrong
import sectonone.droidsoft.ap.theme.kti_yellow

class InterviewFinishedScreenModel : ScreenModel {

    private val _state = MutableStateFlow<InterviewFinishedDisplay?>(null)
    val state = _state.asStateFlow()

    fun fetchScreenData(interviewSummary: InterviewPracticeSummary) {
        _state.value = when(interviewSummary.practiceResult) {
            PracticeResult.Failed -> InterviewFinishedDisplay(
                practiceResult = interviewSummary.practiceResult,
                header = "Failed",
                body = "There is no one who loves pain itself, who seeks after it and wants to have it, simply because it is pain...",
                percentage = interviewSummary.score.percentageDisplay,
                percentageColor = kti_red_wrong,
                subHeader = "In this interview there were questions about :${
                    interviewSummary.categories.joinToString(
                        ", "
                    )
                }",
                topIcon = Res.drawable.exam,
            )
            PracticeResult.Average -> InterviewFinishedDisplay(
                practiceResult = interviewSummary.practiceResult,
                header = "Average",
                body = "There is no one who loves pain itself, who seeks after it and wants to have it, simply because it is pain...",
                percentage = interviewSummary.score.percentageDisplay,
                percentageColor = kti_yellow,
                subHeader = "In this interview there were questions about :${
                    interviewSummary.categories.joinToString(
                        ", "
                    )
                }",
                topIcon = Res.drawable.medal_blue,
            )
            PracticeResult.Bronze -> InterviewFinishedDisplay(
                practiceResult = interviewSummary.practiceResult,
                header = "Bronze",
                body = "There is no one who loves pain itself, who seeks after it and wants to have it, simply because it is pain...",
                percentage = interviewSummary.score.percentageDisplay,
                percentageColor = kti_yellow,
                subHeader = "In this interview there were questions about :${
                    interviewSummary.categories.joinToString(
                        ", "
                    )
                }",
                topIcon = Res.drawable.medal_bronze,
            )
            PracticeResult.Silver -> InterviewFinishedDisplay(
                practiceResult = interviewSummary.practiceResult,
                header = "Silver",
                body = "There is no one who loves pain itself, who seeks after it and wants to have it, simply because it is pain...",
                percentage = interviewSummary.score.percentageDisplay,
                percentageColor = kti_green,
                subHeader = "In this interview there were questions about :${
                    interviewSummary.categories.joinToString(
                        ", "
                    )
                }",
                topIcon = Res.drawable.medal_silver,
            )
            PracticeResult.Golden -> InterviewFinishedDisplay(
                practiceResult = interviewSummary.practiceResult,
                header = "Golden",
                body = "There is no one who loves pain itself, who seeks after it and wants to have it, simply because it is pain...",
                percentage = interviewSummary.score.percentageDisplay,
                percentageColor = kti_green,
                subHeader = "In this interview there were questions about :${
                    interviewSummary.categories.joinToString(
                        ", "
                    )
                }",
                topIcon = Res.drawable.medal_gold,
            )
            PracticeResult.Goat -> InterviewFinishedDisplay(
                practiceResult = interviewSummary.practiceResult,
                header = "Goat",
                body = "There is no one who loves pain itself, who seeks after it and wants to have it, simply because it is pain...",
                percentage = interviewSummary.score.percentageDisplay,
                percentageColor = kti_green,
                subHeader = "In this interview there were questions about :${
                    interviewSummary.categories.joinToString(
                        ", "
                    )
                }",
                topIcon = Res.drawable.trophy,
            )
        }
    }
}