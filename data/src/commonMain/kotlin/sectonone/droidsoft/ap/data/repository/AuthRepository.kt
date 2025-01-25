package sectonone.droidsoft.ap.data.repository

import kotlinx.coroutines.flow.map
import sectonone.droidsoft.ap.data.model.User
import sectonone.droidsoft.ap.data.source.FirebaseAuthDataSource
import sectonone.droidsoft.ap.data.toUser

class AuthRepository(private val firebaseAuthDataSource: FirebaseAuthDataSource) {

    val onAuthStateChanged = firebaseAuthDataSource.onAuthStateChanged.map { it.toUser() }
    val currentUser get() = firebaseAuthDataSource.currentUser.toUser()

    suspend fun signInWithGoogle(googleToken: String): Result<User?> {
        return runCatching {
            firebaseAuthDataSource.signInWithGoogle(googleToken).toUser()
        }
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<User?> {
        return runCatching {
            firebaseAuthDataSource.signInWithEmailAndPassword(email, password).toUser()
        }
    }

    suspend fun createAccount(email: String, password: String): Result<User?> {
        return runCatching {
            firebaseAuthDataSource.createAccount(email, password).toUser()
        }
    }

    suspend fun logOut() {
        firebaseAuthDataSource.logOut()
    }
}
