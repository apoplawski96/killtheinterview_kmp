package sectonone.droidsoft.ap.data.auth

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import sectonone.droidsoft.ap.data.model.User
import sectonone.droidsoft.ap.data.repository.AuthRepository

class AuthSessionManager(private val authRepository: AuthRepository) {

    private val coroutineScope = CoroutineScope(SupervisorJob())

    private val _authState = MutableStateFlow(authRepository.currentUser)
    val authState: StateFlow<User?> get() = _authState.asStateFlow()

    init {
        authRepository.onAuthStateChanged
            .onEach { _authState.value = it }
            .launchIn(coroutineScope)

    }

    val isAuthenticated get() = authRepository.currentUser != null

    val currentUser get() =  authRepository.currentUser
}