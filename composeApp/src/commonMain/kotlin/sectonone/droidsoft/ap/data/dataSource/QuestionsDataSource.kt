package sectonone.droidsoft.ap.data.dataSource

import sectonone.droidsoft.ap.model.schema.QuestionSchemaV2

interface QuestionsDataSource {
    suspend fun getQuestions(files: List<String>): List<QuestionSchemaV2>
}