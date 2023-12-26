package sectonone.droidsoft.ap.screens.interviewCurated.legacy//package com.example.myapplication.screens.interviewCurated.legacy
//
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ColumnScope
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.defaultMinSize
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyItemScope
//import androidx.compose.foundation.lazy.itemsIndexed
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.Divider
//import androidx.compose.material.Icon
//import androidx.compose.material.IconButton
//import androidx.compose.material.TextButton
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.KeyboardArrowDown
//import androidx.compose.material.icons.filled.KeyboardArrowUp
//import androidx.compose.material.icons.filled.Refresh
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import cafe.adriel.voyager.core.screen.Screen
//import com.appkickstarter.shared.SharedRes
//import sectonone.droidsoft.ap.compose.KTIColumnWithGradient
//import sectonone.droidsoft.ap.compose.KTIHorizontalSpacer
//import sectonone.droidsoft.ap.compose.KTIIcon
//import sectonone.droidsoft.ap.compose.KTIIconButton
//import sectonone.droidsoft.ap.compose.KTITextNew
//import sectonone.droidsoft.ap.compose.KTITopAppBar
//import sectonone.droidsoft.ap.compose.KTIVerticalSpacer
//import sectonone.droidsoft.ap.compose.clickableNoRipple
//import sectonone.droidsoft.ap.di.getScreenModel
//import sectonone.droidsoft.ap.model.Question
//import sectonone.droidsoft.ap.model.TopCategory
//import com.example.myapplication.screens.interviewCurated.InterviewChatScreenContent
//import com.example.myapplication.screens.interviewCurated.InterviewCuratedScreenModel
//import sectonone.droidsoft.ap.screens.interviewCurated.model.InterviewChatItemUiModel
//import com.example.myapplication.theme.kti_accent
//import com.example.myapplication.theme.kti_dark_blue
//import com.example.myapplication.theme.kti_divider
//import com.example.myapplication.theme.kti_green
//import com.example.myapplication.theme.kti_grey
//import com.example.myapplication.theme.kti_light_blue
//import com.example.myapplication.theme.kti_red
//import com.example.myapplication.theme.kti_softblack
//import com.example.myapplication.theme.kti_softwhite
//import org.jetbrains.compose.resources.ExperimentalResourceApi
//
//class LegacyInterviewCuratedScreen(private val categories: List<TopCategory>) : Screen {
//
//    @Composable
//    override fun Content() {
//        val screenModel: InterviewCuratedScreenModel = getScreenModel()
//
//        val scoreboardState = screenModel.scoreboardState.collectAsState().value
//
//        val chatState = screenModel.screenState.collectAsState().value
//
//        LaunchedEffect(null) { screenModel.initQuestions(categories) }
//
////        InterviewCuratedScreenContent(
////            screenState = state,
////            scoreboardState = scoreboardState,
////            onAddPointClick = { screenModel.questionAnsweredWithPoint() },
////            onNoPointClick = { screenModel.questionAnsweredNoPoint() },
////        )
//
//        InterviewChatScreenContent(
//            scoreboardState = scoreboardState,
//            onAddPointClick = { screenModel.questionAnsweredWithPoint() },
//            onNoPointClick = { screenModel.questionAnsweredNoPoint() },
//            screenStateChat = chatState,
//        )
//    }
//}
//
//
//private val horizontalPadding = 8.dp
//
//@Composable
//private fun InterviewChatScreenContent(
//    screenStateChat: InterviewCuratedScreenModel.ViewStateChat,
//    scoreboardState: InterviewCuratedScreenModel.Scoreboard,
//    onAddPointClick: () -> Unit,
//    onNoPointClick: () -> Unit,
//) {
//    KTIColumnWithGradient {
//        KTITopAppBar(
//            title = "Interview Simulation, " + "Score: ${scoreboardState.questionsAnswered}/${scoreboardState.questionsAsked}",
//            iconsSection = { },
//        )
//        when (screenStateChat) {
//            is InterviewCuratedScreenModel.ViewStateChat.InterviewActive -> {
//                Column(Modifier.fillMaxSize()) {
//                    LazyColumn(
//                        modifier = Modifier.weight(10f),
//                        contentPadding = PaddingValues(horizontal = 16.dp),
//                    ) {
//                        item { KTIVerticalSpacer(height = 8.dp) }
//                        itemsIndexed(items = screenStateChat.chatItems, key = { i, it -> "${it.hashCode()}, index: $i" }) { _, chatItem ->
//                            when (chatItem) {
//                                is InterviewChatItemUiModel.Answer -> {
//                                    CandidateBubbleChatItem(chatItem)
//                                }
//
//                                is InterviewChatItemUiModel.InterviewerQuestion -> {
//                                    InterviewerBubbleChatItem(chatItem)
//                                }
//                            }
//                        }
//                        item { KTIVerticalSpacer(height = 8.dp) }
//                    }
//                    ControlSection(
//                        addPointClick = onAddPointClick,
//                        noPointClick = onNoPointClick,
//                        modifier = Modifier.weight(1.5f),
//                    )
//                }
//            }
//
//            is InterviewCuratedScreenModel.ViewStateChat.InterviewFinished -> {
//                KTITextNew("no questions left", fontSize = 16.sp, fontWeight = FontWeight.W700)
//            }
//        }
//    }
//}
//
//private val radius = 24.dp
//
//@Composable
//private fun LazyItemScope.InterviewerBubbleChatItem(chatItem: InterviewChatItemUiModel.InterviewerQuestion) {
//    Row(
//        modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth(),
//        horizontalArrangement = Arrangement.End,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        KTIHorizontalSpacer(44.dp)
//        Box(
//            modifier = Modifier
//                .clip(
//                    RoundedCornerShape(
//                        topEnd = radius,
//                        topStart = radius,
//                        bottomStart = radius,
//                        bottomEnd = 0.dp,
//                    )
//                )
//                .background(kti_dark_blue)
//                .padding(vertical = 8.dp, horizontal = 12.dp)
//        ) {
//            KTITextNew(
//                text = chatItem.question.question,
//                fontSize = 14.sp,
//                fontWeight = FontWeight.W400,
//                color = kti_softwhite,
//            )
//        }
//    }
//}
//
//@Composable
//private fun LazyItemScope.CandidateBubbleChatItem(chatItem: InterviewChatItemUiModel.Answer) {
//    Row(
//        modifier = Modifier.padding(vertical = 4.dp),
//        horizontalArrangement = Arrangement.Start,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Box(
//            modifier = Modifier
//                .clip(
//                    RoundedCornerShape(
//                        topEnd = radius,
//                        topStart = radius,
//                        bottomStart = 0.dp,
//                        bottomEnd = radius,
//                    )
//                )
//                .background(kti_light_blue)
//                .padding(vertical = 8.dp, horizontal = 12.dp)
//        ) {
//            when (chatItem) {
//                InterviewChatItemUiModel.Answer.BadAnswer -> {
//                    KTITextNew(
//                        text = "I don't know. :(",
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.W400
//                    )
//                }
//
//                InterviewChatItemUiModel.Answer.GoodAnswer -> {
//                    KTITextNew(
//                        text = "Sure, I know!",
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.W400
//                    )
//                }
//            }
//        }
//        KTIHorizontalSpacer(44.dp)
//    }
//}
//
//@Composable
////private fun InterviewCuratedScreenContent(
////    screenState: InterviewCuratedScreenModel.ViewStateSimple,
////    scoreboardState: InterviewCuratedScreenModel.Scoreboard,
////    onAddPointClick: () -> Unit,
////    onNoPointClick: () -> Unit,
////) {
////    KTIColumnWithGradient {
////        KTITopAppBar(
////            title = "Interview Simulation, " + "Score: ${scoreboardState.questionsAnswered}/${scoreboardState.questionsAsked}",
////            iconsSection = { })
////        when (screenState) {
////            InterviewCuratedScreenModel.ViewStateSimple.Idle -> {
////                KTICircularProgressIndicator()
////            }
////
////            InterviewCuratedScreenModel.ViewStateSimple.NoQuestionsLeft -> {
////                KTITextNew("no questions left", fontSize = 16.sp, fontWeight = FontWeight.W700)
////            }
////
////            is InterviewCuratedScreenModel.ViewStateSimple.QuestionDropped -> {
////                Column(Modifier.fillMaxSize()) {
////                    KTIIllustration(imageResource = SharedRes.images.undraw_interview_re_e5jn)
////                    KTIVerticalSpacer(height = 16.dp)
////                    QuestionCard(screenState.question, Modifier.weight(10f))
////                    ControlSection(
////                        addPointClick = onAddPointClick,
////                        noPointClick = onNoPointClick,
////                        modifier = Modifier.weight(1.5f),
////                    )
////                }
////            }
////        }
////    }
////}
//
//@OptIn(ExperimentalResourceApi::class)
//private fun ColumnScope.ControlSection(
//    addPointClick: () -> Unit,
//    noPointClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Column(modifier = modifier then Modifier.fillMaxWidth().background(kti_softwhite)) {
//        Divider(color = kti_grey, thickness = 1.5.dp)
//        KTIVerticalSpacer(8.dp)
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            KTIIconButton(onClick = addPointClick, modifier = Modifier.weight(1f)) {
//                KTIIcon(imageResource = SharedRes.images.ic_check, tint = kti_green, size = 44.dp)
//            }
//            KTIIconButton(onClick = noPointClick, modifier = Modifier.weight(1f)) {
//                KTIIcon(imageResource = SharedRes.images.ic_cross, tint = kti_red, size = 26.dp)
//            }
//        }
//    }
//}
//
//@Composable
//private fun InterviewButton(onClick: () -> Unit, label: String, iconResId: Int) {
//
//}
//
//@Composable
//private fun QuestionCard(
//    item: Question,
//    modifier: Modifier = Modifier,
//) {
//    val isExpanded = rememberSaveable(item) { mutableStateOf(false) }
//    val isAnswered = rememberSaveable(item) { mutableStateOf(false) }
//    Column(
//        modifier = modifier then Modifier
//            .fillMaxWidth()
//            .background(kti_softwhite)
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.Top
//        ) {
//            Column(
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.Start,
//                modifier = Modifier.weight(8f)
//            ) {
//                KTIVerticalSpacer(height = 8.dp)
//                QuestionTopSection(question = item)
//                QuestionTitle(isAnswered = isAnswered.value, question = item.question)
//                AnimatedVisibility(visible = isExpanded.value) {
//                    Column {
//                        KTIVerticalSpacer(height = 2.dp)
//                        KTITextNew(
//                            text = item.answer,
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.W400,
//                            modifier = Modifier.padding(horizontal = horizontalPadding + 2.dp),
//                        )
//                        KTIVerticalSpacer(height = 16.dp)
//                    }
//                }
//            }
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1.5f)
//                    .defaultMinSize(minHeight = 52.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                if (isAnswered.value.not()) {
//                    ToggleAnswerButton(
//                        shouldDisplayAnswer = isExpanded.value,
//                        displayAnswerOnClick = { isExpanded.value = !isExpanded.value },
//                    )
//                } else {
//                    IconButton(onClick = {
//                        isAnswered.value = false
//                        isExpanded.value = false
//                    }) {
//                        Icon(
//                            imageVector = Icons.Filled.Refresh,
//                            contentDescription = "Reopen question",
//                            tint = kti_softwhite,
//                            modifier = Modifier.size(18.dp)
//                        )
//                    }
//                }
//            }
//        }
//        AnimatedVisibility(visible = isExpanded.value || isAnswered.value) {
//            Column(modifier = Modifier.fillMaxWidth()) {
////                BottomSection(
////                    isAnswered = isAnswered.value,
////                    markAsAnswered = { question ->
////                        isAnswered.value = true
////                        isExpanded.value = false
//////                            markAsAnswered.invoke(question)
////                    },
////                    item = item,
////                )
//            }
//        }
//        Divider(
//            modifier = Modifier.fillMaxWidth(),
//            thickness = 1.dp,
//            color = kti_divider.copy(alpha = 0.4f)
//        )
//    }
//}
//
//@Composable
//private fun QuestionTopSection(
//    question: Question,
//) {
//    KTITextNew(
//        text = "${question.topCategory.displayName}. ${question.subCategory?.displayName ?: ""}",
//        fontSize = 14.sp,
//        fontWeight = FontWeight.W300,
//        color = kti_softblack.copy(alpha = 0.8f),
//        modifier = Modifier.padding(horizontal = 10.dp),
//        lineHeight = 6.sp,
//    )
//}
//
//@Composable
//private fun QuestionTitle(
//    isAnswered: Boolean,
//    question: String,
//) {
//    KTIVerticalSpacer(height = if (isAnswered.not()) 4.dp else 0.dp)
//    KTITextNew(
//        text = question,
//        fontSize = 20.sp,
//        fontWeight = if (isAnswered.not()) FontWeight.Medium else FontWeight.Normal,
//        modifier = Modifier.padding(horizontal = horizontalPadding + 2.dp),
//        color = if (isAnswered.not()) kti_softblack else kti_softwhite,
//        lineHeight = 14.sp,
//    )
//    KTIVerticalSpacer(height = if (isAnswered.not()) 8.dp else 0.dp)
//}
//
//@Composable
//private fun BottomSection(
//    isAnswered: Boolean,
//    markAsAnswered: (Question) -> Unit,
//    item: Question,
//) {
//    if (isAnswered.not()) {
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(
//                    start = horizontalPadding + 8.dp,
//                    end = horizontalPadding + 8.dp,
//                    top = 12.dp,
//                    bottom = 2.dp
//                )
//        ) {
//            MarkAsAnsweredButton(
//                toggleAsAnswered = { markAsAnswered(item) },
//            )
//        }
//    }
//}
//
//@Composable
//private fun MarkAsAnsweredButton(
//    toggleAsAnswered: () -> Unit,
//) {
//    Row(
//        horizontalArrangement = Arrangement.End,
//        verticalAlignment = Alignment.CenterVertically,
//    ) {
//        TextButton(onClick = toggleAsAnswered) {
//            KTITextNew(
//                text = "Mark as answered",
//                fontSize = 12.sp,
//                fontWeight = FontWeight.W600,
//                color = kti_green,
//            )
//        }
//    }
//}
//
//@Composable
//private fun ToggleAnswerButton(
//    shouldDisplayAnswer: Boolean,
//    displayAnswerOnClick: () -> Unit,
//) {
//    Icon(
//        imageVector = if (shouldDisplayAnswer) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
//        contentDescription = null,
//        tint = if (shouldDisplayAnswer) kti_accent else kti_softblack,
//        modifier = Modifier
//            .clickableNoRipple { displayAnswerOnClick() }
//            .size(18.dp)
//    )
//}