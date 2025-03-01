package sectonone.droidsoft.ap.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import sectonone.droidsoft.ap.data.model.Subscription
import sectonone.droidsoft.ap.data.source.impl.FirebaseFirestoreDataSource

class UserRepository(private val firestore: FirebaseFirestoreDataSource) {

    fun userSubscriptionFlow(uid: String): Flow<Subscription?> =
        firestore
            .userSubscriptionFlow(uid)
            .map {
                Subscription.mapFromString(it).also { println("2137 - subscription collected, $it") }
            }
}
