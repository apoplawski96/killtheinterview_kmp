package sectonone.droidsoft.ap.ui.components.bottomsheet

data class BottomSheetListItem<T>(
    val value: T,
    val label: String,
    val bottomSheetListItemType: BottomSheetListItemType,
    val isSelected: Boolean,
)
