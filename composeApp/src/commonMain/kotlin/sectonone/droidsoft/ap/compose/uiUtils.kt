package sectonone.droidsoft.ap.compose

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

fun List<Any>.prettyPrint(): String {
    return joinToString(separator = ", ") { it.toString() }
}

inline fun <reified T : Enum<T>> getRandomUniqueEnumValues(n: Int): List<T> {
    val enumValues = enumValues<T>().toList()
    val adjustedN = n.coerceAtMost(enumValues.size) // Cap N to the size of the enum
    return enumValues.shuffled(Random).take(adjustedN)
}