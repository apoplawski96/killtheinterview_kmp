package sectonone.droidsoft.ap.screens.interviewSetup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import sectonone.droidsoft.ap.compose.KTIButtonShared
import sectonone.droidsoft.ap.compose.KTICardContainer
import sectonone.droidsoft.ap.compose.KTICardItem
import sectonone.droidsoft.ap.compose.KTICardVariant
import sectonone.droidsoft.ap.compose.KTIGridWithCards
import sectonone.droidsoft.ap.compose.KTITextNew
import sectonone.droidsoft.ap.compose.KTITopAppBar
import sectonone.droidsoft.ap.compose.VerticalSpacer
import sectonone.droidsoft.ap.di.getScreenModel
import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.screens.interviewCurated.InterviewChatScreen
import sectonone.droidsoft.ap.screens.interviewSetup.model.SelectableCategory
import sectonone.droidsoft.ap.theme.ktiColors
import sectonone.droidsoft.ap.theme.kti_accent
import sectonone.droidsoft.ap.theme.kti_grey

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
                        categories = categoriesState.filter { it.isSelected }.map { it.categoryV2 }
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
    Scaffold(
        topBar = { KTITopAppBar(isNested = true, title = "Select categories") },
        backgroundColor = ktiColors.backgroundSurface,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            KTIGridWithCards(
                items = categories.map { category: SelectableCategory ->
                    KTICardItem(
                        value = category,
                        label = category.categoryV2.displayName,
                    )
                },
                onClick = onCategoryClick,
                variant = KTICardVariant.WithImageCoverSelectable,
            )
            val isActive = categories.any { it.isSelected }
            AnimatedVisibility(visible = isActive) {
                Column(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    KTIButtonShared(
                        label = "Go to interview",
                        labelColor = ktiColors.onSecondary,
                        backgroundColor = kti_accent,
                        onClick = onGoToInterviewClick,
                        enabled = isActive,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
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
    KTICardContainer(
        onClick = { onCategoryClick.invoke(item) },
//        borderColor = borderColor,
        height = 92.dp,
        backgroundColor = if (item.isSelected.not()) ktiColors.backgroundSurfaceVariant else ktiColors.secondary,
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxSize(),
        ) {
            KTITextNew(
                text = item.categoryV2.displayName,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = if (item.isSelected.not()) ktiColors.textMain else ktiColors.onSecondary,
                modifier = Modifier.weight(8f).align(Alignment.Bottom)
            )
        }
    }
}