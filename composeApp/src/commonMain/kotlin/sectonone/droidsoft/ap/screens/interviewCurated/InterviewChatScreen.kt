package sectonone.droidsoft.ap.screens.interviewCurated

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import sectonone.droidsoft.ap.compose.KTIButtonShared
import sectonone.droidsoft.ap.compose.KTIChatTopAppBar
import sectonone.droidsoft.ap.compose.KTIFloatingActionButton
import sectonone.droidsoft.ap.compose.KTIHorizontalSpacer
import sectonone.droidsoft.ap.compose.KTIIcon
import sectonone.droidsoft.ap.compose.KTIScaffold
import sectonone.droidsoft.ap.compose.KTITextNew
import sectonone.droidsoft.ap.compose.KTIVerticalSpacer
import sectonone.droidsoft.ap.compose.LoadingAnimation
import sectonone.droidsoft.ap.di.getScreenModel
import sectonone.droidsoft.ap.model.Question
import sectonone.droidsoft.ap.model.TopCategory
import sectonone.droidsoft.ap.screens.interviewCurated.model.InterviewChatItemUiModel
import sectonone.droidsoft.ap.theme.KTITheme
import sectonone.droidsoft.ap.theme.ktiColors
import sectonone.droidsoft.ap.theme.kti_dark_blue
import sectonone.droidsoft.ap.theme.kti_dark_grey
import sectonone.droidsoft.ap.theme.kti_green
import sectonone.droidsoft.ap.theme.kti_grey
import sectonone.droidsoft.ap.theme.kti_light_blue
import sectonone.droidsoft.ap.theme.kti_softblack
import sectonone.droidsoft.ap.theme.kti_softwhite

internal class InterviewChatScreen(private val categories: List<TopCategory>) : Screen {

    @Composable
    override fun Content() {
        val screenModel: InterviewChatScreenModel = getScreenModel()

        val scoreboardState = screenModel.scoreboardState.collectAsState().value
        val chatState = screenModel.screenState.collectAsState().value
        val inputEnabledState = screenModel.inputEnabled.collectAsState().value
        val currentQuestion = screenModel.currentQuestion.collectAsState().value

        val chatListState = rememberLazyListState()

        LaunchedEffect(null) {
            screenModel.initQuestions(categories)
        }

        LaunchedEffect(chatState, inputEnabledState) {
            if (chatState is InterviewChatScreenModel.ViewStateChat.InterviewActive && chatState.chatItems.isNotEmpty()) {
                chatListState.animateScrollToItem(chatState.chatItems.lastIndex)
            }
        }

        InterviewChatScreenContent(
            scoreboardState = scoreboardState,
            onAddPointClick = { screenModel.questionAnsweredWithPoint() },
            onNoPointClick = { screenModel.questionAnsweredNoPoint() },
            screenStateChat = chatState,
            inputEnabled = inputEnabledState,
            chatListState = chatListState,
            currentQuestion = currentQuestion,
        )
    }
}

