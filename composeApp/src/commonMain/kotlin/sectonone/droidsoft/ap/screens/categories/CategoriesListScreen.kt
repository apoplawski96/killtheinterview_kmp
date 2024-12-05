package sectonone.droidsoft.ap.screens.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import sectonone.droidsoft.ap.compose.GridVariant
import sectonone.droidsoft.ap.compose.KTIBackgroundSurface
import sectonone.droidsoft.ap.compose.KTICardItem
import sectonone.droidsoft.ap.compose.KTIGridWithCards
import sectonone.droidsoft.ap.compose.KTITopAppBar
import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.screens.questionsList.ListOfQuestionsScreen

internal object CategoriesListScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val categories = remember { Category.entries }

        CategoriesListScreenContent(
            onClick = { category ->
                if (category != null) { // todo: remove nullability
                    navigator.push(ListOfQuestionsScreen(listOf(category)))
                }
            },
            items = categories
        )
    }
}

@Composable
private fun CategoriesListScreenContent(
    onClick: (Category?) -> Unit,
    items: List<Category>,
) {
    KTIBackgroundSurface {
        Column(modifier = Modifier.fillMaxSize()) {
            KTITopAppBar(title = "Categories")
            KTIGridWithCards(
                items = items.map { category: Category ->
                    KTICardItem(
                        value = category,
                        label = category.displayName
                    )
                },
                onClick = onClick,
//                variant = GridVariant.SUB_CATEGORY,
            )
        }
    }
}