package sectonone.droidsoft.ap.data.model

data class User(
    val info: UserAuth,
    val subscription: Subscription?,
    val isPremium: Boolean = subscription != Subscription.Freemium,
)