@Composable
private fun InterviewChatScreenContent(
    screenStateChat: InterviewChatScreenModel.ViewStateChat,
    scoreboardState: InterviewChatScreenModel.Scoreboard,
    onAddPointClick: () -> Unit,
    onNoPointClick: () -> Unit,
    inputEnabled: Boolean,
    chatListState: LazyListState,
    currentQuestion: Question?,
) {
    val isAnswerExpanded = rememberSaveable(screenStateChat) { mutableStateOf(false) }
    val setIsAnswerExpanded = { isAnswerExpanded.value = !isAnswerExpanded.value  }
    KTIScaffold(
        topBar = {
            KTIChatTopAppBar()
        },
        bottomBar = {
            ControlSection(
                addPointClick = onAddPointClick,
                noPointClick = onNoPointClick,
                inputEnabled = inputEnabled,
                showAnswerClick = setIsAnswerExpanded,
                isAnswerExpanded = isAnswerExpanded.value,
            )
        },
        floatingActionButton = {
//            androidx.compose.animation.AnimatedVisibility(
//                visible = isAnswerExpanded.value.not() && inputEnabled,
//                enter = scaleIn(),
//                exit = scaleOut(),
//            ) {
//                KTIFloatingActionButton(onClick = setIsAnswerExpanded, icon = Icons.Default.QuestionMark)
//            }
        }
    ) {
        when (screenStateChat) {
            is InterviewChatScreenModel.ViewStateChat.InterviewActive -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(Modifier.weight(10f)) {
                        LazyColumn(
                            modifier = Modifier.align(Alignment.TopCenter),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            state = chatListState,
                        ) {
                            item { KTIVerticalSpacer(height = 8.dp) }
                            itemsIndexed(
                                items = screenStateChat.chatItems,
                                key = { i, it -> "${it.hashCode()}, index: $i" }) { _, chatItem ->
                                when (chatItem) {
                                    is InterviewChatItemUiModel.CandidateMessage -> {
                                        CandidateBubbleChatItem(chatItem)
                                    }

                                    is InterviewChatItemUiModel.InterviewerMessage -> {
                                        InterviewerBubbleChatItem(chatItem)
                                    }
                                }
                            }
                            item { KTIVerticalSpacer(height = 8.dp) }
                        }
                        androidx.compose.animation.AnimatedVisibility(
                            visible = isAnswerExpanded.value,
                            modifier = Modifier.align(Alignment.BottomEnd).padding(start = 32.dp, end = 16.dp, bottom = 4.dp),
                        ) {
                            Column(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(
                                            topEnd = radius,
                                            topStart = radius,
                                            bottomStart = radius,
                                            bottomEnd = 0.dp,
                                        )
                                    )
                                    .background(ktiColors.appBars)
                                    .padding(vertical = 8.dp, horizontal = 12.dp)
                                    .verticalScroll(rememberScrollState())
                                    .clickable { setIsAnswerExpanded.invoke() }
                            ) {
                                KTIVerticalSpacer(4.dp)
                                KTIIcon(Icons.Default.Info, size = 16.dp)
                                KTIVerticalSpacer(4.dp)
                                KTITextNew(text = currentQuestion?.answer ?: "Current question is null")
                            }
                        }
                    }
                    ControlSection(
                        addPointClick = onAddPointClick,
                        noPointClick = onNoPointClick,
                        modifier = Modifier.weight(1f),
                        inputEnabled = inputEnabled,
                        showAnswerClick = setIsAnswerExpanded,
                        isAnswerExpanded = isAnswerExpanded.value,
                    )
                }
            }

            is InterviewChatScreenModel.ViewStateChat.InterviewFinished -> {
                KTITextNew("no questions left", fontSize = 16.sp, fontWeight = FontWeight.W700)
            }
        }
    }


//    KTIColumnWithGradient {
//        KTIChatTopAppBar()
//        when (screenStateChat) {
//            is InterviewChatScreenModel.ViewStateChat.InterviewActive -> {
//                Column(
//                    modifier = Modifier.fillMaxSize(),
//                    verticalArrangement = Arrangement.Bottom,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Box(Modifier.weight(10f)) {
//                        LazyColumn(
//                            modifier = Modifier.align(Alignment.TopCenter),
//                            contentPadding = PaddingValues(horizontal = 16.dp),
//                            state = chatListState,
//                        ) {
//                            item { KTIVerticalSpacer(height = 8.dp) }
//                            itemsIndexed(
//                                items = screenStateChat.chatItems,
//                                key = { i, it -> "${it.hashCode()}, index: $i" }) { _, chatItem ->
//                                when (chatItem) {
//                                    is InterviewChatItemUiModel.CandidateMessage -> {
//                                        CandidateBubbleChatItem(chatItem)
//                                    }
//
//                                    is InterviewChatItemUiModel.InterviewerMessage -> {
//                                        InterviewerBubbleChatItem(chatItem)
//                                    }
//                                }
//                            }
//                            item { KTIVerticalSpacer(height = 8.dp) }
//                        }
//                        androidx.compose.animation.AnimatedVisibility(
//                            isAnswerExpanded.value,
//                            modifier = Modifier.align(Alignment.BottomEnd).padding(start = 32.dp, end = 16.dp, bottom = 4.dp)
//                        ) {
//                            Column(
//                                modifier = Modifier
//                                    .clip(
//                                        RoundedCornerShape(
//                                            topEnd = radius,
//                                            topStart = radius,
//                                            bottomStart = radius,
//                                            bottomEnd = 0.dp,
//                                        )
//                                    )
//                                    .background(kti_softwhite)
//                                    .padding(vertical = 8.dp, horizontal = 12.dp)
//                                    .verticalScroll(rememberScrollState())
//                            ) {
//                                KTIVerticalSpacer(4.dp)
//                                KTIIcon(Icons.Default.Info, size = 16.dp)
//                                KTIVerticalSpacer(4.dp)
//                                KTITextNew(text = currentQuestion?.answer ?: "Current question is null")
//                            }
//                        }
//                    }
//                    ControlSection(
//                        addPointClick = onAddPointClick,
//                        noPointClick = onNoPointClick,
//                        modifier = Modifier.weight(1f),
//                        inputEnabled = inputEnabled,
//                        showAnswerClick = { isAnswerExpanded.value = !isAnswerExpanded.value },
//                        isAnswerExpanded = isAnswerExpanded.value,
//                    )
//                }
//            }
//
//            is InterviewChatScreenModel.ViewStateChat.InterviewFinished -> {
//                KTITextNew("no questions left", fontSize = 16.sp, fontWeight = FontWeight.W700)
//            }
//        }
//    }
}

