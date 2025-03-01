package sectonone.droidsoft.ap.screens.interviewCurated.finished

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource
import sectonone.droidsoft.ap.data.model.PracticeResult

data class InterviewFinishedDisplay(
    val practiceResult: PracticeResult,
    val topIcon: DrawableResource,
    val header: String,
    val percentage: String,
    val percentageColor: Color,
    val subHeader: String,
    val body: String,
)
