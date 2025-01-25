package sectonone.droidsoft.ap.screens.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import sectonone.droidsoft.ap.data.auth.AuthSessionManager
import sectonone.droidsoft.ap.data.model.User
import sectonone.droidsoft.ap.data.repository.AuthRepository

class LoginScreenModel(
    private val authRepository: AuthRepository,
    private val authSession: AuthSessionManager,
) : ScreenModel {

    private val _loginState = MutableStateFlow<Result<User?>?>(null)
    val loginState: StateFlow<Result<User?>?> get() = _loginState

    private val _createAccountState = MutableStateFlow<Result<User?>?>(null)
    val createAccountState: StateFlow<Result<User?>?> get() = _createAccountState

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
