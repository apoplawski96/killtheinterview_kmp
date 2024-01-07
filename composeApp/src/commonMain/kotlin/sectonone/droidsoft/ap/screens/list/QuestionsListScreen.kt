@file:OptIn(ExperimentalMaterialApi::class)

package sectonone.droidsoft.ap.screens.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import sectonone.droidsoft.ap.compose.KTICircularProgressIndicator
import sectonone.droidsoft.ap.compose.KTIColumnWithGradient
import sectonone.droidsoft.ap.compose.KTIText
import sectonone.droidsoft.ap.compose.KTITextNew
import sectonone.droidsoft.ap.compose.KTITopAppBar
import sectonone.droidsoft.ap.compose.KTIVerticalSpacer
import sectonone.droidsoft.ap.compose.bottomsheet.base.KTIModalBottomSheetLayout
import sectonone.droidsoft.ap.compose.clickableNoRipple
import sectonone.droidsoft.ap.di.getScreenModel
import sectonone.droidsoft.ap.model.Question
import sectonone.droidsoft.ap.model.SubCategory
import sectonone.droidsoft.ap.model.TopCategory
import sectonone.droidsoft.ap.screens.list.components.ListScreenBottomSheetContent
import sectonone.droidsoft.ap.screens.list.components.ListScreenScoreBar
import sectonone.droidsoft.ap.theme.ktiColors
import sectonone.droidsoft.ap.theme.kti_accent
import sectonone.droidsoft.ap.theme.kti_divider
import sectonone.droidsoft.ap.theme.kti_green
import sectonone.droidsoft.ap.theme.kti_softblack
import sectonone.droidsoft.ap.theme.kti_softwhite

