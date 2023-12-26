package sectonone.droidsoft.ap.screens.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import sectonone.droidsoft.ap.compose.KTIButton
import sectonone.droidsoft.ap.compose.KTITextButton
import sectonone.droidsoft.ap.compose.KTITextNew
import sectonone.droidsoft.ap.compose.KTIVerticalSpacer
import sectonone.droidsoft.ap.screens.home.HomeScreen
import sectonone.droidsoft.ap.theme.KTITheme

internal object WelcomeScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        WelcomeScreenContent(
            navigateToHomeScreen = {
                navigator.push(HomeScreen)
            },
            navigateToLoginScreen = {
                // TODO
            }
        )
    }
}

@Composable
private fun WelcomeScreenContent(
    navigateToHomeScreen: () -> Unit,
    navigateToLoginScreen: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(KTITheme.colors.backgroundSurface)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopSection(modifier = Modifier.weight(2f))
        BottomSection(
            navigateToHomeScreen = navigateToHomeScreen,
            navigateToLoginScreen = navigateToLoginScreen,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun TopSection(modifier: Modifier = Modifier) {
    Box(modifier = modifier then Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
//        KTIIllustration(imageResource = SharedRes.images.undraw_programming_re_kg9v)
    }
}

@Composable
private fun BottomSection(
    navigateToHomeScreen: () -> Unit,
    navigateToLoginScreen: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier then Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        KTITextNew(
            text = "Kill your upcoming tech interview",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        KTIVerticalSpacer(height = 16.dp)
        KTIButton(
            label = "Create account",
            labelColor = KTITheme.colors.textMain,
            backgroundColor = KTITheme.colors.secondary,
            onClick = navigateToLoginScreen,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )
        KTITextButton(
            onClick = navigateToHomeScreen,
            label = "Continue without account",
            labelColor = KTITheme.colors.textMain,
            size = 12.sp
        )
    }
}