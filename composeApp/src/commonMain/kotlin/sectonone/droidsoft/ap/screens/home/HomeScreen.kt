package sectonone.droidsoft.ap.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.School
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import sectonone.droidsoft.ap.ui.components.KTIAvatarWithAnimation
import sectonone.droidsoft.ap.ui.components.KTICardItem
import sectonone.droidsoft.ap.ui.components.KTIIcon
import sectonone.droidsoft.ap.ui.components.KTITextNew
import sectonone.droidsoft.ap.ui.components.VerticalSpacer
import sectonone.droidsoft.ap.data.di.getScreenModel
import sectonone.droidsoft.ap.data.model.Category
import sectonone.droidsoft.ap.data.model.HomeScreenMenuItem
import sectonone.droidsoft.ap.data.model.UIHomeScreenSection
import sectonone.droidsoft.ap.data.model.interviewSummary
import sectonone.droidsoft.ap.data.data.getRandomUniqueEnumValues
import sectonone.droidsoft.ap.screens.home.components.InterviewHistorySummaryLayout
import sectonone.droidsoft.ap.screens.home.components.PagerCarouselLayout
import sectonone.droidsoft.ap.screens.home.components.RecommendedCategoriesLayout
import sectonone.droidsoft.ap.screens.interviewsHistory.InterviewHistoryScreen
import sectonone.droidsoft.ap.theme.KTITheme
import sectonone.droidsoft.ap.theme.ktiColors
import sectonone.droidsoft.ap.theme.nightskyGradient

internal object HomeScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: HomeScreenModel = getScreenModel()

        val viewState = screenModel.viewState.collectAsState().value

        LaunchedEffect(null) {
            println("2137 - home screen initialize")
            screenModel.initialize()
        }

        HomeScreenContent(
            state = viewState,
            onMenuItemClicked = { item ->
                val screen = InterviewHistoryScreen
                navigator.push(screen)
            },
            onSeeAllInterviewsClick = {
                navigator.push(InterviewHistoryScreen)
            }
        )
    }
}

@Composable
private fun HomeScreenContent(
    state: HomeScreenModel.ViewState,
    onMenuItemClicked: (HomeScreenMenuItem) -> Unit,
    onSeeAllInterviewsClick: () -> Unit,
) {
    Scaffold(
        backgroundColor = KTITheme.colors.backgroundSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            VerticalSpacer(16.dp)
            TopSection()
            VerticalSpacer(height = 24.dp)
            when (state) {
                is HomeScreenModel.ViewState.HomeItems -> {
                    HomeScreenFeedSection(
                        feed = state.items,
                        onMenuItemClicked = onMenuItemClicked,
                        onSeeAllInterviewsClick = onSeeAllInterviewsClick
                    )
                }

                is HomeScreenModel.ViewState.Loading -> {
//                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun TopSection() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            KTITextNew(
                text = "Hello candidate",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = KTITheme.colors.textMain
            )
            VerticalSpacer(2.dp)
            KTITextNew(
                text = "It's time to prepare for your next interview!",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = KTITheme.colors.textVariant2
            )
        }
//        KTIAvatarWithAnimation(size = 36.dp, strokeWidth = 5f)
    }
}

