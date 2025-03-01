package sectonone.droidsoft.ap.screens.bookmarks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import sectonone.droidsoft.ap.data.di.getScreenModel
import sectonone.droidsoft.ap.data.model.Question
import sectonone.droidsoft.ap.screens.questions.QuestionCard
import sectonone.droidsoft.ap.theme.ktiColors
import sectonone.droidsoft.ap.ui.components.KTITopAppBar

internal object BookmarksScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<BookmarksScreenModel>()
        val items by screenModel.state.collectAsState()

        BookmarksScreenLayout(
            items = items,
            markAsAnswered = { },
            markAsUnanswered = { },
            removeBookmark = { screenModel.removeBookmark(it) },
            addBookmark = { screenModel.addBookmark(it) },
            questionsTotalCount = 100
        )
    }
}

@Composable
private fun BookmarksScreenLayout(
    items: List<Question>,
    markAsAnswered: (Question) -> Unit,
    markAsUnanswered: (Question) -> Unit,
    questionsTotalCount: Int,
    addBookmark: (Question) -> Unit,
    removeBookmark: (Question) -> Unit,
) {
    Scaffold(
        topBar = { KTITopAppBar(isNested = true, title = "Bookmarks") },
        backgroundColor = ktiColors.backgroundSurface,
    ) {
        Column {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(
                    items = items,
                    key = { _, item -> "${item.id} + ${item.hashCode()}" }
                ) { _, item ->
                    QuestionCard(
                        item = item,
                        markAsAnswered = markAsAnswered,
                        markAsUnanswered = markAsUnanswered,
                        addBookmark = addBookmark,
                        removeBookmark = removeBookmark,
                        locked = false,
                        showPaywall = {}
                    )
                }
            }
        }
    }
}