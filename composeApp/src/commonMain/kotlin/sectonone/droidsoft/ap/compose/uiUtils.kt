package sectonone.droidsoft.ap.compose

import sectonone.droidsoft.ap.theme.*

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