package sectonone.droidsoft.ap.data.data

import kotlin.random.Random

inline fun <reified T : Enum<T>> getRandomUniqueEnumValues(n: Int): List<T> {
    val enumValues = enumValues<T>().toList()
    val adjustedN = n.coerceAtMost(enumValues.size) // Cap N to the size of the enum
    return enumValues.shuffled(Random).take(adjustedN)
}
