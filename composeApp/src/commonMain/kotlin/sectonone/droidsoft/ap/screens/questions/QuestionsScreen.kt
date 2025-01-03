@file:OptIn(ExperimentalMaterialApi::class)

package sectonone.droidsoft.ap.screens.questions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import sectonone.droidsoft.ap.ui.components.KTICircularProgressIndicator
import sectonone.droidsoft.ap.ui.components.KTIText
import sectonone.droidsoft.ap.ui.components.KTITextNew
import sectonone.droidsoft.ap.ui.components.KTITopAppBar
import sectonone.droidsoft.ap.ui.components.VerticalSpacer
import sectonone.droidsoft.ap.ui.components.clickableNoRipple
import sectonone.droidsoft.ap.ui.components.prettyPrint
import sectonone.droidsoft.ap.di.getScreenModel
import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.Question
import sectonone.droidsoft.ap.theme.KTITheme
import sectonone.droidsoft.ap.theme.ktiColors
import sectonone.droidsoft.ap.theme.kti_accent
import sectonone.droidsoft.ap.theme.kti_green
import sectonone.droidsoft.ap.theme.kti_softwhite
import sectonone.droidsoft.ap.ui.components.KTIIcon
import sectonone.droidsoft.ap.ui.components.KTIIconButton

internal class QuestionsScreen(private val categories: List<Category>) : Screen {

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<QuestionsScreenModel>()

        val state by screenModel.state.collectAsState()
        val scoreboard by screenModel.scoreboard.collectAsState()

        val scope = rememberCoroutineScope()

        val bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden
        )

        val subCategoryTitle = categories.first().displayName

        LaunchedEffect(null) {
            screenModel.viewEvents.collect { event ->
                when (event) {
                    QuestionsScreenModel.ViewEvent.ToggleBottomSheet -> {
                        toggleBottomSheet(
                            scope = scope,
                            bottomSheetState = bottomSheetState,
                        )
                    }
                }
            }
        }

        LaunchedEffect(null) {
            screenModel.initialize(categories)
        }

        QuestionsScreenLayout(
            state = state,
            topBarTitle = subCategoryTitle,
            questionsAnsweredCount = scoreboard.answeredCount,
            questionsTotalCount = scoreboard.totalCount,
            markAsAnswered = { screenModel.markQuestionAsAnswered(it) },
            markAsUnanswered = { screenModel.markQuestionAsUnanswered(it) },
            removeBookmark = { screenModel.removeBookmark(it) },
            addBookmark = { screenModel.addBookmark(it) },
        )
    }
}

@Composable
private fun QuestionsScreenLayout(
    state: QuestionsScreenModel.ViewState,
    topBarTitle: String,
    markAsAnswered: (Question) -> Unit,
    markAsUnanswered: (Question) -> Unit,
    questionsAnsweredCount: Int,
    questionsTotalCount: Int,
    addBookmark: (Question) -> Unit,
    removeBookmark: (Question) -> Unit,
) {
    Scaffold(
        topBar = { KTITopAppBar(title = topBarTitle) },
        backgroundColor = ktiColors.backgroundSurface,
        content = {
            when (state) {
                is QuestionsScreenModel.ViewState.QuestionsLoaded -> {
                    QuestionsList(
                        questions = state.questions,
                        markAsAnswered = markAsAnswered,
                        markAsUnanswered = markAsUnanswered,
                        questionsTotalCount = questionsTotalCount,
                        questionsAnsweredCount = questionsAnsweredCount,
                        addBookmark = addBookmark,
                        removeBookmark = removeBookmark
                    )
                }

                is QuestionsScreenModel.ViewState.Loading -> {
                    KTICircularProgressIndicator()
                }

                QuestionsScreenModel.ViewState.Error -> {
                    KTIText(text = "Error!")
                }
            }
        }
    )
}

private val horizontalPadding = 8.dp

@Composable
fun QuestionsList(
    questions: List<Question>,
    markAsAnswered: (Question) -> Unit,
    markAsUnanswered: (Question) -> Unit,
    questionsAnsweredCount: Int,
    questionsTotalCount: Int,
    addBookmark: (Question) -> Unit,
    removeBookmark: (Question) -> Unit,
) {
    Column {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(
                items = questions,
                key = { _, item -> "${item.id} + ${item.hashCode()}" }
            ) { _, item ->
                QuestionCard(
                    item = item,
                    markAsAnswered = markAsAnswered,
                    markAsUnanswered = markAsUnanswered,
                    addBookmark = addBookmark,
                    removeBookmark = removeBookmark
                )
            }
        }
    }
}

