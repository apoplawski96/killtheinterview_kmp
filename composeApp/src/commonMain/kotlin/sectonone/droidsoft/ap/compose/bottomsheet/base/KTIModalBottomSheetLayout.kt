package sectonone.droidsoft.ap.compose.bottomsheet.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun KTIModalBottomSheetLayout(
    sheetState: ModalBottomSheetState,
    bottomSheetContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        modifier = modifier,
        sheetContent = {
            Box(modifier = Modifier.defaultMinSize(minHeight = 1.dp)) {
                bottomSheetContent()
            }
        },
        sheetState = sheetState,
        sheetBackgroundColor = Color.Transparent,
        sheetShape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp),
        sheetElevation = 0.dp,
        content = content
    )
}
