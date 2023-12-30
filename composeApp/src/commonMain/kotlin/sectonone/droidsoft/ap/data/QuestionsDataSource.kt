package sectonone.droidsoft.ap.data

import kotlinx.serialization.json.Json
import sectonone.droidsoft.ap.json.ResourcesFileReader
import sectonone.droidsoft.ap.model.schema.QuestionSchema

private const val FILE_NAME_DROID = "questions_android.json"
private const val FILE_NAME_DROID_FLOW = "questions_android_flow.json"
private const val FILE_NAME_DROID_COROUTINES = "questions_android_coroutines.json"
private const val FILE_NAME_DROID_LIFECYCLE = "questions_android_lifecycle.json"
private const val FILE_NAME_DROID_SECURITY = "questions_android_security.json"
private const val FILE_NAME_DROID_ARCHITECTURE = "questions_android_architecture.json"
private const val FILE_NAME_DROID_CONFIGURATION = "questions_android_configuration.json"
private const val FILE_NAME_DROID_VIEW_MODEL = "questions_android_viewmodel.json"
private const val FILE_NAME_DROID_CORE = "questions_android_core.json"
private const val FILE_NAME_DROID_COMPOSE = "questions_android_compose.json"

private const val FILE_NAME_IOS = "questions_ios.json"
private const val FILE_NAME_DESIGN_PATTERNS = "questions_design_patterns.json"
private const val FILE_NAME_KOTLIN = "questions_kotlin.json"
private const val FILE_NAME_GIT = "questions_git.json"
private const val FILE_NAME_PROGRAMMING_PARADIGMS = "questions_programming_paradigms.json"

private val files = listOf(
    FILE_NAME_DROID,
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

    suspend fun getQuestionsAndroid(): List<QuestionSchema> = decodeQuestionsFromFile(FILE_NAME_DROID) +
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
        return Json.decodeFromString(jsonFileContent)
    }
}