package sectonone.droidsoft.ap.data.repositories

import kotlinx.coroutines.flow.map
import sectonone.droidsoft.ap.data.mapUser
import sectonone.droidsoft.ap.data.model.UserAuth
import sectonone.droidsoft.ap.data.source.impl.FirebaseAuthDataSource

class AuthRepository(private val auth: FirebaseAuthDataSource) {

    val authStateChangeFlow get() = auth.onAuthStateChanged.map { it.mapUser() }

    fun createGoogleAuthProvider() {
        auth.createGoogleAuthProvider()
    }

    suspend fun signInWithGoogle(googleToken: String): Result<UserAuth?> {
        return runCatching {
            auth.signInWithGoogle(googleToken).mapUser()
        }
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<UserAuth?> {
        return runCatching {
            auth.signInWithEmailAndPassword(email, password).mapUser()
        }
    }

    suspend fun createAccount(email: String, password: String): Result<UserAuth?> {
        return runCatching {
            auth.createAccount(email, password).mapUser()
        }
    }

    suspend fun logOut() {
        auth.logOut()
    }
}
