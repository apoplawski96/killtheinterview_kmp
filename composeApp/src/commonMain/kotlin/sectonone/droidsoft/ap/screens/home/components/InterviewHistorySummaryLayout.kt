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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sectonone.droidsoft.ap.compose.HorizontalSpacer
import sectonone.droidsoft.ap.compose.InterviewSummaryCard
import sectonone.droidsoft.ap.compose.KTIIcon
import sectonone.droidsoft.ap.compose.KTILinearProgressIndicator
import sectonone.droidsoft.ap.compose.KTITextNew
import sectonone.droidsoft.ap.compose.VerticalSpacer
import sectonone.droidsoft.ap.compose.prettyPrint
import sectonone.droidsoft.ap.model.InterviewHistorySummary
import sectonone.droidsoft.ap.model.UIHomeScreenSection
import sectonone.droidsoft.ap.theme.ktiColors

enum class InterviewHistorySummaryVariant { GridSingleRow, GridTwoRows, Column; }

val interviewSummaryCardSize = 164.dp

@Composable
fun InterviewHistorySummaryLayout(
    uiState: UIHomeScreenSection.InterviewHistorySummaryUI,
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
            KTITextNew(
                "See all",
                fontSize = 12.sp,
                color = ktiColors.textVariant2,
                modifier = Modifier.clickable { onSeeAllInterviewsClick.invoke() })
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
                                InterviewSummaryCard(item = uiState.items[index], onClick = {}, variant = variant)
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
                                    InterviewSummaryCard(uiState.items[index * 2], {}, variant)
                                }
                                // Second item in the row
                                if ((index * 2) + 1 < itemCount) {
                                    InterviewSummaryCard(uiState.items[(index * 2) + 1], {}, variant)
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