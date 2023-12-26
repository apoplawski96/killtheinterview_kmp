package sectonone.droidsoft.ap.model

enum class TopCategory(
    val id: String,
    val displayName: String,
    val subCategories: List<SubCategory> = emptyList(),
) {
    ANDROID(id = "1", displayName = "Android", subCategories = AndroidSubCategory.entries),
    IOS(id = "2", displayName = "iOS", subCategories = IOSSubCategory.entries),
    GIT(id = "3", displayName = "GIT"),
    REST(id = "4", displayName = "Rest"),
//    DATA_STRUCTURES("5", "Data structures"),
//    ALGORITHMS("6", "Algorithms"),
    DESIGN_PATTERNS(id = "7", displayName = "Design Patterns"),
    PROGRAMMING_PARADIGMS(id = "13", displayName = "Programming Paradigms"),
//    REACT("8", "React"),
//    WEB("9", "Web"),
//    C_SHARP("10", "C#"),
//    DOT_NET("11", ".NET"),
//    ANGULAR("11", "Angular"),
//    GRAPH_QL("11", "Graph QL"),
//    SCRUM("11", "Scrum"),
//    CPP("11", "C++"),
    KOTLIN(id = "12", displayName = "Kotlin");

    companion object {

        fun getForId(id: String): TopCategory? = entries.firstOrNull { category -> id == category.id }

        fun getForName(name: String): TopCategory? = entries.firstOrNull { category -> name == category.displayName }
    }
}

