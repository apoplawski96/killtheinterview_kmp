package sectonone.droidsoft.ap.theme

import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

@Composable
internal fun ProvideKTIRipple(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalRippleTheme provides KTIRippleTheme(darkTheme),
        content = content
    )
}

private class KTIRippleTheme(val darkTheme: Boolean) : RippleTheme {

    @Composable
    override fun defaultColor(): Color = KTITheme.colors.ripple

    @Composable
    override fun rippleAlpha(): RippleAlpha =
        RippleTheme.defaultRippleAlpha(
            Color.Black,
            lightTheme = !darkTheme
        )
}
