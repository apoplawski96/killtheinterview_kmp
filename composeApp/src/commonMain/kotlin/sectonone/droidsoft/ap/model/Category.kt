package sectonone.droidsoft.ap.model

enum class Category(
    val key: String,
    val displayName: String,
    val fileWithQuestions: String = "v2_questions_$key.json",
    val imageFile: String = "$key.webp",
) {
    Android("android", "Android"),
    AndroidAppArchitecture("android_architecture", "Android App Architecture", imageFile = "android.webp"),
    Compose("android_compose", "Compose"),
    AndroidConfiguration("android_configuration", "Android Configuration", imageFile = "android.webp"),
    AndroidCore("android_core", "Android Core", imageFile = "android.webp"),
    Coroutines("kotlin_coroutines", "Coroutines"),
    Flow("kotlin_flow", "Flow", imageFile = "kotlin_coroutines.webp"),
    AndroidLifecycle("android_lifecycle", "Android Lifecycle", imageFile = "android.webp"),
    AndroidSecurity("android_security", "Android Security"),
    AndroidViewModel("android_viewmodel", "Android ViewModel", imageFile = "android.webp"),
    DesignPatterns("design_patterns", "Design Patterns"),
    Git("git", "Git"),
    IOS("ios", "iOS"),
    Kotlin("kotlin", "Kotlin"),
    ProgrammingParadigms("programming_paradigms", "Programming Paradigms"),
    ;

    companion object {
        fun getForKey(key: String?): Category? {
            return entries.find { it.key == key }
        }
    }
}

val allQuestionsFiles get() = Category.entries.map { it.fileWithQuestions }