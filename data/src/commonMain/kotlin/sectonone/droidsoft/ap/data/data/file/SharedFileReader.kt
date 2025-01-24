package sectonone.droidsoft.ap.data.data.file

import killtheinterviewkmp.data.generated.resources.Res
import kotlinx.coroutines.IO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi

class ResourcesFileReader {

    @OptIn(ExperimentalResourceApi::class)
    suspend fun readFile(fileName: String): String? {
        return try {
            withContext(Dispatchers.IO) {
                val resource = Res.readBytes(fileName)
                val resourceDecoded = resource.decodeToString()
                resourceDecoded
            }
        } catch (e: Exception) {
            null
        }
    }
}