package sectonone.droidsoft.ap.json

import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.resource

expect class SharedFileReader() {
    fun loadJsonFile(fileName: String): String?
}

class OtherFileReader {

    suspend fun loadJsonFile(fileName: String): String? = try {
        println("2137 - fileName: $fileName")
        val resource = resource(fileName)
        val resourceDecoded = resource.readBytes().decodeToString().also {
            Napier.v(tag = "2137") { "content 2137 $it" }
            println("2137 - content: $it")
        }
        resourceDecoded
    } catch (e: Exception) {
        println("2137 - error, $e")
        null
    }
}