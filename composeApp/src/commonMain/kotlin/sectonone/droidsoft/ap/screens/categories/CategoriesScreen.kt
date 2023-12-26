package sectonone.droidsoft.ap.screens.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import sectonone.droidsoft.ap.compose.GridVariant
import sectonone.droidsoft.ap.compose.KTIBoxWithGradientBackground
import sectonone.droidsoft.ap.compose.KTICardItem
import sectonone.droidsoft.ap.compose.KTICircularProgressIndicator
import sectonone.droidsoft.ap.compose.KTIGridWithCards
import sectonone.droidsoft.ap.compose.KTITopAppBar
import sectonone.droidsoft.ap.compose.KTIVerticalSpacer
import sectonone.droidsoft.ap.di.getScreenModel
import sectonone.droidsoft.ap.model.TopCategory
import sectonone.droidsoft.ap.screens.subcategories.SubCategoriesScreen

internal object CategoriesScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: CategoriesScreenModel = getScreenModel()
        val navigator = LocalNavigator.currentOrThrow

        val viewState = viewModel.state.collectAsState().value
        val lazyGridState = rememberLazyGridState()

        LaunchedEffect(null) {
            viewModel.initialize()
        }

        CategoriesScreenContent(
            state = viewState,
            lazyGridState = lazyGridState,
            onCategoryClick = { category ->
                if (category is TopCategory) {
                    navigator.push(SubCategoriesScreen(topCategory = category))
                }
            },
        )
    }
}


@Composable
private fun CategoriesScreenContent(
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