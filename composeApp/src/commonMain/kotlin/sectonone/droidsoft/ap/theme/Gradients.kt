package sectonone.droidsoft.ap.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val colorStart = Color(red = 40, green = 48, blue = 61)
val colorEnd = Color(red = 0, green = 0, blue = 0)
val nightskyGradient = Brush.linearGradient(
    colors = listOf(colorStart, colorEnd),
    start = Offset(-100f, -250f), // Start at the top-left
    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY), // End at the bottom-right
) // export gradient

private val colorStartGrey = Color(red = 149, green = 149, blue = 158)
private val colorEndGrey = Color(red = 67, green = 67, blue = 73)
val greyGradient = Brush.linearGradient(
    colors = listOf(colorStartGrey, colorEndGrey),
    start = Offset(-100f, -250f), // Start at the top-left
    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY), // End at the bottom-right
) // export gradient