private val radius = 24.dp

@Composable
private fun LazyItemScope.InterviewerBubbleChatItem(chatItem: InterviewChatItemUiModel.InterviewerMessage) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        KTIHorizontalSpacer(44.dp)
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topEnd = radius,
                        topStart = radius,
                        bottomStart = radius,
                        bottomEnd = 0.dp,
                    )
                )
                .background(ktiColors.secondary)
                .padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            when (chatItem) {
                is InterviewChatItemUiModel.InterviewerMessage.OtherMessage -> {
                    KTITextNew(
                        text = chatItem.message,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        color = ktiColors.onSecondary,
                    )
                }

                is InterviewChatItemUiModel.InterviewerMessage.QuestionAsked -> {
                    KTITextNew(
                        text = chatItem.question.question,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        color = ktiColors.onSecondary,
                    )
                }

                InterviewChatItemUiModel.InterviewerMessage.Writing -> {
                    LoadingAnimation(circleColor = ktiColors.onSecondary)
                }
            }
        }
    }
}

@Composable
private fun LazyItemScope.CandidateBubbleChatItem(chatItem: InterviewChatItemUiModel.CandidateMessage) {
    val paddingBottom = 4.dp
    Row(
        modifier = Modifier.padding(top = 4.dp, bottom = paddingBottom),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topEnd = radius,
                        topStart = radius,
                        bottomStart = 0.dp,
                        bottomEnd = radius,
                    )
                )
                .background(ktiColors.chatPrimary)
                .padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            when (chatItem) {
                InterviewChatItemUiModel.CandidateMessage.BadAnswer -> {
                    KTITextNew(
                        text = "I don't know. :(",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                }

                InterviewChatItemUiModel.CandidateMessage.GoodAnswer -> {
                    KTITextNew(
                        text = "Sure, I know!",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                }

                InterviewChatItemUiModel.CandidateMessage.Writing -> {
                    LoadingAnimation(circleColor = ktiColors.textMain)
                }
            }
        }
        KTIHorizontalSpacer(44.dp)
    }
}

@Composable
private fun ControlSection(
    addPointClick: () -> Unit,
    noPointClick: () -> Unit,
    showAnswerClick: () -> Unit,
    inputEnabled: Boolean,
    isAnswerExpanded: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier then Modifier
            .fillMaxWidth()
            .sizeIn(minHeight = 80.dp)
            .background(KTITheme.colors.appBars),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            KTIButtonShared(
                label = "I knew it!",
                onClick = addPointClick,
                iconColor = if (inputEnabled) kti_softwhite else kti_grey,
                enabled = inputEnabled,
                backgroundColor = kti_green,
                labelColor = if (inputEnabled) kti_softwhite else kti_grey,
                modifier = Modifier.weight(1f),
            )
            KTIHorizontalSpacer(width = 16.dp)
            KTIButtonShared(
                label = "Confused :(",
                onClick = noPointClick,
                iconColor = if (inputEnabled) kti_softwhite else kti_grey,
                enabled = inputEnabled,
                backgroundColor = if (inputEnabled) kti_grey else kti_grey.copy(alpha = 0.7f),
                labelColor = if (inputEnabled) kti_dark_grey.copy(alpha = 0.9f) else kti_dark_grey.copy(alpha = 0.6f),
                modifier = Modifier.weight(1f),
            )
            KTIHorizontalSpacer(width = 16.dp)
            KTIFloatingActionButton(onClick = showAnswerClick, icon = Icons.Default.QuestionMark)
//            KTIButtonShared(
//                label = if (isAnswerExpanded.not()) "Show answer" else "Hide answer",
//                onClick = showAnswerClick,
//                icon = if (isAnswerExpanded.not()) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
//                iconColor = if (inputEnabled) kti_softblack else kti_grey,
//                enabled = inputEnabled,
//                backgroundColor = kti_softwhite,
//                labelColor = if (inputEnabled) kti_softblack else kti_grey,
//            )
        }
    }
}