package sectonone.droidsoft.ap.screens.subcategories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import sectonone.droidsoft.ap.compose.GridVariant
import sectonone.droidsoft.ap.compose.KTIBackgroundSurface
import sectonone.droidsoft.ap.compose.KTICardItem
import sectonone.droidsoft.ap.compose.KTICircularProgressIndicator
import sectonone.droidsoft.ap.compose.KTIGridWithCards
import sectonone.droidsoft.ap.compose.KTITopAppBar
import sectonone.droidsoft.ap.di.getScreenModel
import sectonone.droidsoft.ap.model.SubCategory
import sectonone.droidsoft.ap.model.TopCategory
import sectonone.droidsoft.ap.screens.list.QuestionsListScreen

internal class SubCategoriesScreen(private val topCategory: TopCategory?) : Screen {

    @Composable
    override fun Content() {
        val viewModel: SubCategoriesScreenModel = getScreenModel()
        val navigator = LocalNavigator.currentOrThrow

        if (topCategory == null) return

        val viewState = viewModel.state.collectAsState().value
        val lazyGridState = rememberLazyGridState()

        LaunchedEffect(null) {
            viewModel.initialize(topCategory)
        }

        SubCategoriesScreenContent(
            state = viewState,
            onClick = { subCategory ->
                navigator.push(
                    QuestionsListScreen(topCategory, subCategory)
                )
            },
            lazyGridState = lazyGridState,
            topCategory = topCategory,
        )
    }
}
@Composable
fun SubCategoriesScreenContent(
    state: SubCategoriesScreenModel.ViewState,
    onClick: (SubCategory?) -> Unit,
    lazyGridState: LazyGridState,
    topCategory: TopCategory,
) {
    KTIBackgroundSurface {
        when (state) {
            is SubCategoriesScreenModel.ViewState.Loading -> {
                KTICircularProgressIndicator()
            }

            is SubCategoriesScreenModel.ViewState.SubCategoriesLoaded -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    KTITopAppBar(title = "${topCategory.displayName} categories")
                    KTIGridWithCards(
                        items = state.categories.map { subCategory: SubCategory ->
                            KTICardItem(
                                value = subCategory,
                                label = subCategory.displayName
                            )
                        },
                        onClick = onClick,
                        state = lazyGridState,
                        variant = GridVariant.SUB_CATEGORY,
                    )
                }
            }
        }
    }
}