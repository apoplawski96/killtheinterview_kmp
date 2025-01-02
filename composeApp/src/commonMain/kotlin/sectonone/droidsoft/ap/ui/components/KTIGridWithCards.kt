package sectonone.droidsoft.ap.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.screens.interviewSetup.model.SelectableCategory
import sectonone.droidsoft.ap.theme.*

data class KTICardItem<T>(
    val value: T,
    val label: String,
    val cardColor: Color? = null,
    val assetResourcePath: String? = null,
)

enum class KTICardVariant {
    Simple,
    WithImageCover,
    WithImageCoverSelectable;
}

@Composable
fun <T> KTIGridWithCards(
    items: List<KTICardItem<T>>,
    onClick: (T) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState(),
    variant: KTICardVariant = KTICardVariant.Simple,
    columns: GridCells = GridCells.Fixed(2),
) {
    LazyVerticalGrid(
        columns = columns,
        modifier = modifier then Modifier.padding(start = 8.dp, end = 8.dp),
        state = state,
        content = {
            item { VerticalSpacer(height = 8.dp) }
            item { VerticalSpacer(height = 8.dp) }
            this.itemsIndexed(items = items) { index, item ->
                when (variant) {
                    KTICardVariant.Simple -> {
                        KTICard(
                            item = item.applyColor(index),
                            onClick = onClick,
                            padding = PaddingValues(all = 4.dp),
                            textColor = kti_softwhite,
                            fontWeight = FontWeight.W500
                        )
                    }

                    KTICardVariant.WithImageCover -> {
                        if (item.value is Category) {
                            CategoryWithCoverCard(
                                item.value,
                                padding = PaddingValues(8.dp),
                                onClick = { onClick.invoke(item.value) })
                        }
                    }

                    KTICardVariant.WithImageCoverSelectable -> {
                        if (item.value is SelectableCategory) {
                            SelectableCategoryWithCoverCard(
                                item.value,
                                padding = PaddingValues(8.dp),
                                onClick = { onClick.invoke(item.value) }
                            )
                        }
                    }
                }
            }
            if (items.count() % 2 == 0) {
                item { VerticalSpacer(height = 8.dp) }
                item { VerticalSpacer(height = 8.dp) }
            } else {
                item { VerticalSpacer(height = 8.dp) }
            }
        }
    )
}

@Composable
private fun <T> KTICard(
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

private val cardMinHeight = 86.dp

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