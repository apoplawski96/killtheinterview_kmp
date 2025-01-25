package sectonone.droidsoft.ap.data.model

enum class Subscription(val key: String) {
    Freemium("subscription_freemium"),
    FullAccess("subscription_full");

    companion object {
        fun mapFromString(subscription: String?) = entries.find { it.key == subscription }
    }
}