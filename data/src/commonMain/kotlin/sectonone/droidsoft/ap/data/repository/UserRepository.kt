package sectonone.droidsoft.ap.data.repository

import kotlinx.coroutines.flow.map
import sectonone.droidsoft.ap.data.model.Subscription
import sectonone.droidsoft.ap.data.source.FirebaseFirestoreDataSource

class UserRepository(private val firestore: FirebaseFirestoreDataSource) {

    fun userSubscriptionFlow(uid: String) =
        firestore
            .userSubscriptionFlow(uid)
            .map {
                Subscription.mapFromString(it).also { println("2137 - subscription collected, $it") }
            }
}
