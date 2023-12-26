package sectonone.droidsoft.ap.screens.list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import sectonone.droidsoft.ap.compose.KTIVerticalSpacer
import sectonone.droidsoft.ap.compose.bottomsheet.base.KTIBottomSheetSurface
import sectonone.droidsoft.ap.compose.bottomsheet.content.BottomSheetListItemType
import sectonone.droidsoft.ap.compose.bottomsheet.content.SelectableListItem
import sectonone.droidsoft.ap.compose.bottomsheet.model.BottomSheetListItem
import sectonone.droidsoft.ap.model.Difficulty

@Composable
fun ListScreenBottomSheetContent(
    selectedDifficulties: List<Difficulty>,
    onDifficultyToggled: (Difficulty) -> Unit,
) {
    KTIBottomSheetSurface(title = "Filter questions by difficulty") {
        Column(modifier = Modifier.fillMaxWidth()) {
            difficultiesToSelectableListItems(selectedDifficulties).forEach { difficulty ->
                SelectableListItem(
                    isSelected = difficulty.isSelected,
                    item = difficulty,
                    itemType = difficulty.bottomSheetListItemType,
                    onItemSelected = {
                        onDifficultyToggled(difficulty.value)
                    },
                )
            }
            KTIVerticalSpacer(height = 16.dp)
        }
    }
}

private fun difficultiesToSelectableListItems(
    selectedDifficulties: List<Difficulty>,
): List<BottomSheetListItem<Difficulty>> {
    return Difficulty.values().toList().map { difficulty ->
        BottomSheetListItem(
            value = difficulty,
            label = difficulty.displayName,
            bottomSheetListItemType = BottomSheetListItemType.CHECKABLE,
            isSelected = selectedDifficulties.firstOrNull { selectedDifficulty ->
                difficulty == selectedDifficulty
            } != null
        )
    }
}

