@file:OptIn(ExperimentalFoundationApi::class)

package sectonone.droidsoft.ap.screens.home.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import sectonone.droidsoft.ap.ui.components.AnimatedPagerIndicator
import sectonone.droidsoft.ap.ui.components.KTIIcon
import sectonone.droidsoft.ap.ui.components.KTITextNew
import sectonone.droidsoft.ap.ui.components.VerticalSpacer
import sectonone.droidsoft.ap.ui.components.clickableNoRipple
import sectonone.droidsoft.ap.ui.components.rememberAutoSwipeTimer
import sectonone.droidsoft.ap.model.UIHomeScreenSection
import sectonone.droidsoft.ap.theme.ktiColors
import sectonone.droidsoft.ap.theme.white
import sectonone.droidsoft.ap.theme.white80
import sectonone.droidsoft.ap.theme.white90

private val imageRadius = 24.dp
private const val pagerAutoScrollAnimationDurationMillis = 700
private val containerHeight = 450.dp
private val containerMinHeightStandalone = 454.dp

@Composable
fun PagerCarouselLayout(uiState: UIHomeScreenSection.PagerCarousel) {
    val items = uiState.items
    val pageCount = remember(items) { items.size }
    val pagerState = rememberPagerState(pageCount = { pageCount })

    var currentItem by remember(items, pagerState.currentPage) {
        mutableStateOf(items.getOrNull(pagerState.currentPage))
    }

    var showAnswer by remember { mutableStateOf(false) }

    val onTextClick = remember {
        Modifier.clickableNoRipple { showAnswer = !showAnswer }
    }

    val isPagerDragged = pagerState.interactionSource.collectIsDraggedAsState()

    val autoSwipeTimer = rememberAutoSwipeTimer(
        swipeInterval = 10000,
        doAutoSwipe = {
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % pageCount,
                animationSpec = tween(pagerAutoScrollAnimationDurationMillis)
            )
        },
        shouldAutoSwipe = showAnswer.not() && isPagerDragged.value.not()
    )
    val timerState = autoSwipeTimer.progress.collectAsState()

    val imageSize by animateDpAsState(targetValue = if (showAnswer) 104.dp else 240.dp, label = "Animatable image size")
    val cornerRadius by animateDpAsState(targetValue = if (showAnswer) 10.dp else 24.dp, label = "Animatable corner radius")

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            KTITextNew("Random questions", fontSize = 16.sp, color = ktiColors.textMain)
        }
        VerticalSpacer(16.dp)
        Box(Modifier.clip(RoundedCornerShape(40.dp))) {
            Crossfade(
                targetState = currentItem?.imagePath,
                animationSpec = tween(durationMillis = 700),
                label = "Blurred background image"
            ) { imagePath ->
                Image(
                    painter = painterResource(imagePath.toString()),
                    modifier = Modifier
                        .scale(1.5f)
                        .fillMaxWidth()
                        .graphicsLayer { alpha = 0.8f }
                        .heightIn(min = containerHeight)
                        .border(
                            width = 0.5.dp,
                            color = ktiColors.backgroundSurfaceVariant,
                            shape = RoundedCornerShape(size = 40.dp)
                        )
                        .blur(150.dp),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.FillWidth,
                    contentDescription = ""
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth().heightIn(min = containerHeight),
            ) {
                Column(modifier = Modifier.fillMaxWidth().weight(8f)) {
                    VerticalSpacer(height = 24.dp)
                    HorizontalPager(state = pagerState) { index ->
                        Box(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Card(
                                shape = RoundedCornerShape(cornerRadius),
                                elevation = 8.dp,
                            ) {
                                Image(
                                    painter = painterResource(currentItem?.imagePath.toString()),
                                    modifier = Modifier
                                        .size(imageSize)
                                        .clip(RoundedCornerShape(cornerRadius))
                                        .border(
                                            width = 0.5.dp,
                                            color = white80,
                                            shape = RoundedCornerShape(size = cornerRadius)
                                        ),
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                    VerticalSpacer(height = 24.dp)
                    AnimatedPagerIndicator(
                        numberOfPages = pagerState.pageCount,
                        selectedPage = pagerState.currentPage,
                        autoScrollProgressProvider = { timerState.value }
                    )
                    VerticalSpacer(height = 12.dp)
                    InfoSection(currentItem, showAnswer = showAnswer, onTextClick = onTextClick)
                }
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).weight(1.2f), verticalArrangement = Arrangement.Bottom) {
                    VerticalSpacer(12.dp)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
//                            KTIIcon(Icons.Default.BookmarkBorder, size = 24.dp)
//                            KTIIcon(Icons.Default.ChevronRight, size = 24.dp)
                            KTITextNew(if (showAnswer) "Hide answer" else "Show answer", fontSize = 12.sp, color = white80, modifier = Modifier.then(onTextClick))
                        }
//                        KTITextNew("Go to category", fontSize = 12.sp, color = white80)
                        KTIIcon(Icons.Default.ChevronRight, size = 24.dp)
                    }
                    VerticalSpacer(24.dp)
                }
            }
        }
        VerticalSpacer(24.dp)
    }
}

