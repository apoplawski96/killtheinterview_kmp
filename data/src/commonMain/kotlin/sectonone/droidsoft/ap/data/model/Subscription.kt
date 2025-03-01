package sectonone.droidsoft.ap.data.model

enum class Subscription(val key: String) {
    Freemium("free"),
    Pro("pro");

    companion object {
        fun mapFromString(subscription: String?) = entries.find { it.key == subscription }
    }
}