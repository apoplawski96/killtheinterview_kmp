package sectonone.droidsoft.ap.data.auth

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import sectonone.droidsoft.ap.data.model.User
import sectonone.droidsoft.ap.data.repository.AuthRepository
import sectonone.droidsoft.ap.data.repository.UserRepository

class AuthSessionManager(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val supervisorScope: CoroutineScope,
) {

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    fun initialize() {
        authRepository.createGoogleAuthProvider()
        supervisorScope.launch {
            authRepository.authStateChangeFlow
                .collectLatest { authUser ->
                    if (authUser != null) {
                        userRepository.userSubscriptionFlow(authUser.uid)
                            .map { subscription ->
                                User(
                                    authUser,
                                    subscription
                                )
                            }
                            .collect { user ->
                                _user.value = user
                            }
                    } else {
                        _user.value = null
                    }
                }
        }
    }
}