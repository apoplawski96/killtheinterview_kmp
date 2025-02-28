package sectonone.droidsoft.ap.screens.interviewSetup.model

import sectonone.droidsoft.ap.data.model.Category
import sectonone.droidsoft.ap.data.model.PremiumItem

data class SelectableCategory(
    val isSelected: Boolean,
    val category: PremiumItem<Category>,
)