@Composable
fun QuestionCard(
    item: Question,
    markAsAnswered: (Question) -> Unit,
    markAsUnanswered: (Question) -> Unit,
    addBookmark: (Question) -> Unit,
    removeBookmark: (Question) -> Unit,
) {
    var isExpanded by remember(item) { mutableStateOf(false) }
    var isAnswered by remember(item) { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth().clickable {

        },
        elevation = 4.dp,
        backgroundColor = ktiColors.backgroundSurfaceVariant,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
            // Top section
            VerticalSpacer(height = 4.dp)
            QuestionTopSection(question = item)
            VerticalSpacer(height = if (isAnswered.not()) 2.dp else 0.dp)
            // Question
            KTITextNew(
                text = item.question,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(horizontal = horizontalPadding + 2.dp),
                color = if (isAnswered.not()) ktiColors.textMain else ktiColors.textVariant2,
                lineHeight = 14.sp,
            )
            VerticalSpacer(height = 8.dp)
            // Answer
            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(KTITheme.colors.backgroundSurface)
                        .padding(8.dp)
                ) {
                    VerticalSpacer(height = 8.dp)
                    KTITextNew(
                        text = item.answer,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        modifier = Modifier.padding(horizontal = horizontalPadding + 2.dp),
                        fontStyle = FontStyle.Italic,
                        color = KTITheme.colors.textMain,
                    )
                    VerticalSpacer(height = 8.dp)
                }
            }
            VerticalSpacer(height = 8.dp)
            // Bottom section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (item.isBookmarked) {
                        KTIIconButton(onClick = { removeBookmark.invoke(item) }, icon = { KTIIcon(Icons.Outlined.Bookmark) })
                    } else {
                        KTIIconButton(onClick = { addBookmark.invoke(item) }, icon = { KTIIcon(Icons.Outlined.BookmarkAdd) })
                    }
                    if (isAnswered.not()) {
                        KTIIconButton(onClick = { markAsAnswered.invoke(item) }, icon = { KTIIcon(Icons.Outlined.CheckBox) })
                    } else {
                        KTIIconButton(onClick = { markAsUnanswered.invoke(item) }, icon = { KTIIcon(Icons.Default.CheckBox, tint = kti_green) })
                    }
                }
                // Expand answer
                Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(end = 8.dp)) {
                    if (isAnswered.not()) {
                        ToggleAnswerButton(
                            shouldDisplayAnswer = isExpanded,
                            displayAnswerOnClick = { isExpanded = !isExpanded },
                        )
                    } else {
                        IconButton(onClick = {
                            isAnswered = false
                            isExpanded = false
                            markAsUnanswered.invoke(item)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = "Reopen question",
                                tint = kti_softwhite,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
        VerticalSpacer(8.dp)
    }
}

@Composable
private fun QuestionTopSection(
    question: Question,
) {
    KTITextNew(
        text = question.categories.prettyPrint(),
        fontSize = 10.sp,
        fontWeight = FontWeight.W300,
        color = ktiColors.textMain.copy(alpha = 0.6f),
        modifier = Modifier.padding(horizontal = 10.dp),
        lineHeight = 6.sp,
    )
}

@Composable
private fun ToggleAnswerButton(
    shouldDisplayAnswer: Boolean,
    displayAnswerOnClick: () -> Unit,
) {
    Icon(
        imageVector = if (shouldDisplayAnswer) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
        contentDescription = null,
        tint = if (shouldDisplayAnswer) kti_accent else KTITheme.colors.textVariant2,
        modifier = Modifier
            .clickableNoRipple { displayAnswerOnClick() }
            .size(24.dp)
    )
}

@ExperimentalMaterialApi
private fun toggleBottomSheet(
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
) {
    scope.launch {
        when (bottomSheetState.currentValue) {
            ModalBottomSheetValue.Hidden -> bottomSheetState.show()
            ModalBottomSheetValue.Expanded,
            ModalBottomSheetValue.HalfExpanded -> bottomSheetState.hide()
        }
    }
}

