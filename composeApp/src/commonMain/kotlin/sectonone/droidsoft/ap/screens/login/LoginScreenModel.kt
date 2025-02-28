package sectonone.droidsoft.ap.screens.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import sectonone.droidsoft.ap.data.auth.UserSessionState
import sectonone.droidsoft.ap.data.model.UserAuth
import sectonone.droidsoft.ap.data.repositories.AuthRepository

class LoginScreenModel(
    private val authRepository: AuthRepository,
    private val authSession: UserSessionState,
) : ScreenModel {

    private val _loginState = MutableStateFlow<Result<UserAuth?>?>(null)
    val loginState: StateFlow<Result<UserAuth?>?> get() = _loginState

    private val _createAccountState = MutableStateFlow<Result<UserAuth?>?>(null)
    val createAccountState: StateFlow<Result<UserAuth?>?> get() = _createAccountState

    fun createAccount(email: String, password: String) {
        screenModelScope.launch {
            val result = authRepository.createAccount(email, password)
            _createAccountState.value = result
        }
    }

    fun loginWithEmailAndPassword(email: String, password: String) {
        screenModelScope.launch {
            val result = authRepository.signInWithEmailAndPassword(email, password)
            _loginState.value = result
        }
    }
}
