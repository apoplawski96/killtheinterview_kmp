package sectonone.droidsoft.ap.data.source

import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.flow

class FirebaseFirestoreDataSource(private val db: FirebaseFirestore) {

    private val usersCollection get() = db.collection("users")

    val allSubscriptionsFlow
        get() = flow {
            usersCollection
                .snapshots
                .collect { querySnapshot ->
                    val subscriptions = querySnapshot.documents.associate { documentSnapshot ->
                        val uid = documentSnapshot.id
                        val subscription = documentSnapshot.get("subscription") as? String
                        uid to subscription
                    }
                    emit(subscriptions)
                }
        }

    suspend fun setUserSubscription(uid: String, subscription: String) {
        usersCollection
            .document(uid)
            .set("subscription" to subscription, merge = true)
    }

    suspend fun getUserSubscription(uid: String): String? {
        return usersCollection
            .document(uid)
            .get()
            .get("subscription")
    }

    fun userSubscriptionFlow(uid: String) = flow {
        usersCollection
            .document(uid)
            .snapshots
            .collect { documentSnapshot ->
                emit(documentSnapshot.get<String>("subscription"))
            }
    }
}