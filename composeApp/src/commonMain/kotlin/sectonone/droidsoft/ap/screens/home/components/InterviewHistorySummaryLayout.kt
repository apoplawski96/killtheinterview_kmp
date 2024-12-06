package sectonone.droidsoft.ap.screens.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sectonone.droidsoft.ap.compose.KTIHorizontalSpacer
import sectonone.droidsoft.ap.compose.KTIIcon
import sectonone.droidsoft.ap.compose.KTILinearProgressIndicator
import sectonone.droidsoft.ap.compose.KTITextNew
import sectonone.droidsoft.ap.compose.KTIVerticalSpacer
import sectonone.droidsoft.ap.compose.prettyPrint
import sectonone.droidsoft.ap.model.InterviewSummary
import sectonone.droidsoft.ap.model.UIHomeScreenSection
import sectonone.droidsoft.ap.theme.ktiColors

@Composable
fun InterviewHistorySummaryLayout(uiState: UIHomeScreenSection.InterviewHistorySummary) {
    val itemCount = uiState.items.size // Replace with the actual list size
    val cardSize = 164.dp // Adjust size as needed

    Column {
        KTIVerticalSpacer(12.dp)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            KTITextNew("Your last interviews", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            KTITextNew("See all", fontSize = 12.sp, color = ktiColors.textVariant2)
        }
        KTIVerticalSpacer(12.dp)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(itemCount / 2) { index ->
                Row {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // First item in the row
                        if (index * 2 < itemCount) {
                            ItemCard(uiState.items[index * 2], cardSize)
                        }
                        // Second item in the row
                        if ((index * 2) + 1 < itemCount) {
                            ItemCard(uiState.items[(index * 2) + 1], cardSize)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCard(item: InterviewSummary, cardSize: Dp) {
    // Animate progress bar from 0 to item.scorePercent
    val animatedProgress = remember { Animatable(0f) }
    LaunchedEffect(item.scorePercent) {
        animatedProgress.animateTo(
            targetValue = item.scorePercent,
            animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
        )
    }
    // Animate text progress from 0% to item.scorePercentDisplay
    val animatedTextProgress = remember { Animatable(0f) }
    LaunchedEffect(item.scorePercent) {
        animatedTextProgress.animateTo(
            targetValue = item.scorePercent * 100,
            animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
        )
    }

    Card(
        modifier = Modifier
            .size(cardSize)
            .clip(RoundedCornerShape(16.dp)),
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
                KTIVerticalSpacer(12.dp)
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    KTIIcon(
                        when (item.successSummary) {
                            InterviewSummary.SuccessSummary.Failed -> Icons.Default.ThumbDownOffAlt
                            InterviewSummary.SuccessSummary.Average -> Icons.Default.ThumbsUpDown
                            InterviewSummary.SuccessSummary.Success -> Icons.Default.ThumbUp
                        }
                    )
                    KTIHorizontalSpacer(12.dp)
                    Column {
                        KTITextNew(item.mainCategory.displayName, fontWeight = FontWeight.W600, maxLines = 2)
                        KTITextNew(item.categories.prettyPrint(), color = ktiColors.textVariant2, fontSize = 10.sp, maxLines = 2)
                    }
                }
                KTIVerticalSpacer(12.dp)
            }
            Column(modifier = Modifier.fillMaxWidth().weight(2.5f)) {
                KTITextNew("Progress", color = ktiColors.textVariant, fontSize = 12.sp)
                KTIVerticalSpacer(4.dp)
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