package sectonone.droidsoft.ap.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import sectonone.droidsoft.ap.theme.KTITheme
import sectonone.droidsoft.ap.theme.ktiColors
import sectonone.droidsoft.ap.theme.kti_green
import sectonone.droidsoft.ap.theme.kti_red
import sectonone.droidsoft.ap.theme.kti_yellow

@Composable
fun KTICircularProgressIndicator(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier = modifier, color = KTITheme.colors.secondary)
}

@Composable
fun KTILinearProgressIndicator(percent: Float, modifier: Modifier = Modifier) {
    val color = when {
        percent < 0.33f -> kti_red // Low percentage (0% to 33%)
        percent < 0.66f -> kti_yellow // Medium percentage (33% to 66%)
        else -> kti_green // High percentage (66% to 100%)
    }

    LinearProgressIndicator(
        progress = percent,
        color = color,
        backgroundColor = ktiColors.backgroundSurface,
        modifier = modifier then Modifier
            .height(2.dp) // Adjust the height as needed
            .clip(RoundedCornerShape(4.dp)) // Add rounded corners for aesthetics
    )
}
