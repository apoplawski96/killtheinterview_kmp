package sectonone.droidsoft.ap.compose.bottomsheet.model

import sectonone.droidsoft.ap.compose.bottomsheet.content.BottomSheetListItemType

data class BottomSheetListItem<T>(
    val value: T,
    val label: String,
    val bottomSheetListItemType: BottomSheetListItemType,
    val isSelected: Boolean,
)
