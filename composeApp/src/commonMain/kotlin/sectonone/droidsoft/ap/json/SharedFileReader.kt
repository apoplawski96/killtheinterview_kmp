package sectonone.droidsoft.ap.json

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.resource

expect class SharedFileReader() {
    fun loadJsonFile(fileName: String): String?
}

class ResourcesFileReader(private val defaultDispatcher: CoroutineDispatcher) {

    suspend fun readFile(fileName: String): String? {
        return try {
            withContext(defaultDispatcher) {
                val resource = resource(fileName)
                val resourceDecoded = resource.readBytes().decodeToString()
                resourceDecoded
            }
        } catch (e: Exception) {
            null
        }
    }
}