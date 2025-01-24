package sectonone.droidsoft.ap.data.data.source

import sectonone.droidsoft.ap.data.model.schema.QuestionScheme

interface QuestionsDataSource {
    suspend fun getQuestions(files: List<String>): List<QuestionScheme>?
}