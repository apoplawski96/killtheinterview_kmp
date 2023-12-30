package sectonone.droidsoft.ap.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sectonone.droidsoft.ap.theme.kti_dark_grey
import sectonone.droidsoft.ap.theme.kti_green
import sectonone.droidsoft.ap.theme.kti_grey
import sectonone.droidsoft.ap.theme.kti_softwhite

@Composable
private fun ControlSection(
    addPointClick: () -> Unit,
    noPointClick: () -> Unit,
    showAnswerClick: () -> Unit,
    inputEnabled: Boolean,
    isAnswerExpanded: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier then Modifier
            .fillMaxWidth()
            .sizeIn(minHeight = 80.dp)
            .background(kti_softwhite),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            KTIButtonShared(
                label = "I knew it!",
                onClick = addPointClick,
                iconColor = if (inputEnabled) kti_softwhite else kti_grey,
                enabled = inputEnabled,
                backgroundColor = kti_green,
                labelColor = if (inputEnabled) kti_softwhite else kti_grey,
                modifier = Modifier.weight(1f),
            )
            KTIHorizontalSpacer(width = 16.dp)
            KTIButtonShared(
                label = "Confused :(",
                onClick = noPointClick,
                iconColor = if (inputEnabled) kti_softwhite else kti_grey,
                enabled = inputEnabled,
                backgroundColor = if (inputEnabled) kti_grey else kti_grey.copy(alpha = 0.7f),
                labelColor = if (inputEnabled) kti_dark_grey.copy(alpha = 0.9f) else kti_dark_grey.copy(alpha = 0.6f),
                modifier = Modifier.weight(1f),
            )
//            KTIButtonShared(
//                label = if (isAnswerExpanded.not()) "Show answer" else "Hide answer",
//                onClick = showAnswerClick,
//                icon = if (isAnswerExpanded.not()) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
//                iconColor = if (inputEnabled) kti_softblack else kti_grey,
//                enabled = inputEnabled,
//                backgroundColor = kti_softwhite,
//                labelColor = if (inputEnabled) kti_softblack else kti_grey,
//            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Scaffold(
        bottomBar = {
            ControlSection(
                addPointClick = { /*TODO*/ },
                noPointClick = { /*TODO*/ },
                showAnswerClick = { /*TODO*/ },
                inputEnabled = true,
                isAnswerExpanded = false,
            )
        },
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .background(Color.White)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .background(backgroundGradientBrush)
                .fillMaxSize()
        )
    }
}