package sectonone.droidsoft.ap.data

import kotlinx.serialization.json.Json
import sectonone.droidsoft.ap.json.ResourcesFileReader
import sectonone.droidsoft.ap.model.schema.QuestionSchema
import sectonone.droidsoft.ap.model.schema.QuestionSchemaV2

private const val FILE_NAME_DROID_FLOW = "v2_questions_android_flow.json"
private const val FILE_NAME_DROID_COROUTINES = "v2_questions_android_coroutines.json"
private const val FILE_NAME_DROID_LIFECYCLE = "v2_questions_android_lifecycle.json"
private const val FILE_NAME_DROID_SECURITY = "v2_questions_android_security.json"
private const val FILE_NAME_DROID_ARCHITECTURE = "v2_questions_android_architecture.json"
private const val FILE_NAME_DROID_CONFIGURATION = "v2_questions_android_configuration.json"
private const val FILE_NAME_DROID_VIEW_MODEL = "v2_questions_android_viewmodel.json"
private const val FILE_NAME_DROID_CORE = "v2_questions_android_core.json"
private const val FILE_NAME_DROID_COMPOSE = "v2_questions_android_compose.json"

private const val FILE_NAME_IOS = "v2_questions_ios.json"
private const val FILE_NAME_DESIGN_PATTERNS = "v2_questions_design_patterns.json"
private const val FILE_NAME_KOTLIN = "v2_questions_kotlin.json"
private const val FILE_NAME_GIT = "v2_questions_git.json"
private const val FILE_NAME_PROGRAMMING_PARADIGMS = "v2_questions_programming_paradigms.json"

private val files = listOf(
    FILE_NAME_DROID_FLOW,
    FILE_NAME_DROID_COROUTINES,
    FILE_NAME_DROID_LIFECYCLE,
    FILE_NAME_DROID_SECURITY,
    FILE_NAME_DROID_ARCHITECTURE,
    FILE_NAME_DROID_CONFIGURATION,
    FILE_NAME_DROID_VIEW_MODEL,
    FILE_NAME_DROID_CORE,
    FILE_NAME_DROID_COMPOSE,
    FILE_NAME_IOS,
    FILE_NAME_DESIGN_PATTERNS,
    FILE_NAME_KOTLIN,
    FILE_NAME_GIT,
    FILE_NAME_PROGRAMMING_PARADIGMS,
)

class QuestionsDataSource(private val resourcesFileReader: ResourcesFileReader) {

    suspend fun getAll(): List<QuestionSchema> = buildList {
        files.forEach { fileName ->
            addAll(decodeQuestionsFromFile(fileName))
        }
    }

    suspend fun getAllV2(): List<QuestionSchemaV2> = buildList {
        files.forEach { fileName ->
            addAll(decodeQuestionsFromFileV2(fileName))
        }
    }

    suspend fun getQuestionsAndroid(): List<QuestionSchema> =
            decodeQuestionsFromFile(FILE_NAME_DROID_FLOW) +
            decodeQuestionsFromFile(FILE_NAME_DROID_COROUTINES) +
            decodeQuestionsFromFile(FILE_NAME_DROID_LIFECYCLE) +
            decodeQuestionsFromFile(FILE_NAME_DROID_SECURITY) +
            decodeQuestionsFromFile(FILE_NAME_DROID_ARCHITECTURE) +
            decodeQuestionsFromFile(FILE_NAME_DROID_CONFIGURATION) +
            decodeQuestionsFromFile(FILE_NAME_DROID_VIEW_MODEL) +
            decodeQuestionsFromFile(FILE_NAME_DROID_CORE) +
            decodeQuestionsFromFile(FILE_NAME_DROID_COMPOSE)

    suspend fun getQuestionsIOS(): List<QuestionSchema> =
        decodeQuestionsFromFile(FILE_NAME_IOS)

    suspend fun getQuestionsDesignPatterns(): List<QuestionSchema> =
        decodeQuestionsFromFile(FILE_NAME_DESIGN_PATTERNS)

    suspend fun getQuestionsGit(): List<QuestionSchema> =
        decodeQuestionsFromFile(FILE_NAME_GIT)

    suspend fun getQuestionsKotlin(): List<QuestionSchema> =
        decodeQuestionsFromFile(FILE_NAME_KOTLIN)

    suspend fun getQuestionsProgrammingParadigms(): List<QuestionSchema> =
        decodeQuestionsFromFile(FILE_NAME_PROGRAMMING_PARADIGMS)

    private suspend fun decodeQuestionsFromFile(fileName: String): List<QuestionSchema> {
        val jsonFileContent = resourcesFileReader.readFile(fileName) ?: return emptyList()
        println("2137 - json file content: $jsonFileContent")
        return Json.decodeFromString(jsonFileContent)
    }

    private suspend fun decodeQuestionsFromFileV2(fileName: String): List<QuestionSchemaV2> {
        val jsonFileContent = resourcesFileReader.readFile(fileName) ?: return emptyList()
        println("2137 - json file content: $jsonFileContent")
        return Json.decodeFromString(jsonFileContent)
    }
}