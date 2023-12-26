package sectonone.droidsoft.ap.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.CoPresent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import sectonone.droidsoft.ap.compose.KTICardItem
import sectonone.droidsoft.ap.compose.KTICardSmallWithUnderText
import sectonone.droidsoft.ap.compose.KTICardWithIllustration
import sectonone.droidsoft.ap.compose.KTIHorizontalSpacer
import sectonone.droidsoft.ap.compose.KTITextNew
import sectonone.droidsoft.ap.compose.KTITopAppBar
import sectonone.droidsoft.ap.compose.KTIVerticalSpacer
import sectonone.droidsoft.ap.compose.applyColor
import sectonone.droidsoft.ap.di.getScreenModel
import sectonone.droidsoft.ap.model.HomeScreenFeedItem
import sectonone.droidsoft.ap.model.HomeScreenMenuItem
import sectonone.droidsoft.ap.model.SubCategory
import sectonone.droidsoft.ap.model.TopCategory
import sectonone.droidsoft.ap.screens.categories.CategoriesScreen
import sectonone.droidsoft.ap.screens.interviewSetup.InterviewSetupScreen
import sectonone.droidsoft.ap.theme.KTITheme

internal object HomeScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: HomeScreenModel = getScreenModel()

        val viewState = screenModel.viewState.collectAsState().value

        LaunchedEffect(null) {
            screenModel.initialize()
        }

        HomeScreenContent(
            state = viewState,
            onMenuItemClicked = { item ->
                when (item) {
                    HomeScreenMenuItem.AI_INTERVIEW -> {
                        navigator.push(
                            InterviewSetupScreen
                        )
                    }

                    HomeScreenMenuItem.QUESTIONS_CATEGORIES -> {
                        navigator.push(
                            CategoriesScreen
                        )
                    }
                }
            },
            onSubCategoryClick = { subCategory -> }
        )
    }
}

@Composable
private fun HomeScreenContent(
    state: HomeScreenModel.ViewState,
    onMenuItemClicked: (HomeScreenMenuItem) -> Unit,
    onSubCategoryClick: (SubCategory) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(KTITheme.colors.backgroundSurface)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        KTITopAppBar(isNested = false)
        HelloSection()
        KTIVerticalSpacer(height = 32.dp)
        IllustrationSection()
        when (state) {
            is HomeScreenModel.ViewState.HomeItems -> {
                HomeScreenFeedSection(
                    feed = state.items,
                    onMenuItemClicked = onMenuItemClicked,
                    onSubCategoryClick = onSubCategoryClick
                )
            }

            is HomeScreenModel.ViewState.Loading -> {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun HelloSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        KTITextNew(
            text = "Hello candidate",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = KTITheme.colors.textMain
        )
        KTITextNew(
            text = "It's time to prepare for your next interview!",
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = KTITheme.colors.textVariant2
        )
    }
}

@Composable
private fun IllustrationSection() {
//    KTIIllustration(
//        imageResource = SharedRes.images.undraw_certificate_re_yadi,
//        modifier = Modifier.height(256.dp)
//    )
}

@Composable
private fun HomeScreenFeedSection(
    feed: List<HomeScreenFeedItem>,
    onMenuItemClicked: (HomeScreenMenuItem) -> Unit,
    onSubCategoryClick: (SubCategory) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        feed.forEach { feedItem ->
            when (feedItem) {
                is HomeScreenFeedItem.MenuItems -> {
                    MenuItems(items = feedItem.items, onItemClicked = onMenuItemClicked)
                }

                is HomeScreenFeedItem.RandomSubCategoriesCarousel -> {
                    RandomSubCategoriesCarousel(
                        onSubCategoryClick = onSubCategoryClick,
                        subCategories = feedItem.subCategories,
                        topCategory = feedItem.topCategory
                    )
                }

                is HomeScreenFeedItem.LastLearnedSubCategoriesCarousel -> {}
                is HomeScreenFeedItem.LastLearnedSubCategory -> {}
                is HomeScreenFeedItem.RandomBookmarkedQuestion -> {}
            }
        }
    }
}

@Composable
private fun MenuItems(
    items: List<HomeScreenMenuItem>,
    onItemClicked: (HomeScreenMenuItem) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
    ) {
        items.forEach { homeItem ->
            KTICardWithIllustration(
                item = KTICardItem(value = homeItem, label = homeItem.displayName),
                onClick = onItemClicked,
                fontWeight = FontWeight.W500,
                imageResource = when (homeItem) {
                    HomeScreenMenuItem.AI_INTERVIEW -> Icons.Default.CoPresent
                    HomeScreenMenuItem.QUESTIONS_CATEGORIES -> Icons.Default.AccountTree
                }
            )
            KTIVerticalSpacer(height = 12.dp)
        }
    }
}

@Composable
private fun RandomSubCategoriesCarousel(
    onSubCategoryClick: (SubCategory) -> Unit,
    subCategories: List<SubCategory>,
    topCategory: TopCategory,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        KTITextNew(
            text = "Categories for ${topCategory.displayName}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        LazyRow {
            item { KTIHorizontalSpacer(width = 16.dp) }
            itemsIndexed(
                items = subCategories,
                key = { _, subCategory -> subCategory.id }) { index, subCategory ->
                KTICardSmallWithUnderText(
                    item = KTICardItem(
                        value = subCategory,
                        label = subCategory.displayName
                    ).applyColor(index),
                    onClick = onSubCategoryClick
                )
            }
            item { KTIHorizontalSpacer(width = 16.dp) }
        }
    }
}

