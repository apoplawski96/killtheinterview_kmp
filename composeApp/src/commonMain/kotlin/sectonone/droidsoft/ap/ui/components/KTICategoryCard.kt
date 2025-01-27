package sectonone.droidsoft.ap.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import sectonone.droidsoft.ap.data.model.Category
import sectonone.droidsoft.ap.screens.interviewSetup.model.SelectableCategory
import sectonone.droidsoft.ap.theme.KTITheme
import sectonone.droidsoft.ap.theme.kti_grayish
import sectonone.droidsoft.ap.theme.kti_grayish_variant
import sectonone.droidsoft.ap.theme.white

private val recommendedCategoryCardWidth = 164.dp
private val recommendedCategoryCardHeight = 96.dp

@Composable
fun CategoryWithCoverCard(
    category: Category,
    onClick: () -> Unit = {},
    padding: PaddingValues = PaddingValues(0.dp),
) {
    Card(
        modifier = Modifier
            .size(width = recommendedCategoryCardWidth, height = recommendedCategoryCardHeight)
            .clip(RoundedCornerShape(16.dp))
            .padding(padding)
            .clickable { onClick.invoke() },
        elevation = 4.dp,
        backgroundColor = rememberRandomCardColor(),
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            KTITextNew(
                text = category.displayName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp),
                color = white
            )
            Card(
                elevation = 6.dp,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(64.dp)
                    .graphicsLayer {
                        rotationZ = 30f
                        translationX = 20.dp.toPx()
                        translationY = 10.dp.toPx()
                    }
            ) {
                Image(
                    painter = painterResource(category.imageRes),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun SelectableCategoryWithCoverCard(
    category: SelectableCategory,
    onClick: () -> Unit = {},
    padding: PaddingValues = PaddingValues(0.dp),
) {
    Card(
        modifier = Modifier
            .size(width = recommendedCategoryCardWidth, height = recommendedCategoryCardHeight)
            .clip(RoundedCornerShape(16.dp))
            .padding(padding)
            .clickable { onClick.invoke() },
        elevation = 4.dp,
        backgroundColor = if (!category.isSelected) {
            KTITheme.colors.backgroundSurfaceVariant
        } else {
            KTITheme.colors.secondary
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            KTITextNew(
                text = category.category.displayName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp),
                color = if (category.isUnlocked) white else white.copy(alpha = 0.5f)
            )
            if (category.isUnlocked.not()) {
                Icon(Icons.Default.Lock, null, modifier = Modifier.alpha(0.5f).align(Alignment.Center).size(64.dp))
            }
            Card(
                elevation = 6.dp,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(64.dp)
                    .graphicsLayer {
                        rotationZ = 30f
                        translationX = 20.dp.toPx()
                        translationY = 10.dp.toPx()
                    }
            ) {
                Image(
                    painter = painterResource(category.category.imageRes),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .drawWithContent {
                            drawContent() // Draw the image
                            if (category.isUnlocked.not()) {
                                drawRect(
                                    color = Color.Gray.copy(alpha = 0.5f), // Semi-transparent grey
                                    size = size
                                )
                            }
                        }
                )
            }
        }
    }
}