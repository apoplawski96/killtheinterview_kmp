package sectonone.droidsoft.ap.screens.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import sectonone.droidsoft.ap.ui.components.VerticalSpacer
import sectonone.droidsoft.ap.theme.KTITheme

internal object LibraryScreen : Tab {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        LibraryScreenContent()
    }

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.LibraryMusic)
            return remember {
                TabOptions(1u, "Library", icon)
            }
        }
}

@Composable
private fun LibraryScreenContent() {
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
//            TopSection()
            VerticalSpacer(height = 24.dp)
        }
    }
}