@Composable
private fun InfoSection(
    currentItem: UIHomeScreenSection.PagerCarousel.CarouselItem?,
    showAnswer: Boolean,
    onTextClick: Modifier,
) {
    if (currentItem == null) return

    val questionFontSize by animateFloatAsState(
        targetValue = if (showAnswer) 14f else 12f, // Define the font size in float
        label = "Question Font Size Animation"
    )

    val questionColorAsState: Color by animateColorAsState(
        targetValue = if (showAnswer) white else white90,
        animationSpec = tween(700),
        label = "page indicator animation",
    )

    val categoryFontSize by animateFloatAsState(
        targetValue = if (showAnswer) 11f else 14f, // Define the font size in float
        label = "Category Font Size Animation"
    )

    val categoryColorAsState: Color by animateColorAsState(
        targetValue = if (showAnswer.not()) white else white80,
        animationSpec = tween(700),
        label = "page indicator animation",
    )

    Crossfade(
        targetState = currentItem,
        animationSpec = tween(durationMillis = 700),
        label = "Info section"
    ) { item ->
        Column(Modifier.padding(start = 24.dp, end = 24.dp)) {
            VerticalSpacer(height = 4.dp)
            KTITextNew(
                text = item.category?.displayName.toString(),
                fontWeight = if (showAnswer.not()) FontWeight(500) else FontWeight(400),
                fontSize = categoryFontSize.sp,
                lineHeight = 22.sp,
                color = categoryColorAsState,
                letterSpacing = 0.14.sp,
                textAlign = TextAlign.Start,
                maxLines = 1
            )
            VerticalSpacer(height = 4.dp)
            val question = item.question.question
            val annotatedQuestion = buildAnnotatedString {
                val displayText = if (showAnswer) {
                    question // Show the entire paragraph if expanded
                } else {
                    question.take(100) // Adjust the number to fit "more" without extra text
                }

                append(displayText)

                if (showAnswer.not()) {
                    append(" ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = white)) {
                        append("show answer")
                    }
                }
            }
            val answer = item.question.answer
            val annotatedAnswer = buildAnnotatedString {
                val displayText = if (showAnswer) {
                    answer // Show the entire paragraph if expanded
                } else {
                    answer.take(100) // Adjust the number to fit "more" without extra text
                }

                append(displayText)

                if (showAnswer) {
                    append(" ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = white)) {
                        append("hide answer")
                    }
                }
            }
            KTITextNew(
                text = question,
                fontWeight = if (showAnswer) FontWeight(500) else FontWeight(400),
                fontSize = questionFontSize.sp,
                lineHeight = 16.sp,
                color = questionColorAsState,
                letterSpacing = 0.sp,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .animateContentSize()
                    .then(onTextClick)
            )
            if (showAnswer.not()) {
                VerticalSpacer(8.dp)
//                KTITextNew(
//                    text = "Show answer",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 10.sp,
//                    lineHeight = 22.sp,
//                    color = white,
//                    letterSpacing = 0.sp,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    modifier = Modifier
//                        .animateContentSize()
//                        .then(onTextClick)
//                )
            }
            if (showAnswer) {
                KTITextNew(
                    text = answer,
                    fontWeight = FontWeight(400),
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    color = white90,
                    letterSpacing = 0.sp,
                    maxLines = Int.MAX_VALUE,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .animateContentSize()
                        .then(onTextClick)
                )
                VerticalSpacer(8.dp)
//                KTITextNew(
//                    text = "Hide answer",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 10.sp,
//                    lineHeight = 22.sp,
//                    color = white,
//                    letterSpacing = 0.sp,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    modifier = Modifier
//                        .animateContentSize()
//                        .then(onTextClick)
//                )
            }
        }
    }
}