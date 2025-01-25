package sectonone.droidsoft.ap.data.source

import com.mmk.kmpauth.google.GoogleAuthCredentials
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.GoogleAuthProvider
import dev.gitlive.firebase.auth.auth

class FirebaseAuthDataSource(private val firebaseAuth: FirebaseAuth) {

    val onAuthStateChanged = firebaseAuth.authStateChanged

    val currentUser get() = firebaseAuth.currentUser

    fun createGoogleAuthProvider() {
        com.mmk.kmpauth.google.GoogleAuthProvider.create(
            credentials = GoogleAuthCredentials(
                serverId = "969974281440-j6649vr2ec4l6ore4qkhvta7egob54t5.apps.googleusercontent.com"
            )
        )
    }

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