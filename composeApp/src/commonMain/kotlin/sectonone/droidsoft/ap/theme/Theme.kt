package sectonone.droidsoft.ap.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)

private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(2.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

private val AppTypography = Typography(
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    )
)

internal val LocalThemeIsDark = compositionLocalOf { mutableStateOf(true) }

@Composable
internal fun AppTheme(
    content: @Composable() () -> Unit
) {
    val systemIsDark = isSystemInDarkTheme()
    val isDarkState = remember { mutableStateOf(systemIsDark) }
    CompositionLocalProvider(
        LocalThemeIsDark provides isDarkState
    ) {
        val isDark by isDarkState
        SystemAppearance(isDark)
        MaterialTheme(
            colorScheme = if (isDark) DarkColorScheme else LightColorScheme,
            typography = AppTypography,
            shapes = AppShapes,
            content = {
                Surface(content = content)
            }
        )
    }
}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)

// KTI

val LightColorPalette = KTIColors(
    primary = white,
    secondary = kti_red,
    onPrimary = black,
    onSecondary = white,
    textMain = black,
    textVariant = kti_dark_grey,
    textVariant2 = kti_grey_variant,
    textVariant3 = kti_grey_variant,
    textVariant4 = kti_offblack,
    textVariant5 = kti_grey_variant,
    textVariant6 = kti_dark_grey,
    backgroundRoot = kti_light_grey,
    backgroundSurface = white,
    backgroundSurfaceVariant = white,
    backgroundSurfaceVariant2 = kti_light_grey,
    backgroundSurfaceVariant3 = kti_mid_grey,
    backgroundDim = kti_black_30alpha,
    divider = kti_mid_grey,
    dividerVariant = kti_light_grey,
    dividerVariant2 = kti_dark_grey,
    accentGreen = kti_green_variant,
    ripple = kti_grey_variant,
    unselected = kti_offblack,
    border = kti_offblack,
    borderVariant = black,
    error = kti_error_red,
    brightRed = kti_bright_red,
    yellow = kti_yellow,
    blue = kti_blue,
    lightBlue = kti_light_blue,
    orange = kti_orange,
    purple = kti_purple,
    mauve = kti_mauve,
    countDownTimerButton = kti_dark_blue,
    isDark = false,
    appBars = kti_pinterest_white,
    background = kti_pinterest_light_grey,
    chatAccent = kti_accent,
    chatPrimary = kti_pinterest_white,
)

val DarkColorPalette = KTIColors(
    primary = black,
    secondary = kti_red,
    onPrimary = white,
    onSecondary = white,
    textMain = white,
    textVariant = kti_light_grey,
    textVariant2 = kti_light_grey,
    textVariant3 = kti_grey_variant,
    textVariant4 = kti_offblack,
    textVariant5 = kti_mid_grey,
    textVariant6 = kti_mid_grey,
    backgroundRoot = kti_offblack,
    backgroundSurface = kti_dark_grey,
    backgroundSurfaceVariant = kti_offblack,
    backgroundSurfaceVariant2 = kti_dark_grey,
    backgroundSurfaceVariant3 = kti_grey_variant,
    backgroundDim = kti_black_60alpha,
    divider = kti_dark_grey,
    dividerVariant = black,
    dividerVariant2 = kti_grey_variant,
    accentGreen = kti_green_variant,
    ripple = white,
    unselected = white,
    border = kti_light_grey,
    borderVariant = kti_light_grey,
    error = kti_error_red,
    brightRed = kti_bright_red,
    yellow = kti_yellow,
    blue = kti_blue,
    lightBlue = kti_light_blue,
    orange = kti_orange,
    purple = kti_purple,
    mauve = kti_mauve,
    countDownTimerButton = white,
    isDark = true,
    appBars = kti_pinterest_dark,
    background = kti_pinterest_dark,
    chatAccent = kti_accent,
    chatPrimary = kti_pinterest_grey,
)

@Composable
fun KTITheme(content: @Composable () -> Unit = {}) {
    val isDark by LocalThemeIsDark.current
    val colors = if (isDark) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    ProvideKTIColors(colors = colors) {
        androidx.compose.material.MaterialTheme(
            colors = debugColors(),
            shapes = Shapes,
        ) {
            ProvideKTIRipple(darkTheme = isDark) {
                content()
            }
        }
    }
}

internal object KTITheme {

    val colors: KTIColors
        @Composable
        get() = LocalKTIColors.current

//    val typography: KTIColors
//        @Composable
//        get() = LocalKTIColors.current
//
//    val dimens: KTIColors
//        @Composable
//        get() = LocalKTIColors.current
}


val ktiColors: KTIColors
    @Composable
    get() = KTITheme.colors
