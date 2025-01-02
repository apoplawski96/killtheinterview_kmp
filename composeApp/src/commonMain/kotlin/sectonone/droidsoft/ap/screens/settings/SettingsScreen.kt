package sectonone.droidsoft.ap.screens.settings

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import sectonone.droidsoft.ap.ui.components.KTIBackgroundSurface
import sectonone.droidsoft.ap.ui.components.KTITextNew

internal object SettingsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        SettingsScreenContent()
    }
}

@Composable
private fun SettingsScreenContent() {
    KTIBackgroundSurface {
        KTITextNew("Settings")
    }
}