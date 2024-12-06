package sectonone.droidsoft.ap.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
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
import sectonone.droidsoft.ap.compose.KTICardWithIllustration
import sectonone.droidsoft.ap.compose.KTITextNew
import sectonone.droidsoft.ap.compose.KTITopAppBar
import sectonone.droidsoft.ap.compose.KTIVerticalSpacer
import sectonone.droidsoft.ap.compose.getRandomUniqueEnumValues
import sectonone.droidsoft.ap.di.getScreenModel
import sectonone.droidsoft.ap.model.Category
import sectonone.droidsoft.ap.model.UIHomeScreenSection
import sectonone.droidsoft.ap.model.HomeScreenMenuItem
import sectonone.droidsoft.ap.model.interviewSummary
import sectonone.droidsoft.ap.screens.categories.CategoriesListScreen
import sectonone.droidsoft.ap.screens.home.components.InterviewHistorySummaryLayout
import sectonone.droidsoft.ap.screens.home.components.RecommendedCategoriesLayout
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
                    HomeScreenMenuItem.CHAT_INTERVIEW -> {
                        navigator.push(
                            InterviewSetupScreen
                        )
                    }

                    HomeScreenMenuItem.QUESTIONS_CATEGORIES -> {
                        navigator.push(
                            CategoriesListScreen
                        )
                    }
                }
            },
        )
    }
}

@Composable
fun HomeScreenContent(
    state: HomeScreenModel.ViewState,
    onMenuItemClicked: (HomeScreenMenuItem) -> Unit,
) {
    Scaffold(
        topBar = { KTITopAppBar(isNested = false) },
        backgroundColor = KTITheme.colors.backgroundSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HelloSection()
            KTIVerticalSpacer(height = 32.dp)
            when (state) {
                is HomeScreenModel.ViewState.HomeItems -> {
                    HomeScreenFeedSection(
                        feed = state.items,
                        onMenuItemClicked = onMenuItemClicked,
                    )
                }

                is HomeScreenModel.ViewState.Loading -> {
                    CircularProgressIndicator()
                }
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
        KTIVerticalSpacer(2.dp)
        KTITextNew(
            text = "It's time to prepare for your next interview!",
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = KTITheme.colors.textVariant2
        )
    }
}

@Composable
private fun HomeScreenFeedSection(
    feed: List<UIHomeScreenSection>,
    onMenuItemClicked: (HomeScreenMenuItem) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        feed.forEach { feedItem: UIHomeScreenSection ->
            when (feedItem) {
                is UIHomeScreenSection.MenuItems -> {
                    MenuItems(feedItem.items, onMenuItemClicked)
                }
                is UIHomeScreenSection.InterviewHistorySummary -> {
                    InterviewHistorySummaryLayout(feedItem)
                }
                is UIHomeScreenSection.RandomBookmarkedQuestion -> {}
                is UIHomeScreenSection.BookmarkedCategories -> TODO()
                is UIHomeScreenSection.BookmarkedQuestions -> TODO()
                is UIHomeScreenSection.RecommendedCategoriesCarousel -> {
                    RecommendedCategoriesLayout(feedItem)
                }
                is UIHomeScreenSection.DailyChallenge -> TODO()
                is UIHomeScreenSection.RandomQuestionsCarousel -> TODO()
                is UIHomeScreenSection.RecommendedCategory -> TODO()
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
                    HomeScreenMenuItem.CHAT_INTERVIEW -> Icons.Default.CoPresent
                    HomeScreenMenuItem.QUESTIONS_CATEGORIES -> Icons.Default.AccountTree
                }
            )
            KTIVerticalSpacer(height = 12.dp)
        }
    }
}

val interviewsSummaryMock = listOf(
    interviewSummary(answeredCount = 2, failedCount = 10, categories = getRandomUniqueEnumValues<Category>(3)),
    interviewSummary(answeredCount = 20, failedCount = 10, categories = getRandomUniqueEnumValues<Category>(2)),
    interviewSummary(answeredCount = 15, failedCount = 10, categories = getRandomUniqueEnumValues<Category>(3)),
    interviewSummary(answeredCount = 5, failedCount = 10, categories = getRandomUniqueEnumValues<Category>(4)),
    interviewSummary(answeredCount = 8, failedCount = 1, categories = getRandomUniqueEnumValues<Category>(2)),
    interviewSummary(answeredCount = 2, failedCount = 10, categories = getRandomUniqueEnumValues<Category>(2)),
)

val homeScreenMock = HomeScreenModel.ViewState.HomeItems(
    items = listOf(
        UIHomeScreenSection.MenuItems(
            items = listOf(HomeScreenMenuItem.CHAT_INTERVIEW, HomeScreenMenuItem.QUESTIONS_CATEGORIES)
        ),
        UIHomeScreenSection.InterviewHistorySummary(
            items = interviewsSummaryMock
        ),
        UIHomeScreenSection.RecommendedCategoriesCarousel(
            items = listOf(
                Category.Android, Category.Compose, Category.AndroidSecurity, Category.DesignPatterns, Category.Kotlin
            )
        )
    )
)