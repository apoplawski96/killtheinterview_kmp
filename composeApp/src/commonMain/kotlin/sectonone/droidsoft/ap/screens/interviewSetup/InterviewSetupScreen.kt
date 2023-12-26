package sectonone.droidsoft.ap.screens.interviewSetup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import sectonone.droidsoft.ap.compose.GridVariant
import sectonone.droidsoft.ap.compose.KTIBoxWithGradientBackground
import sectonone.droidsoft.ap.compose.KTIButton
import sectonone.droidsoft.ap.compose.KTICardContainer
import sectonone.droidsoft.ap.compose.KTICardItem
import sectonone.droidsoft.ap.compose.KTICircularProgressIndicator
import sectonone.droidsoft.ap.compose.KTIGridWithCards
import sectonone.droidsoft.ap.compose.KTITextNew
import sectonone.droidsoft.ap.compose.KTITopAppBar
import sectonone.droidsoft.ap.compose.KTIVerticalSpacer
import sectonone.droidsoft.ap.di.getScreenModel
import sectonone.droidsoft.ap.model.TopCategory
import sectonone.droidsoft.ap.screens.categories.CategoriesScreenModel
import sectonone.droidsoft.ap.screens.interviewCurated.InterviewChatScreen
import sectonone.droidsoft.ap.screens.interviewSetup.model.SelectableCategory
import sectonone.droidsoft.ap.theme.KTITheme
import sectonone.droidsoft.ap.theme.*

internal object InterviewSetupScreen : Screen {

    @Composable
    override fun Content() {

        val screenModel: InterviewSetupScreenModel = getScreenModel()
        val navigator = LocalNavigator.currentOrThrow

        val categoriesState = screenModel.viewState.collectAsState().value

        InterviewSetupScreenContent(
            categories = categoriesState,
            onCategoryClick = { screenModel.toggleCategory(it) },
            lazyGridState = rememberLazyGridState(),
            onGoToInterviewClick = {
                navigator.push(
                    InterviewChatScreen(
                        categories = categoriesState.filter { it.isSelected }.map { it.category }
                    )
                )
            }
        )
    }
}

@Composable
private fun InterviewSetupScreenContent(
    categories: List<SelectableCategory>,
    onCategoryClick: (SelectableCategory) -> Unit,
    onGoToInterviewClick: () -> Unit,
    lazyGridState: LazyGridState,
) {
    KTIBoxWithGradientBackground {
        Column(
            modifier = Modifier.fillMaxSize().background(KTITheme.colors.backgroundSurface),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            KTITopAppBar(title = "Select categories", iconsSection = { })
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    state = lazyGridState,
                    modifier = Modifier.weight(10f),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    item { KTIVerticalSpacer(height = 8.dp) }
                    item { KTIVerticalSpacer(height = 8.dp) }
                    categories.forEachIndexed { _, selectableCategory ->
                        item {
                            SelectableCategoryCard(
                                item = selectableCategory,
                                onCategoryClick = onCategoryClick
                            )
                        }
                        item { KTIVerticalSpacer(height = 12.dp) }
                    }
                }
                Column(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    val isActive = categories.any { it.isSelected }
                    KTIButton(
                        label = "Go to interview",
                        labelColor = if (isActive) kti_softblack else kti_light_grey,
                        backgroundColor = kti_accent,
                        onClick = onGoToInterviewClick,
                        enabled = isActive
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectableCategoryCard(
    item: SelectableCategory,
    onCategoryClick: (SelectableCategory) -> Unit,
) {
    val borderColor = if (item.isSelected) kti_accent else kti_grey
//    Card(
//        shape = RoundedCornerShape(size = 16.dp),
//        backgroundColor = Color.Transparent,
//        border = BorderStroke(width = 2.dp, color = borderColor),
//        modifier = Modifier
//            .clickableNoRipple { onCategoryClick.invoke(item) }
//            .heightIn(min = 96.dp)
//            .fillMaxWidth()
//            .padding(4.dp),
//        elevation = 0.dp
//    ) {
//        Row(
//            modifier = Modifier.padding(12.dp).fillMaxSize(),
//        ) {
//            KTITextNew(
//                text = item.category.displayName,
//                maxLines = 2,
//                overflow = TextOverflow.Ellipsis,
//                fontWeight = FontWeight.Normal,
//                fontSize = 16.sp,
//                color = kti_softblack,
//                modifier = Modifier.weight(8f).align(Alignment.Bottom)
//            )
//            Box(Modifier.weight(1.5f).align(Alignment.Top)) {
//                Box(
//                    modifier = Modifier
//                        .size(24.dp)
//                        .clip(CircleShape)
//                        .border(
//                            width = if (item.isSelected) 8.dp else 1.dp,
//                            color = if (item.isSelected) kti_accent else kti_grey
//                        )
//                        .align(Alignment.TopCenter)
//                )
//            }
//        }
//    }

    KTICardContainer(onClick = { onCategoryClick.invoke(item) }, borderColor = borderColor, height = 92.dp) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxSize(),
        ) {
            KTITextNew(
                text = item.category.displayName,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = kti_softblack,
                modifier = Modifier.weight(8f).align(Alignment.Bottom)
            )
            Box(Modifier.weight(1.5f).align(Alignment.Top)) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .border(
                            width = if (item.isSelected) 8.dp else 1.dp,
                            color = if (item.isSelected) kti_accent else kti_grey
                        )
                        .align(Alignment.TopCenter)
                )
            }
        }
    }
}

@Composable
private fun InterviewSetupScreenContent(
    state: CategoriesScreenModel.ViewState,
    onCategoryClick: (TopCategory?) -> Unit,
    lazyGridState: LazyGridState
) {
    KTIBoxWithGradientBackground {
        when (state) {
            is CategoriesScreenModel.ViewState.CategoriesLoaded -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    KTITopAppBar(title = "Categories")
                    KTIGridWithCards(
                        items = state.categories.map { topCategory ->
                            KTICardItem(
                                value = topCategory,
                                label = topCategory.displayName
                            )
                        },
                        onClick = onCategoryClick,
                        state = lazyGridState,
                        variant = GridVariant.TOP_CATEGORY,
                    )
                    KTIVerticalSpacer(height = 64.dp)
//                    KTIIllustration(imageResource = SharedRes.images.undraw_scientist_ft0o)
                }
            }

            is CategoriesScreenModel.ViewState.Loading -> {
                KTICircularProgressIndicator()
            }
        }
    }
}