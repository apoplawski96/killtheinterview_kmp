package sectonone.droidsoft.ap.data.source

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.GoogleAuthProvider
import dev.gitlive.firebase.auth.auth

class FirebaseAuthDataSource(private val firebaseAuth: FirebaseAuth) {

    val onAuthStateChanged = firebaseAuth.authStateChanged

    val currentUser get() = firebaseAuth.currentUser

    suspend fun signInWithGoogle(googleToken: String): FirebaseUser? {
        return try {
            val authResult = firebaseAuth.signInWithCredential(
                GoogleAuthProvider.credential(googleToken, null)
            )
            authResult.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String): FirebaseUser? {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password)
            authResult.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun createAccount(email: String, password: String): FirebaseUser? {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password)
            authResult.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun logOut() {
        firebaseAuth.signOut()
    }
}