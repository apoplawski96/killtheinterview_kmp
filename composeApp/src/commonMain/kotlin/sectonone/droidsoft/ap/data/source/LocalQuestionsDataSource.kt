package sectonone.droidsoft.ap.data.source

import kotlinx.serialization.json.Json
import sectonone.droidsoft.ap.data.file.ResourcesFileReader
import sectonone.droidsoft.ap.model.schema.QuestionScheme

class LocalQuestionsDataSource(private val resourcesFileReader: ResourcesFileReader) : QuestionsDataSource {

    override suspend fun getQuestions(files: List<String>): List<QuestionScheme>? = try {
        buildList {
            files.forEach { file ->
                addAll(decodeQuestionsFromFileV2(file))
            }
        }
    } catch (e: Exception) {
        null
    }

    private suspend fun decodeQuestionsFromFileV2(fileName: String): List<QuestionScheme> {
        val jsonFileContent = resourcesFileReader.readFile(fileName) ?: return emptyList()
        return Json.decodeFromString(jsonFileContent)
    }
}


//class LocalQuestionsDataSource(private val resourcesFileReader: ResourcesFileReader) : QuestionsDataSource {
//
//    /* MutableMap<fileName: String, questions: List<QuestionSchemaV2>> */
//    private var _questionsCache: MutableMap<String, List<QuestionSchemaV2>>? = null
//
//    override suspend fun getQuestions(files: List<String>): List<QuestionSchemaV2> {
//        if (_questionsCache == null) loadAllQuestionsFromDiscToCache()
//
//        return buildList {
//            files.forEach { fileName ->
//                addAll(_questionsCache?.get(fileName) ?: emptyList())
//            }
//        }
//    }
//
//    private suspend fun loadAllQuestionsFromDiscToCache() {
//        Category.entries.map { it.fileWithQuestions }.forEach { file ->
//            decodeQuestionsFromFile(file).let { questions ->
//                _questionsCache?.set(file, questions)
//            }
//        }
//        println("2137 - questions loaded: $_questionsCache")
//    }
//
//    private suspend fun decodeQuestionsFromFile(fileName: String): List<QuestionSchemaV2> {
//        val jsonFileContent = resourcesFileReader.readFile(fileName) ?: return emptyList()
//        return Json.decodeFromString(jsonFileContent)
//    }
//}