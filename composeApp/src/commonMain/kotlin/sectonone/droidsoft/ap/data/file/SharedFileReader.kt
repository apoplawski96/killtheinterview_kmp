package sectonone.droidsoft.ap.data.file

import killtheinterview_kmp.composeapp.resources.Res
import kotlinx.coroutines.IO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ResourcesFileReader {

    suspend fun readFile(fileName: String): String? {
        return try {
            withContext(Dispatchers.IO) {
                val resourceBytes = Res.readBytes(fileName)
                val resourceDecoded = resourceBytes.decodeToString()
                resourceDecoded
            }
        } catch (e: Exception) {
            null
        }
    }
}