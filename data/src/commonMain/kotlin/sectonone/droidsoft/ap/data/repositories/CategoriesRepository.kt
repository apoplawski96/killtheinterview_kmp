package sectonone.droidsoft.ap.data.repositories

import sectonone.droidsoft.ap.data.auth.UserSessionState
import sectonone.droidsoft.ap.data.model.Category
import sectonone.droidsoft.ap.data.model.PremiumItem
import sectonone.droidsoft.ap.data.source.CategoriesDataSource

class CategoriesRepository(
    private val dataSource: CategoriesDataSource,
    private val userSessionState: UserSessionState,
) {

    suspend fun getCategories(): List<PremiumItem<Category>>? {
        return dataSource.getCategories()?.map {
            PremiumItem(
                item = it,
                unlocked = it.isFreemium || userSessionState.proSubscription
            )
        }
    }
}