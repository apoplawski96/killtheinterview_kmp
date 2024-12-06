package sectonone.droidsoft.ap.compose

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import sectonone.droidsoft.ap.theme.KTITheme
import sectonone.droidsoft.ap.theme.LocalThemeIsDark

@SuppressLint("UnrememberedMutableState")
@Composable
fun PreviewTheme(
    isDark: Boolean,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalThemeIsDark provides mutableStateOf(isDark)) {
        KTITheme {
            content()
        }
    }
}