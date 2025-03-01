package sectonone.droidsoft.ap.screens.interviewCurated.finished

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import sectonone.droidsoft.ap.data.di.getScreenModel
import sectonone.droidsoft.ap.data.model.InterviewHistorySummary
import sectonone.droidsoft.ap.data.resources.trophy
import sectonone.droidsoft.ap.screens.home.HomeScreen
import sectonone.droidsoft.ap.screens.interviewCurated.model.InterviewPracticeSummary
import sectonone.droidsoft.ap.screens.interviewCurated.model.percentageDisplay
import sectonone.droidsoft.ap.theme.KTITheme
import sectonone.droidsoft.ap.theme.ktiColors
import sectonone.droidsoft.ap.theme.kti_green
import sectonone.droidsoft.ap.theme.kti_red_wrong
import sectonone.droidsoft.ap.theme.nightskyGradient
import sectonone.droidsoft.ap.ui.components.KTIIcon
import sectonone.droidsoft.ap.ui.components.KTITextNew
import sectonone.droidsoft.ap.ui.components.VerticalSpacer

class InterviewFinishedScreen(
    private val summary: InterviewPracticeSummary,
) : Screen {

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<InterviewFinishedScreenModel>()
        val navigator = LocalNavigator.current
        val state by screenModel.state.collectAsState()

        LaunchedEffect(null) {
            screenModel.fetchScreenData(summary)
        }

        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(null) {
            delay(300)
            visible = true
        }

        Layout(visible, state, onGoBackClick = { navigator?.popUntil { it is HomeScreen } })
    }

    @Composable
    private fun Layout(
        visible: Boolean,
        state: InterviewFinishedDisplay?,
        onGoBackClick: () -> Unit
    ) {
        if (state == null) return

        Box(
            Modifier.fillMaxSize().background(KTITheme.colors.backgroundSurface),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Top,
        ) {
            // not visible  for some reason
            KTIIcon(
                Icons.Default.Close,
                modifier = Modifier.size(28.dp).align(Alignment.TopEnd).padding(16.dp).alpha(0.5f),
                tint = Color.Red
            )
            AnimatedVisibility(
                visible,
                enter = expandHorizontally(
                    spring(
                        stiffness = Spring.StiffnessLow,
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                    )
                ),
                exit = shrinkHorizontally(
                    spring(
                        stiffness = Spring.StiffnessLow,
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                    )
                ),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Card(
                    modifier = Modifier.align(Alignment.Center).padding(horizontal = 32.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = KTITheme.colors.backgroundSurfaceVariant),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        Modifier.background(KTITheme.colors.backgroundSurfaceVariant)
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                    ) {
                        VerticalSpacer(32.dp)
                        // Trophy image
                        Image(
                            painterResource(state.topIcon),
                            contentDescription = "",
                            modifier = Modifier.size(128.dp),
                        )

                        VerticalSpacer(13.dp)


                        // Header
                        KTITextNew(state.header, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)

                        VerticalSpacer(16.dp)

                        // Percentage score
                        KTITextNew(
                            state.percentage,
                            fontSize = 56.sp,
                            color = state.percentageColor,
                            fontWeight = FontWeight.Bold
                        )

                        VerticalSpacer(16.dp)

                        // Sub header
                        KTITextNew(
                            state.subHeader, fontSize = 18.sp, fontWeight = FontWeight.SemiBold
                        )

                        VerticalSpacer(8.dp)

                        // Body
                        KTITextNew(
                            state.body,
                            fontSize = 14.sp,
                            color = KTITheme.colors.textVariant2
                        )

                        VerticalSpacer(16.dp)

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Score item - Correct
                            ScoreItem(
                                score = summary.score.questionsAnswered,
                                color = kti_green,
                                label = "Correct \nanswers"
                            )
                            // Score item - Incorrect
                            ScoreItem(
                                score = summary.score.questionsTotal - summary.score.questionsAnswered,
                                color = kti_red_wrong,
                                label = "Incorrect \nanswers"
                            )
                            // Score item - Total
                            ScoreItem(
                                score = summary.score.questionsTotal,
                                color = KTITheme.colors.secondary,
                                label = "Total \nquestions"
                            )

                        }

                        VerticalSpacer(32.dp)


                        Button(
                            onClick = {
                                onGoBackClick.invoke()
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
                                        Modifier.background(
                                            brush = nightskyGradient,
                                            shape = RoundedCornerShape(24.dp)
                                        )
                                    )
                                        then Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                KTITextNew(
                                    text = "Go back",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .padding(horizontal = 12.dp, vertical = 12.dp),
                                    color = ktiColors.textMain,
                                    fontWeight = FontWeight.Medium
                                )
                                KTIIcon(
                                    tint = ktiColors.textMain,
                                    imageResource = Icons.Default.ArrowRight
                                )
                            }
                        }

                        VerticalSpacer(16.dp)

                    }
                }
            }
        }
    }
}

@Composable
private fun ScoreItem(score: Int, color: Color, label: String) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.size(36.dp).background(color, RoundedCornerShape(100.dp))) {
            KTITextNew(
                text = score.toString(),
                modifier = Modifier.align(Alignment.Center).padding(4.dp),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp,
                fontSize = 16.sp
            )
        }
        VerticalSpacer(height = 8.dp)
        KTITextNew(
            label,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp,
            color = KTITheme.colors.textVariant2
        )
    }
}