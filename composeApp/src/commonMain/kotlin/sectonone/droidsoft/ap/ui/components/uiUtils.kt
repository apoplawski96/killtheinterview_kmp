package sectonone.droidsoft.ap.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.jetbrains.compose.resources.painterResource
import sectonone.droidsoft.ap.data.model.Category
import sectonone.droidsoft.ap.theme.*
import kotlin.random.Random

fun <T> KTICardItem<T>.applyColor(itemIndex: Int): KTICardItem<T> {
    val colorIndex = itemIndex % cardColors.size
    return this.copy(cardColor = cardColors[colorIndex])
}

private val cardColors = listOf(
    kti_dark_blue,
    kti_yellow,
    kti_green_variant,
    kti_purple,
    kti_bright_red,
    kti_mauve,
    kti_green,
    kti_orange
)

@Composable
fun rememberRandomCardColor() = remember {
    cardColors.random()
}

fun List<Any>.prettyPrint(): String {
    return joinToString(separator = ", ") { it.toString() }
}

@Composable
fun Category.getCoverResource() = painterResource("${this.key}_cover.png")