internal class QuestionsListScreen(
    private val topCategory: TopCategory?,
    private val subCategory: SubCategory?,
) : Screen {

    @Composable
    override fun Content() {
        val screenModel: QuestionsListScreenModel = getScreenModel()

        ListScreen(
            viewModel = screenModel,
            topCategory = topCategory,
            subCategory = subCategory
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListScreen(
    viewModel: QuestionsListScreenModel,
    topCategory: TopCategory?,
    subCategory: SubCategory?,
) {
    if (topCategory == null) return

    val scope = rememberCoroutineScope()

    val viewState = viewModel.viewState.collectAsState().value
    val selectedDifficulties = viewModel.selectedDifficulties.collectAsState().value
    val scoreboard = viewModel.scoreboard.collectAsState().value

    val bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    var sortDropdownMenuDisplayed by remember { mutableStateOf(false) }

    val subCategoryTitle = subCategory?.displayName ?: topCategory.displayName

    LaunchedEffect(null) {
        viewModel.viewEvents.collect { event ->
            when (event) {
                QuestionsListScreenModel.ViewEvent.ToggleBottomSheet -> {
                    toggleBottomSheet(
                        scope = scope,
                        bottomSheetState = bottomSheetState,
                    )
                }
            }
        }
    }

    LaunchedEffect(null) {
        viewModel.initialize(
            subCategory = subCategory,
            topCategory = topCategory,
        )
    }

    ListScreenContentNew(
        viewState = viewState,
        bottomSheetContent = {
            ListScreenBottomSheetContent(
                selectedDifficulties = selectedDifficulties,
                onDifficultyToggled = { toggledDifficulty ->
                    viewModel.toggleDifficulty(toggledDifficulty)
                }
            )
        },
        bottomSheetState = bottomSheetState,
        topBarTitle = subCategoryTitle,
        onToggleBottomSheetClick = { viewModel.toggleBottomSheet() },
        toggleDropdownMenu = { sortDropdownMenuDisplayed = !sortDropdownMenuDisplayed },
        sortDropdownMenuDisplayed = sortDropdownMenuDisplayed,
        onSortModeClick = { sortMode -> viewModel.sortModeSelected(sortMode) },
        questionsAnsweredCount = scoreboard.answeredCount,
        questionsTotalCount = scoreboard.totalCount,
        markAsAnswered = { question -> viewModel.markQuestionAsAnswered(question) },
        markAsUnanswered = { question -> viewModel.markQuestionAsUnanswered(question) },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ListScreenContentNew(
    viewState: QuestionsListScreenModel.ViewState,
    bottomSheetState: ModalBottomSheetState,
    bottomSheetContent: @Composable () -> Unit,
    onToggleBottomSheetClick: () -> Unit,
    topBarTitle: String,
    sortDropdownMenuDisplayed: Boolean,
    toggleDropdownMenu: () -> Unit,
    onSortModeClick: (QuestionsListScreenModel.SortMode) -> Unit,
    markAsAnswered: (Question) -> Unit,
    markAsUnanswered: (Question) -> Unit,
    questionsAnsweredCount: Int,
    questionsTotalCount: Int,
) {
    Scaffold(
        topBar = { KTITopAppBar(title = topBarTitle) },
        backgroundColor = ktiColors.backgroundSurface,
        content = {
            when (viewState) {
                is QuestionsListScreenModel.ViewState.QuestionsLoaded -> {
                    QuestionList(
                        questions = viewState.questions,
                        markAsAnswered = markAsAnswered,
                        markAsUnanswered = markAsUnanswered,
                        questionsTotalCount = questionsTotalCount,
                        questionsAnsweredCount = questionsAnsweredCount
                    )
                }

                is QuestionsListScreenModel.ViewState.Loading -> {
                    KTICircularProgressIndicator()
                }

                QuestionsListScreenModel.ViewState.Error -> {
                    KTIText(text = "Error!")
                }
            }
        }
    )

//    KTIScaffold(
//        topBar = { KTIChatTopAppBar() },
//    ) {
//        when (viewState) {
//            is QuestionsListScreenModel.ViewState.QuestionsLoaded -> {
//                QuestionList(
//                    questions = viewState.questions,
//                    markAsAnswered = markAsAnswered,
//                    markAsUnanswered = markAsUnanswered,
//                    questionsTotalCount = questionsTotalCount,
//                    questionsAnsweredCount = questionsAnsweredCount
//                )
//            }
//
//            is QuestionsListScreenModel.ViewState.Loading -> {
//                KTICircularProgressIndicator()
//            }
//
//            QuestionsListScreenModel.ViewState.Error -> {
//                KTIText(text = "Error!")
//            }
//        }
//    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ListScreenContent(
    viewState: QuestionsListScreenModel.ViewState,
    bottomSheetState: ModalBottomSheetState,
    bottomSheetContent: @Composable () -> Unit,
    onToggleBottomSheetClick: () -> Unit,
    topBarTitle: String,
    sortDropdownMenuDisplayed: Boolean,
    toggleDropdownMenu: () -> Unit,
    onSortModeClick: (QuestionsListScreenModel.SortMode) -> Unit,
    markAsAnswered: (Question) -> Unit,
    markAsUnanswered: (Question) -> Unit,
    questionsAnsweredCount: Int,
    questionsTotalCount: Int,
) {
//    Scaffold(
//        topBar = {
//            ListScreenTopBar(
//                bottomSheetState = bottomSheetState,
//                onToggleBottomSheetClick = onToggleBottomSheetClick,
//                topBarTitle = topBarTitle,
//                sortDropdownMenuDisplayed = sortDropdownMenuDisplayed,
//                toggleDropdownMenu = toggleDropdownMenu,
//                onSortModeClick = onSortModeClick
//            )
//        },
//    ) {
//
//
//    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(kti_softwhite),
    ) {
        KTIModalBottomSheetLayout(
            sheetState = bottomSheetState,
            bottomSheetContent = bottomSheetContent,
            modifier = Modifier.fillMaxSize(),
        ) {
            KTIColumnWithGradient {
                KTITopAppBar(title = topBarTitle)
//                    iconsSection = {
//                        IconButton(onClick = onToggleBottomSheetClick) {
//                            if (bottomSheetState.isVisible) {
//                                Icon(
//                                    imageVector = Icons.Filled.KeyboardArrowDown,
//                                    contentDescription = "Bottom sheet icon",
//                                    tint = kti_accent
//                                )
//                            } else {
//                                Icon(
//                                    imageVector = Icons.Filled.KeyboardArrowUp,
//                                    contentDescription = "Bottom sheet icon",
//                                    tint = kti_accent
//                                )
//                            }
            }
//                        IconButton(onClick = toggleDropdownMenu) {
//                            KTIIcon(drawableRes = R.drawable.ic_sort, tint = kti_soft_black)
//                        }
//                        DropdownMenu(
//                            expanded = sortDropdownMenuDisplayed,
//                            onDismissRequest = toggleDropdownMenu,
//                            modifier = Modifier.background(kti_soft_white)
//                        ) {
//                            QuestionsListViewModel.SortMode.values().toList().forEach { sortMode ->
//                                DropdownMenuItem(onClick = { onSortModeClick(sortMode) }) {
//                                    KTITextNew(
//                                        text = sortMode.displayName,
//                                        fontSize = 16.sp,
//                                        fontWeight = FontWeight.W400,
//                                        color = kti_soft_black
//                                    )
//                                }
//                            }
//                        }
//                    }
//                )
            when (viewState) {
                is QuestionsListScreenModel.ViewState.QuestionsLoaded -> {
                    QuestionList(
                        questions = viewState.questions,
                        markAsAnswered = markAsAnswered,
                        markAsUnanswered = markAsUnanswered,
                        questionsTotalCount = questionsTotalCount,
                        questionsAnsweredCount = questionsAnsweredCount
                    )
                }

                is QuestionsListScreenModel.ViewState.Loading -> {
                    KTICircularProgressIndicator()
                }

                QuestionsListScreenModel.ViewState.Error -> {
                    KTIText(text = "Error!")
                }
            }
        }
    }
}

private val horizontalPadding = 8.dp

@Composable
private fun QuestionList(
    questions: List<Question>,
    markAsAnswered: (Question) -> Unit,
    markAsUnanswered: (Question) -> Unit,
    questionsAnsweredCount: Int,
    questionsTotalCount: Int,
) {
    Column {
        ListScreenScoreBar(score = questionsAnsweredCount, total = questionsTotalCount)
        LazyColumn {
            itemsIndexed(
                items = questions,
                key = { _, item -> "${item.id} + ${item.hashCode()}" }
            ) { _, item ->
                QuestionItem(
                    item = item,
                    markAsAnswered = markAsAnswered,
                    markAsUnanswered = markAsUnanswered,
                )
            }
        }
    }
}

@Composable
private fun QuestionItem(
    item: Question,
    markAsAnswered: (Question) -> Unit,
    markAsUnanswered: (Question) -> Unit,
) {
    val isExpanded = rememberSaveable(item) { mutableStateOf(false) }
    val isAnswered = rememberSaveable(item) { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isAnswered.value) {
                    kti_green
                } else {
                    ktiColors.backgroundSurfaceVariant
                }
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(8f)
            ) {
                KTIVerticalSpacer(height = 4.dp)
                QuestionTopSection(question = item)
                QuestionTitle(isAnswered = isAnswered.value, question = item.question)
                AnimatedVisibility(visible = isExpanded.value) {
                    Column {
                        KTIVerticalSpacer(height = 2.dp)
                        KTITextNew(
                            text = item.answer,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W400,
                            modifier = Modifier.padding(horizontal = horizontalPadding + 2.dp),
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.5f)
                    .defaultMinSize(minHeight = 52.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isAnswered.value.not()) {
                    ToggleAnswerButton(
                        shouldDisplayAnswer = isExpanded.value,
                        displayAnswerOnClick = { isExpanded.value = !isExpanded.value },
                    )
                } else {
                    IconButton(onClick = {
                        isAnswered.value = false
                        isExpanded.value = false
                        markAsUnanswered.invoke(item)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "Reopen question",
                            tint = kti_softwhite,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
        AnimatedVisibility(visible = isExpanded.value || isAnswered.value) {
            Column(modifier = Modifier.fillMaxWidth()) {
                BottomSection(
                    isAnswered = isAnswered.value,
                    markAsAnswered = { question ->
                        isAnswered.value = true
                        isExpanded.value = false
                        markAsAnswered.invoke(question)
                    },
                    item = item,
                )
            }
        }
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = kti_divider.copy(alpha = 0.4f)
        )
    }
}

@Composable
private fun QuestionTopSection(
    question: Question,
) {
    KTITextNew(
        text = "${question.topCategory.displayName}, ${question.subCategory?.displayName}",
        fontSize = 10.sp,
        fontWeight = FontWeight.W300,
        color = ktiColors.textMain.copy(alpha = 0.6f),
        modifier = Modifier.padding(horizontal = 10.dp),
        lineHeight = 6.sp,
    )
}

@Composable
private fun QuestionTitle(
    isAnswered: Boolean,
    question: String,
) {
    KTIVerticalSpacer(height = if (isAnswered.not()) 2.dp else 0.dp)
    KTITextNew(
        text = question,
        fontSize = 14.sp,
        fontWeight = if (isAnswered.not()) FontWeight.SemiBold else FontWeight.Normal,
        modifier = Modifier.padding(horizontal = horizontalPadding + 2.dp),
        color = if (isAnswered.not()) ktiColors.textMain else kti_softwhite,
        lineHeight = 14.sp,
    )
    KTIVerticalSpacer(height = if (isAnswered.not()) 4.dp else 0.dp)
}

@Composable
private fun ToggleAnswerButton(
    shouldDisplayAnswer: Boolean,
    displayAnswerOnClick: () -> Unit,
) {
    Icon(
        imageVector = if (shouldDisplayAnswer) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
        contentDescription = null,
        tint = if (shouldDisplayAnswer) kti_accent else kti_softblack,
        modifier = Modifier
            .clickableNoRipple { displayAnswerOnClick() }
            .size(18.dp)
    )
}

@Composable
private fun BottomSection(
    isAnswered: Boolean,
    markAsAnswered: (Question) -> Unit,
    item: Question,
) {
    if (isAnswered.not()) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = horizontalPadding + 8.dp,
                    end = horizontalPadding + 8.dp,
                    top = 12.dp,
                    bottom = 2.dp
                )
        ) {
            MarkAsAnsweredButton(
                toggleAsAnswered = { markAsAnswered(item) },
            )
        }
    }
}

@Composable
private fun MarkAsAnsweredButton(
    toggleAsAnswered: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextButton(onClick = toggleAsAnswered) {
            KTITextNew(
                text = "Mark as answered",
                fontSize = 12.sp,
                fontWeight = FontWeight.W600,
                color = kti_green,
            )
        }
    }
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

