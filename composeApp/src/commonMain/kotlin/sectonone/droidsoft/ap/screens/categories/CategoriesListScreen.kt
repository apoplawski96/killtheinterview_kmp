package sectonone.droidsoft.ap.screens.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import sectonone.droidsoft.ap.data.di.getScreenModel
import sectonone.droidsoft.ap.data.model.Category
import sectonone.droidsoft.ap.screens.questions.QuestionsScreen
import sectonone.droidsoft.ap.ui.components.KTIBackgroundSurface
import sectonone.droidsoft.ap.ui.components.KTICardItem
import sectonone.droidsoft.ap.ui.components.KTICardVariant
import sectonone.droidsoft.ap.ui.components.KTIGridWithCards
import sectonone.droidsoft.ap.ui.components.KTITopAppBar

internal object CategoriesListScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<CategoriesListScreenModel>()
        val navigator = LocalNavigator.currentOrThrow
        val categories by screenModel.state.collectAsState()

        CategoriesListScreenContent(
            onClick = { category ->
                navigator.push(QuestionsScreen(listOf(category)))
            },
            items = categories
        )
    }
}

@Composable
private fun CategoriesListScreenContent(
    onClick: (Category) -> Unit,
    items: List<CategoryListItem>?,
) {
    KTIBackgroundSurface {
        Column(modifier = Modifier.fillMaxSize()) {
            if (items != null) {
                KTITopAppBar(title = "Categories")
                KTIGridWithCards(
                    items = items.map {
                        KTICardItem(
                            value = it,
                            label = it.category.item.displayName,
                        )
                    },
                    onClick = { onClick.invoke(it.category.item) },
                    variant = KTICardVariant.WithImageCover,
                )
            } else {
                // error
            }
        }
    }
}