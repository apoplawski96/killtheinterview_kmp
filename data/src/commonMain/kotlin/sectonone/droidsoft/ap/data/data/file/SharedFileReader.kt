package sectonone.droidsoft.ap.data.data.file

import kotlinx.coroutines.IO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import sectonone.droidsoft.ap.data.resources.Res

class ResourcesFileReader {

    @OptIn(ExperimentalResourceApi::class)
    suspend fun readFile(fileName: String): String? {
        return try {
            withContext(Dispatchers.IO) {
                val resource = Res.readBytes("files/$fileName")
                val resourceDecoded = resource.decodeToString().also {
                    println("2137 - resource decoded: $it")
                }
                resourceDecoded
            }
        } catch (e: Exception) {
            println("2137 - file reader exception: $e, fileName: $fileName")
            null
        }
    }
}