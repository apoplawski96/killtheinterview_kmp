package sectonone.droidsoft.ap.compose

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import sectonone.droidsoft.ap.theme.KTITheme

@Composable
fun KTICircularProgressIndicator(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier = modifier, color = KTITheme.colors.secondary)
}