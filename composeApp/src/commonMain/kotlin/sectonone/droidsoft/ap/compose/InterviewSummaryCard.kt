package sectonone.droidsoft.ap.compose

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import sectonone.droidsoft.ap.model.InterviewHistorySummary
import sectonone.droidsoft.ap.screens.home.components.InterviewHistorySummaryVariant
import sectonone.droidsoft.ap.screens.home.components.interviewSummaryCardSize
import sectonone.droidsoft.ap.theme.ktiColors

@Composable
fun InterviewSummaryCard(
    item: InterviewHistorySummary,
    onClick: (InterviewHistorySummary) -> Unit,
    variant: InterviewHistorySummaryVariant
) {
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

    val cardSizeModifier = when (variant) {
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
        modifier = cardSizeModifier then Modifier.clip(RoundedCornerShape(16.dp)).clickable { onClick.invoke(item) },
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
                            InterviewHistorySummary.SuccessSummary.Failed -> Icons.Default.ThumbDownOffAlt
                            InterviewHistorySummary.SuccessSummary.Average -> Icons.Default.ThumbsUpDown
                            InterviewHistorySummary.SuccessSummary.Success -> Icons.Default.ThumbUp
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
            Row(modifier = Modifier.fillMaxWidth().weight(2f), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.SpaceBetween) {
                Column(Modifier.weight(10f)) {
                    KTITextNew("Score", color = ktiColors.textVariant, fontSize = 11.sp)
                    VerticalSpacer(6.dp)
                    KTILinearProgressIndicator(animatedProgress.value)
                }
                KTITextNew(
                    "${animatedTextProgress.value.toInt()}%",
                    fontSize = 11.sp,
                    color = ktiColors.textVariant2,
                    modifier = Modifier.padding(start = 8.dp).weight(3f),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}