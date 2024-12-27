package sectonone.droidsoft.ap.screens.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ThumbDownOffAlt
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.ThumbsUpDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sectonone.droidsoft.ap.compose.HorizontalSpacer
import sectonone.droidsoft.ap.compose.KTIIcon
import sectonone.droidsoft.ap.compose.KTILinearProgressIndicator
import sectonone.droidsoft.ap.compose.KTITextNew
import sectonone.droidsoft.ap.compose.VerticalSpacer
import sectonone.droidsoft.ap.compose.prettyPrint
import sectonone.droidsoft.ap.model.InterviewHistorySummaryUI
import sectonone.droidsoft.ap.model.UIHomeScreenSection
import sectonone.droidsoft.ap.theme.ktiColors

enum class InterviewHistorySummaryVariant { GridSingleRow, GridTwoRows, Column; }

val interviewSummaryCardSize = 164.dp

@Composable
fun InterviewHistorySummaryLayout(
    uiState: UIHomeScreenSection.InterviewHistorySummary,
    variant: InterviewHistorySummaryVariant = InterviewHistorySummaryVariant.GridTwoRows,
    onSeeAllInterviewsClick: () -> Unit,
) {
    val itemCount = uiState.items.size

    Column {
        VerticalSpacer(12.dp)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            KTITextNew("Your last interviews", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            KTITextNew("See all", fontSize = 12.sp, color = ktiColors.textVariant2, modifier = Modifier.clickable { onSeeAllInterviewsClick.invoke() })
        }
        VerticalSpacer(8.dp)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            when (variant) {
                InterviewHistorySummaryVariant.GridSingleRow -> {
                    items(itemCount) { index ->
                        Row {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                InterviewSummaryCard(uiState.items[index], variant)
                            }
                        }
                    }
                }

                InterviewHistorySummaryVariant.GridTwoRows -> {
                    items(itemCount / 2) { index ->
                        Row {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // First item in the row
                                if (index * 2 < itemCount) {
                                    InterviewSummaryCard(uiState.items[index * 2], variant)
                                }
                                // Second item in the row
                                if ((index * 2) + 1 < itemCount) {
                                    InterviewSummaryCard(uiState.items[(index * 2) + 1], variant)
                                }
                            }
                        }
                    }
                }

                else -> {

                }
            }
        }
    }
}

@Composable
fun InterviewSummaryCard(item: InterviewHistorySummaryUI, variant: InterviewHistorySummaryVariant) {
    val animatedProgress = remember { Animatable(0f) }
    LaunchedEffect(item.scorePercent) {
        animatedProgress.animateTo(
            targetValue = item.scorePercent,
            animationSpec = tween(durationMillis = 2000, easing = LinearOutSlowInEasing)
        )
    }
    val animatedTextProgress = remember { Animatable(0f) }
    LaunchedEffect(item.scorePercent) {
        animatedTextProgress.animateTo(
            targetValue = item.scorePercent * 100,
            animationSpec = tween(durationMillis = 2000, easing = LinearOutSlowInEasing)
        )
    }

    val cardSizeModifier = when(variant) {
        InterviewHistorySummaryVariant.GridSingleRow -> {
            Modifier.size(interviewSummaryCardSize)
        }
        InterviewHistorySummaryVariant.GridTwoRows -> {
            Modifier.size(interviewSummaryCardSize)
        }
        InterviewHistorySummaryVariant.Column -> {
            Modifier.fillMaxWidth().height(144.dp)
        }
    }

    Card(
        modifier = cardSizeModifier then Modifier.clip(RoundedCornerShape(16.dp)),
        elevation = 4.dp,
        backgroundColor = ktiColors.backgroundSurfaceVariant
    ) {
        Column(Modifier.fillMaxWidth().padding(12.dp)) {
            Column(modifier = Modifier.fillMaxWidth().weight(8f)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    KTITextNew(item.interviewDate, fontSize = 12.sp, color = ktiColors.textVariant)
                    KTIIcon(Icons.Default.ChevronRight, size = 16.dp, tint = ktiColors.textVariant)
                }
                VerticalSpacer(12.dp)
                Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
                    KTIIcon(
                        when (item.successSummary) {
                            InterviewHistorySummaryUI.SuccessSummary.Failed -> Icons.Default.ThumbDownOffAlt
                            InterviewHistorySummaryUI.SuccessSummary.Average -> Icons.Default.ThumbsUpDown
                            InterviewHistorySummaryUI.SuccessSummary.Success -> Icons.Default.ThumbUp
                        }
                    )
                    HorizontalSpacer(12.dp)
                    Column {
                        KTITextNew(item.mainCategory, fontWeight = FontWeight.W600, maxLines = 2)
                        KTITextNew(
                            item.categoriesSummary.prettyPrint(),
                            color = ktiColors.textVariant2,
                            fontSize = 10.sp,
                            maxLines = 2
                        )
                    }
                }
                VerticalSpacer(12.dp)
            }
            Column(modifier = Modifier.fillMaxWidth().weight(2.5f)) {
                KTITextNew("Score", color = ktiColors.textVariant, fontSize = 12.sp)
                VerticalSpacer(4.dp)
                Row(modifier = Modifier.fillMaxWidth().weight(2f), verticalAlignment = Alignment.CenterVertically) {
                    KTILinearProgressIndicator(animatedProgress.value, modifier = Modifier.weight(5f))
                    KTITextNew(
                        "${animatedTextProgress.value.toInt()}%",
                        fontSize = 10.sp,
                        color = ktiColors.textVariant2,
                        modifier = Modifier.weight(1.7f).padding(start = 8.dp),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}