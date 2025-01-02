package sectonone.droidsoft.ap.data.file

import kotlinx.coroutines.IO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.resource

class ResourcesFileReader {

    suspend fun readFile(fileName: String): String? {
        return try {
            withContext(Dispatchers.IO) {
                val resource = resource(fileName)
                val resourceDecoded = resource.readBytes().decodeToString()
                resourceDecoded
            }
        } catch (e: Exception) {
            null
        }
    }
}