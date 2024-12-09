package sectonone.droidsoft.ap.screens.settings

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import sectonone.droidsoft.ap.compose.KTIBackgroundSurface
import sectonone.droidsoft.ap.compose.KTITextNew

internal object SettingsScreen : Tab {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        SettingsScreenContent()
    }

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Settings)
            return remember {
                TabOptions(2u, "Settings", icon)
            }
        }
}

@Composable
private fun SettingsScreenContent() {
    KTIBackgroundSurface {
        KTITextNew("Settings")
    }
}