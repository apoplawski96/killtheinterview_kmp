package sectonone.droidsoft.ap.screens.interviewSetup.model

import sectonone.droidsoft.ap.data.model.Category

data class SelectableCategory(
    val isSelected: Boolean,
    val category: Category,
    val isUnlocked: Boolean,
)
