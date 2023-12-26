package sectonone.droidsoft.ap.data

import app.cash.sqldelight.db.SqlDriver

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class QuestionsDatabaseHelper(
    sqlDriver: SqlDriver,
    private val backgroundDispatcher: CoroutineDispatcher
) {

//    private val dbRef: KaMPKitDb = KaMPKitDb(sqlDriver)
//
//    fun selectAllItems(): Flow<List<Question>> =
//        dbRef.questionsQueries
//            .selectAll()
//            .asFlow()
//            .mapToList()
//            .flowOn(backgroundDispatcher)
//
//    suspend fun insertQuestions(questions: List<AIQuestionSchema>) {
//        dbRef.transactionWithContext(backgroundDispatcher) {
//            questions.forEach { question ->
//                dbRef.questionsQueries.insertQuestion(
//                    questionText = question.question,
//                    answer = question.answer
//                )
//            }
//        }
//    }
//
//    fun selectById(id: Long): Flow<List<Question>> =
//        dbRef.questionsQueries
//            .selectById(id)
//            .asFlow()
//            .mapToList()
//            .flowOn(backgroundDispatcher)
//
//    suspend fun deleteAll() {
//        dbRef.transactionWithContext(backgroundDispatcher) {
//            dbRef.questionsQueries.deleteAll()
//        }
//    }
}
