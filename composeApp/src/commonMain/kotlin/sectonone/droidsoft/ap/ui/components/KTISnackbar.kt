package sectonone.droidsoft.ap.ui.components

import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarData
import androidx.compose.runtime.Composable
import sectonone.droidsoft.ap.theme.KTIColors
import sectonone.droidsoft.ap.theme.KTITheme

@Composable
fun KTISnackbar(snackbarData: SnackbarData) {
    Snackbar(snackbarData, backgroundColor = KTITheme.colors.background, contentColor = KTITheme.colors.textMain)
}