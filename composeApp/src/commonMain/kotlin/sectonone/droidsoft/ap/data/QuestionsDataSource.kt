package sectonone.droidsoft.ap.data

import kotlinx.serialization.json.Json
import sectonone.droidsoft.ap.json.ResourcesFileReader
import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.schema.QuestionSchemaV2

class QuestionsDataSource(private val resourcesFileReader: ResourcesFileReader) {

    suspend fun getQuestions(files: List<String>): List<QuestionSchemaV2> = buildList {
        files.forEach { file ->
            addAll(decodeQuestionsFromFileV2(file))
        }
    }

    private suspend fun decodeQuestionsFromFileV2(fileName: String): List<QuestionSchemaV2> {
        val jsonFileContent = resourcesFileReader.readFile(fileName) ?: return emptyList()
        return Json.decodeFromString(jsonFileContent)
    }
}