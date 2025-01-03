package sectonone.droidsoft.ap.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.Question
import sectonone.droidsoft.ap.screens.questions.QuestionCard
import sectonone.droidsoft.ap.theme.KTITheme

@Preview
@Composable
private fun PreviewQuestionCardNotBookmarked() {
    KTITheme {
        QuestionCard(
            item = Question(
                id = 1,
                question = "What is a coroutine and how does it?",
                answer = "costam",
                categories = listOf(Category.Coroutines),
                isBookmarked = false
            ),
            markAsAnswered = {},
            markAsUnanswered = {},
            addBookmark = {},
            removeBookmark = {},
        )
    }
}

@Preview
@Composable
private fun PreviewQuestionCardBookmarked() {
    KTITheme {
        QuestionCard(
            item = Question(
                id = 1,
                question = "What is a coroutine and how does it?",
                answer = "costam",
                categories = listOf(Category.Coroutines),
                isBookmarked = true
            ),
            markAsAnswered = {},
            markAsUnanswered = {},
            addBookmark = {},
            removeBookmark = {},
        )
    }
}