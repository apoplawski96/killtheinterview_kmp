package sectonone.droidsoft.ap.model

enum class Difficulty(val id: Int, val displayName: String, val keyName: String) {
    Beginner(id = 1, displayName = "Beginner", keyName = "Easy"),
    Intermediate(id = 1, displayName = "Intermediate", keyName = "Medium"),
    Advanced(id = 1, displayName = "Advanced", keyName = "Hard");

    companion object {

        fun getForName(name: String?): Difficulty? = entries.firstOrNull { difficulty ->
            name == difficulty.keyName
        }
    }
}