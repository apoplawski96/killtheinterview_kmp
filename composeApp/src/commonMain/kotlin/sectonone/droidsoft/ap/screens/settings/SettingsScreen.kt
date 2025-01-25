package sectonone.droidsoft.ap.screens.settings

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mmk.kmpauth.google.GoogleButtonUiContainer
import com.mmk.kmpauth.uihelper.google.GoogleSignInButton
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import sectonone.droidsoft.ap.data.di.getScreenModel
import sectonone.droidsoft.ap.data.model.User
import sectonone.droidsoft.ap.theme.ktiColors
import sectonone.droidsoft.ap.ui.components.KTIAvatarWithAnimation
import sectonone.droidsoft.ap.ui.components.KTIButtonLong
import sectonone.droidsoft.ap.ui.components.KTISnackbar
import sectonone.droidsoft.ap.ui.components.KTITextNew
import sectonone.droidsoft.ap.ui.components.KTITopAppBar


internal object SettingsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val screenModel = getScreenModel<SettingsScreenModel>()
        val authState = screenModel.authState.collectAsState()

        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(null) {
            screenModel.logoutState
                .onEach { result ->
                    result?.onSuccess {
                        snackbarHostState.showSnackbar("Successfully logged out")
                    }?.onFailure { error ->
                        snackbarHostState.showSnackbar("Logout failed: ${error.message}")
                    }
                }
                .launchIn(this)
        }

        LaunchedEffect(null) {
            screenModel.googleSignInState
                .onEach { result ->
                    result?.onSuccess {
                        snackbarHostState.showSnackbar("Successfully signed in")
                    }?.onFailure { error ->
                        snackbarHostState.showSnackbar("Sign in failed: ${error.message}")
                    }
                }
                .launchIn(this)
        }

        SettingsScreen(
            isAuthenticated = authState.value != null,
            user = authState.value,
            onCreateAccountClick = {

            },
            onLogoutClick = {
                screenModel.logOut()
            },
            onLogInClick = {

            },
            onSignInWithGoogleClick = { tokenId ->
                screenModel.signInWithGoogle(tokenId)
            },
            snackbarHostState = snackbarHostState,
        )
    }
}

@Composable
private fun SettingsScreen(
    isAuthenticated: Boolean,
    user: User?,
    onCreateAccountClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onLogInClick: () -> Unit,
    onSignInWithGoogleClick: (String?) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        topBar = { KTITopAppBar(isNested = true, title = "Profile") },
        backgroundColor = ktiColors.backgroundSurface,
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
                snackbar = { KTISnackbar(it) },
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {

            // Display section
            KTIAvatarWithAnimation()
            KTITextNew("Hello user: ${user.toString()}")
            KTITextNew(if (isAuthenticated) "Logged in" else "Not logged in")

            // Buttons section
            if (isAuthenticated.not()) {
                GoogleButtonUiContainer(
                    onGoogleSignInResult = { googleUser ->
                        val tokenId = googleUser?.idToken
                        onSignInWithGoogleClick.invoke(tokenId)
                        println("2137 - token: $tokenId")
                    }
                ) {
                    GoogleSignInButton(
                        onClick = { this.onClick() }
                    )
                }
                KTIButtonLong("Log in", { onLogInClick.invoke() })
                KTIButtonLong("Create account", { onCreateAccountClick.invoke() })
            } else {
                KTIButtonLong("Log out", { onLogoutClick.invoke() })
            }
        }
    }
}
