package sectonone.droidsoft.ap.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import sectonone.droidsoft.ap.theme.ktiColors
import sectonone.droidsoft.ap.theme.kti_grey
import sectonone.droidsoft.ap.theme.kti_softwhite

@Composable
fun KTIColumnWithGradient(
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundGradientBrush),
    ) {
        content.invoke()
    }
}

@Composable
fun KTIBackgroundSurface(
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = contentAlignment,
        modifier = Modifier
            .fillMaxSize()
            .background(ktiColors.backgroundSurface),
    ) {
        content.invoke()
    }
}

val backgroundGradientBrush = Brush.verticalGradient(
    0.0f to kti_softwhite,
    1.0f to kti_grey
)