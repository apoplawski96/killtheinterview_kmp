package sectonone.droidsoft.ap.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import sectonone.droidsoft.ap.compose.KTITextNew
import sectonone.droidsoft.ap.compose.KTIVerticalSpacer
import sectonone.droidsoft.ap.compose.rememberRandomCardColor
import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.UIHomeScreenSection
import sectonone.droidsoft.ap.theme.ktiColors
import sectonone.droidsoft.ap.theme.white

private val recommendedCategoryCardWidth = 164.dp
private val recommendedCategoryCardHeight = 96.dp

@Composable
fun RecommendedCategoriesLayout(
    uiState: UIHomeScreenSection.RecommendedCategoriesCarousel,
    variant: InterviewHistorySummaryVariant = InterviewHistorySummaryVariant.SingleRow,
) {
    val itemsCount = uiState.items.count()

    Column {
        KTIVerticalSpacer(12.dp)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            KTITextNew("Recommended for you", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            KTITextNew("Browse more", fontSize = 12.sp, color = ktiColors.textVariant2)
        }
        KTIVerticalSpacer(8.dp)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            when(variant) {
                InterviewHistorySummaryVariant.SingleRow -> {
                    items(itemsCount) { index ->
                        Row {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                ItemCard(uiState.items[index])
                            }
                        }
                    }
                }
                InterviewHistorySummaryVariant.TwoRows -> {
                    items(itemsCount / 2) { index ->
                        Row {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // First item in the row
                                if (index * 2 < itemsCount) {
                                    ItemCard(uiState.items[index])                                }
                                // Second item in the row
                                if ((index * 2) + 1 < itemsCount) {
                                    ItemCard(uiState.items[index])                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemCard(category: Category) {
    Card(
        modifier = Modifier
            .size(width = recommendedCategoryCardWidth, height = recommendedCategoryCardHeight)
            .clip(RoundedCornerShape(16.dp)),
        elevation = 4.dp,
        backgroundColor = rememberRandomCardColor(),
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Category text at the top left
            KTITextNew(
                text = category.displayName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp),
                color = white
            )

            // Tilted image with adjustments
            Card(
                elevation = 6.dp, // Add elevation to the tilted image
                shape = RoundedCornerShape(8.dp), // Shape for the card wrapping the image
                modifier = Modifier
                    .align(Alignment.CenterEnd) // Align to the right
                    .size(64.dp) // Image size
                    .graphicsLayer {
                        rotationZ = 30f // Tilt the image 30 degrees to the right
                        translationX = 20.dp.toPx() // Move the image slightly to the right
                        translationY = 10.dp.toPx()
                    }
            ) {
                Image(
                    painter = painterResource(category.imageFile),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize() // Fill the size of the Card
                )
            }
        }
    }
}
