package sectonone.droidsoft.ap.data.repository

import kotlinx.coroutines.flow.map
import sectonone.droidsoft.ap.data.model.Category
import sectonone.droidsoft.ap.data.model.Question
import sectonone.droidsoft.ap.data.model.allQuestionsFiles
import sectonone.droidsoft.ap.data.source.QuestionsDataSource
import sectonone.droidsoft.ap.data.toDomainModel
import sectonone.droidsoft.ap.data.toDomainModelWithBookmark

class QuestionsRepository(
    private val questionsDataSource: QuestionsDataSource,
    private val bookmarksRepository: BookmarksRepository,
) {

    suspend fun getQuestions(
        categories: List<Category> = Category.entries,
        questionsLimit: Int? = null
    ): List<Question>? {
        val questionsRaw = questionsDataSource.getQuestions(
            files = categories.map { it.fileWithQuestions }
        ) ?: return null

        return if (questionsLimit != null) {
            questionsRaw.take(questionsLimit)
        } else {
            questionsRaw
        }.let { questions ->
            questions.map { it.toDomainModel }
        }
    }

    fun getQuestionsAsFlow(
        categories: List<Category> = Category.entries,
        questionsLimit: Int? = null
    ) = bookmarksRepository.getAllBookmarksAsFlow().map { bookmarks ->
        val questionsRaw = questionsDataSource.getQuestions(
            files = categories.map { it.fileWithQuestions }
        ) ?: emptyList()

        if (questionsLimit != null) {
            questionsRaw.take(questionsLimit)
        } else {
            questionsRaw
        }.let { questions ->
            questions.map { question ->
                question.toDomainModelWithBookmark(isBookmark = bookmarks.any { question.id == it.id })
            }
        }
    }

    suspend fun getQuestionsForIds(ids: List<Int>): List<Question> {
        val allQuestions = questionsDataSource.getQuestions(allQuestionsFiles)
        val questionsForGivenId = buildList {
            ids.forEach { questionId ->
                add(allQuestions?.find { it.id == questionId })
            }
        }
        return questionsForGivenId.mapNotNull { it?.toDomainModel }
    }
}