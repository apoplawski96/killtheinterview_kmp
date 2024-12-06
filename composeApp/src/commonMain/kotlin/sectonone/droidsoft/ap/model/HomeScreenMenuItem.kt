package sectonone.droidsoft.ap.model

import androidx.compose.ui.graphics.Brush
import sectonone.droidsoft.ap.theme.ktiColors
import sectonone.droidsoft.ap.theme.nightskyGradient

enum class HomeScreenMenuItem(val displayName: String, val assetResourcePath: String) {
    QUESTIONS_CATEGORIES(displayName = "Learn questions", assetResourcePath = "book.png"),
    CHAT_INTERVIEW(displayName = "Start interview", assetResourcePath = "student.png"),
}

//fun HomeScreenMenuItem.getBackgrou
