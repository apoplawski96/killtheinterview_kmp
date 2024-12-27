package sectonone.droidsoft.ap.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sectonone.droidsoft.ap.compose.CategoryWithCoverCard
import sectonone.droidsoft.ap.compose.KTITextNew
import sectonone.droidsoft.ap.compose.VerticalSpacer
import sectonone.droidsoft.ap.model.UIHomeScreenSection
import sectonone.droidsoft.ap.theme.ktiColors

@Composable
fun RecommendedCategoriesLayout(
    uiState: UIHomeScreenSection.RecommendedCategoriesCarousel,
    variant: InterviewHistorySummaryVariant = InterviewHistorySummaryVariant.GridSingleRow,
) {
    val itemsCount = uiState.items.count()

    Column {
        VerticalSpacer(12.dp)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            KTITextNew("Recommended for you", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            KTITextNew("Browse more", fontSize = 12.sp, color = ktiColors.textVariant2)
        }
        VerticalSpacer(8.dp)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            when(variant) {
                InterviewHistorySummaryVariant.GridSingleRow -> {
                    items(itemsCount) { index ->
                        Row {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                CategoryWithCoverCard(uiState.items[index])
                            }
                        }
                    }
                }
                InterviewHistorySummaryVariant.GridTwoRows -> {
                    items(itemsCount / 2) { index ->
                        Row {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // First item in the row
                                if (index * 2 < itemsCount) {
                                    CategoryWithCoverCard(uiState.items[index])                                }
                                // Second item in the row
                                if ((index * 2) + 1 < itemsCount) {
                                    CategoryWithCoverCard(uiState.items[index])                                }
                            }
                        }
                    }
                }

                InterviewHistorySummaryVariant.Column -> {

                }
            }
        }
    }
}