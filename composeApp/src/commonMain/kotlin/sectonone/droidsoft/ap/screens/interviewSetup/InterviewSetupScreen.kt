package sectonone.droidsoft.ap.screens.interviewSetup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import sectonone.droidsoft.ap.ui.components.KTIButtonShared
import sectonone.droidsoft.ap.ui.components.KTICardItem
import sectonone.droidsoft.ap.ui.components.KTICardVariant
import sectonone.droidsoft.ap.ui.components.KTIGridWithCards
import sectonone.droidsoft.ap.ui.components.KTITopAppBar
import sectonone.droidsoft.ap.data.di.getScreenModel
import sectonone.droidsoft.ap.screens.interviewCurated.InterviewChatScreen
import sectonone.droidsoft.ap.screens.interviewSetup.model.SelectableCategory
import sectonone.droidsoft.ap.theme.ktiColors
import sectonone.droidsoft.ap.theme.kti_accent
import sectonone.droidsoft.ap.ui.components.KTITextNew

internal object InterviewSetupScreen : Screen {

    @Composable
    override fun Content() {

        val screenModel = getScreenModel<InterviewSetupScreenModel>()
        val navigator = LocalNavigator.currentOrThrow

        val categoriesState = screenModel.viewState.collectAsState().value

        InterviewSetupScreenContent(
            categories = categoriesState,
            onCategoryClick = { screenModel.toggleCategory(it) },
            lazyGridState = rememberLazyGridState(),
            onGoToInterviewClick = {
                if (categoriesState == null) return@InterviewSetupScreenContent
                navigator.push(
                    InterviewChatScreen(
                        categories = categoriesState.filter { it.isSelected }.map { it.category.item }
                    )
                )
            }
        )
    }
}

@Composable
private fun InterviewSetupScreenContent(
    categories: List<SelectableCategory>?,
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
            if (categories != null) {
                KTIGridWithCards(
                    items = categories.map { category: SelectableCategory ->
                        KTICardItem(
                            value = category,
                            label = category.category.item.displayName,
                        )
                    },
                    onClick = onCategoryClick,
                    variant = KTICardVariant.WithImageCoverSelectable,
                    modifier = Modifier.weight(10f)
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
            } else {
                KTITextNew("ERROR")
            }
        }
    }
}