@Composable
private fun HomeScreenFeedSection(
    feed: List<UIHomeScreenSection>,
    onMenuItemClicked: (HomeScreenMenuItem) -> Unit,
    onSeeAllInterviewsClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        feed.forEach { feedItem: UIHomeScreenSection ->
            when (feedItem) {
                is UIHomeScreenSection.MenuItems -> MenuItems(feedItem.items, onMenuItemClicked)
                is UIHomeScreenSection.InterviewHistorySummaryUI -> InterviewHistorySummaryLayout(feedItem, onSeeAllInterviewsClick = onSeeAllInterviewsClick)
                is UIHomeScreenSection.RecommendedCategoriesCarousel -> RecommendedCategoriesLayout(feedItem)
                is UIHomeScreenSection.PagerCarousel -> PagerCarouselLayout(feedItem)
                is UIHomeScreenSection.RandomBookmarkedQuestion -> {}
                is UIHomeScreenSection.BookmarkedCategories -> TODO()
                is UIHomeScreenSection.BookmarkedQuestions -> TODO()
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
            val item = KTICardItem(
                value = homeItem,
                label = homeItem.displayName,
                assetResourcePath = homeItem.assetResourcePath
            )
            Button(
                onClick = {
                    onItemClicked.invoke(item.value)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ),
                shape = RoundedCornerShape(24.dp),
                contentPadding = PaddingValues(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .then(
                            when (item.value) {
                                HomeScreenMenuItem.CHAT_INTERVIEW -> Modifier.background(
                                    brush = nightskyGradient,
                                    shape = RoundedCornerShape(24.dp)
                                )

                                HomeScreenMenuItem.LEARN_QUESTIONS -> Modifier.background(
                                    color = ktiColors.backgroundSurfaceVariant,
                                    shape = RoundedCornerShape(24.dp)
                                )

                                HomeScreenMenuItem.BOOKMARKS -> Modifier.background(
                                    color = ktiColors.backgroundSurfaceVariant,
                                    shape = RoundedCornerShape(24.dp)
                                )
                            }
                        )
                            then Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    KTITextNew(
                        text = item.label,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 12.dp),
                        color = ktiColors.textMain,
                        fontWeight = when (item.value) {
                            HomeScreenMenuItem.LEARN_QUESTIONS, HomeScreenMenuItem.BOOKMARKS -> FontWeight.Normal
                            HomeScreenMenuItem.CHAT_INTERVIEW -> FontWeight.Medium
                        }
                    )
                    KTIIcon(
                        tint = when (item.value) {
                            HomeScreenMenuItem.LEARN_QUESTIONS, HomeScreenMenuItem.BOOKMARKS -> ktiColors.textMain
                            HomeScreenMenuItem.CHAT_INTERVIEW -> ktiColors.secondary
                        },
                        imageResource = when (item.value) {
                            HomeScreenMenuItem.LEARN_QUESTIONS -> Icons.Default.School
                            HomeScreenMenuItem.CHAT_INTERVIEW -> Icons.Default.PlayArrow
                            HomeScreenMenuItem.BOOKMARKS -> Icons.Default.Bookmark
                        }
                    )
                }
            }
            VerticalSpacer(height = 12.dp)
        }
    }
}

val interviewsSummaryMock = listOf(
    interviewSummary(answeredCount = 2, failedCount = 10, categories = getRandomUniqueEnumValues<Category>(3).map { it.displayName }),
    interviewSummary(answeredCount = 20, failedCount = 10, categories = getRandomUniqueEnumValues<Category>(2).map { it.displayName }),
    interviewSummary(answeredCount = 15, failedCount = 10, categories = getRandomUniqueEnumValues<Category>(3).map { it.displayName }),
    interviewSummary(answeredCount = 5, failedCount = 10, categories = getRandomUniqueEnumValues<Category>(4).map { it.displayName }),
    interviewSummary(answeredCount = 8, failedCount = 1, categories = getRandomUniqueEnumValues<Category>(2).map { it.displayName }),
    interviewSummary(answeredCount = 2, failedCount = 10, categories = getRandomUniqueEnumValues<Category>(2).map { it.displayName }),
)

private val homeScreenMock = HomeScreenModel.ViewState.HomeItems(
    items = listOf(
        UIHomeScreenSection.MenuItems(
            items = listOf(HomeScreenMenuItem.CHAT_INTERVIEW, HomeScreenMenuItem.LEARN_QUESTIONS)
        ),
        UIHomeScreenSection.InterviewHistorySummaryUI(
            items = interviewsSummaryMock
        ),
        UIHomeScreenSection.RecommendedCategoriesCarousel(
            items = listOf(
                Category.Android, Category.Compose, Category.AndroidSecurity, Category.DesignPatterns, Category.Kotlin
            )
        )
    )
)