package sectonone.droidsoft.ap.screens.settings

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.gitlive.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import sectonone.droidsoft.ap.data.auth.AuthSessionManager
import sectonone.droidsoft.ap.data.model.User
import sectonone.droidsoft.ap.data.repository.AuthRepository

class SettingsScreenModel(
    private val authRepository: AuthRepository,
    authSession: AuthSessionManager,
) : ScreenModel {

    private val _logoutState = MutableStateFlow<Result<Unit>?>(null)
    val logoutState = _logoutState.asStateFlow()

    private val _googleSignInState = MutableStateFlow<Result<User?>?>(null)
    val googleSignInState = _googleSignInState.asStateFlow()

    val authState = authSession.authState

    fun signInWithGoogle(googleToken: String?) {
        if (googleToken == null) {
            _googleSignInState.value = Result.failure(RuntimeException("Google token was null."))
            return
        }
        screenModelScope.launch {
            val result = authRepository.signInWithGoogle(googleToken)
            _googleSignInState.value = result
        }
    }

    fun logOut() {
        screenModelScope.launch {
            try {
                authRepository.logOut()
                _logoutState.value = Result.success(Unit)
            } catch (e: Exception) {
                _logoutState.value = Result.failure(e)
            }
        }
    }
}
