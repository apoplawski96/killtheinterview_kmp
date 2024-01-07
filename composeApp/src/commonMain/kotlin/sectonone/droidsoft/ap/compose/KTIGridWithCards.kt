package sectonone.droidsoft.ap.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sectonone.droidsoft.ap.theme.*

data class KTICardItem<T>(
    val value: T,
    val label: String,
    val cardColor: Color? = null,
)

enum class GridVariant { TOP_CATEGORY, SUB_CATEGORY; }

@Composable
fun <T> KTIGridWithCards(
    items: List<KTICardItem<T>>,
    onClick: (T?) -> Unit,
    state: LazyGridState,
    variant: GridVariant
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        state = state,
        content = {
            item { KTIVerticalSpacer(height = 8.dp) }
            item { KTIVerticalSpacer(height = 8.dp) }
            if (variant == GridVariant.SUB_CATEGORY) {
                item {
                    AllCard(onClick = { onClick(null) })
                }
            }
            this.itemsIndexed(items = items) { index, item ->
                KTICard(
                    item = item.applyColor(index),
                    onClick = onClick,
                    padding = PaddingValues(all = 4.dp),
                    textColor = kti_softwhite,
                    fontWeight = FontWeight.W500
                )
            }
            if (items.count() % 2 == 0) {
                item { KTIVerticalSpacer(height = 8.dp) }
                item { KTIVerticalSpacer(height = 8.dp) }
            } else {
                item { KTIVerticalSpacer(height = 8.dp) }
            }
        }
    )
}

@Composable
fun <T> KTICard(
    item: KTICardItem<T>,
    onClick: (T) -> Unit,
    padding: PaddingValues,
    fontWeight: FontWeight = FontWeight(400),
    backgroundColor: Color = kti_softwhite,
    textColor: Color = kti_softblack,
) {
    Card(
        shape = RoundedCornerShape(size = 8.dp),
        backgroundColor = item.cardColor ?: backgroundColor,
//        border = BorderStroke(width = 0.5.dp, color = kti_grayish_light.copy(alpha = 0.2f)),
        modifier = Modifier
            .clickableNoRipple { onClick.invoke(item.value) }
            .padding(padding)
            .heightIn(min = 92.dp)
            .fillMaxWidth(),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom,
        ) {
            KTITextNew(
                text = item.label,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = fontWeight,
                fontSize = 16.sp,
                color = textColor
            )
        }
    }
}

private val cardMinHeight = 112.dp

@Composable
fun <T> KTICardWithIllustration(
    item: KTICardItem<T>,
    onClick: (T) -> Unit,
    imageResource: ImageVector,
    fontWeight: FontWeight = FontWeight(400),
    backgroundColor: Color = ktiColors.backgroundSurfaceVariant,
    textColor: Color = ktiColors.textMain,
    labelSize: TextUnit = 16.sp,
) {
    Card(
        shape = RoundedCornerShape(size = 12.dp),
        backgroundColor = backgroundColor,
        elevation = 2.dp,
        modifier = Modifier
            .clickable { onClick.invoke(item.value) }
            .fillMaxWidth()
            .heightIn(cardMinHeight),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(cardMinHeight)
        ) {
            KTITextNew(
                text = item.label,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = fontWeight,
                fontSize = labelSize,
                color = textColor,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp).weight(2f).align(Alignment.Bottom)
            )
            Box(
                modifier = Modifier
                    .weight(1.2f)
                    .heightIn(cardMinHeight)
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                KTIIcon(imageResource, size = 128.dp, tint = ktiColors.textMain)
            }
        }
    }
}

@Composable
fun <T> KTICardSmall(
    item: KTICardItem<T>,
    onClick: (T) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(size = 12.dp),
        backgroundColor = item.cardColor ?: kti_softwhite,
        modifier = Modifier
            .clickableNoRipple { onClick.invoke(item.value) }
            .padding(PaddingValues(end = 8.dp, top = 8.dp, bottom = 8.dp))
            .size(84.dp),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 2.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            KTITextNew(
                text = item.label,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight(500),
                fontSize = 12.sp,
                color = white,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun <T> KTICardSmallWithUnderText(
    item: KTICardItem<T>,
    onClick: (T) -> Unit,
    padding: PaddingValues = PaddingValues(),
) {
    Column(
        modifier = Modifier
            .padding(end = 8.dp, top = 8.dp, bottom = 8.dp)
            .width(84.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Card(
            shape = RoundedCornerShape(size = 12.dp),
            backgroundColor = item.cardColor ?: kti_softwhite,
//        border = BorderStroke(width = 0.5.dp, color = kti_grayish_light.copy(alpha = 0.2f)),
            modifier = Modifier
                .clickableNoRipple { onClick.invoke(item.value) }
                .padding(padding)
                .size(78.dp),
            elevation = 2.dp
        ) {

        }
        KTIVerticalSpacer(height = 8.dp)
        KTITextNew(
            text = item.label,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight(300),
            fontSize = 12.sp,
            color = ktiColors.textVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun AllCard(onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(size = 8.dp),
        backgroundColor = kti_grayish,
        border = BorderStroke(width = 0.5.dp, color = kti_grayish_light.copy(alpha = 0.2f)),
        modifier = Modifier
            .clickableNoRipple { onClick.invoke() }
            .padding(4.dp)
            .heightIn(min = 96.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom,
        ) {
            KTITextNew(
                text = "All",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight(400),
                fontSize = 16.sp,
                color = kti_accent
            )
        }
    }
}

@Composable
fun KTICardContainer(
    onClick: () -> Unit = { },
    backgroundColor: Color = kti_softwhite,
    borderColor: Color = Color.Transparent,
    height: Dp = cardMinHeight,
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(size = 16.dp),
        backgroundColor = backgroundColor,
        elevation = 2.dp,
        border = BorderStroke(1.dp, color = borderColor),
        modifier = Modifier
            .clickableNoRipple { onClick.invoke() }
            .fillMaxWidth()
            .heightIn(height)
            .padding(4.dp),
    ) {
        content.invoke()
    }
}