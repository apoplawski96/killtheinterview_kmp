package sectonone.droidsoft.ap.data.model

import org.jetbrains.compose.resources.DrawableResource
import sectonone.droidsoft.ap.data.resources.Res
import sectonone.droidsoft.ap.data.resources.android
import sectonone.droidsoft.ap.data.resources.android_compose
import sectonone.droidsoft.ap.data.resources.android_security
import sectonone.droidsoft.ap.data.resources.design_patterns
import sectonone.droidsoft.ap.data.resources.git
import sectonone.droidsoft.ap.data.resources.ios
import sectonone.droidsoft.ap.data.resources.kotlin
import sectonone.droidsoft.ap.data.resources.kotlin_coroutines
import sectonone.droidsoft.ap.data.resources.programming_paradigms

enum class Category(
    val key: String,
    val displayName: String,
    val fileWithQuestions: String = "v2_questions_$key.json",
    val imageFile: String = "$key.webp",
    val imageRes: DrawableResource,
) {
    Android("android", "Android", imageRes = Res.drawable.android),
    AndroidAppArchitecture("android_architecture", "Android App Architecture", imageFile = "android.webp", imageRes = Res.drawable.android),
    Compose("android_compose", "Compose", imageRes = Res.drawable.android_compose),
    AndroidConfiguration("android_configuration", "Android Configuration", imageFile = "android.webp", imageRes = Res.drawable.android),
    AndroidCore("android_core", "Android Core", imageFile = "android.webp", imageRes = Res.drawable.android),
    Coroutines("kotlin_coroutines", "Coroutines", imageRes = Res.drawable.kotlin_coroutines),
    Flow("kotlin_flow", "Flow", imageFile = "kotlin_coroutines.webp", imageRes = Res.drawable.kotlin_coroutines),
    AndroidLifecycle("android_lifecycle", "Android Lifecycle", imageFile = "android.webp", imageRes = Res.drawable.android),
    AndroidSecurity("android_security", "Android Security", imageRes = Res.drawable.android_security),
    AndroidViewModel("android_viewmodel", "Android ViewModel", imageFile = "android.webp", imageRes = Res.drawable.android),
    DesignPatterns("design_patterns", "Design Patterns", imageRes = Res.drawable.design_patterns),
    Git("git", "Git", imageRes = Res.drawable.git),
    IOS("ios", "iOS", imageRes = Res.drawable.ios),
    Kotlin("kotlin", "Kotlin", imageRes = Res.drawable.kotlin),
    ProgrammingParadigms("programming_paradigms", "Programming Paradigms", imageRes = Res.drawable.programming_paradigms);

    companion object {
        fun getForKey(key: String?): Category? = entries.find { it.key == key }
    }
}

val allQuestionsFiles get() = Category.entries.map { it.fileWithQuestions }

val List<Category>.parseCategoriesToDb get() = this.joinToString(",") { it.key }