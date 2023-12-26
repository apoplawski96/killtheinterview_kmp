package sectonone.droidsoft.ap.compose.bottomsheet.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sectonone.droidsoft.ap.compose.KTITextNew
import sectonone.droidsoft.ap.compose.KTIVerticalSpacer
import sectonone.droidsoft.ap.theme.*

@Composable
fun KTIBottomSheetSurface(
    title: String,
    bottomSheetTitleProperties: BottomSheetTitleProperties = bottomSheetTitlePropertiesFromDefault(),
    content: @Composable (() -> Unit)
) {
    Surface(
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        color = kti_softwhite,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            BottomSheetNotch()
            BottomSheetTitle(
                title = title,
                bottomSheetTitleProperties = bottomSheetTitleProperties
            )
            KTIVerticalSpacer(height = 16.dp)
            content()
        }
    }
}

@Composable
private fun ColumnScope.BottomSheetNotch() {
    Spacer(
        modifier = Modifier
            .padding(top = 8.dp)
            .size(width = 40.dp, height = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = Color.Black)
            .align(Alignment.CenterHorizontally)
    )
}

@Composable
private fun BottomSheetTitle(
    title: String,
    bottomSheetTitleProperties: BottomSheetTitleProperties = bottomSheetTitlePropertiesFromDefault(),
) {
    KTITextNew(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 24.dp)
            .then(
                if (bottomSheetTitleProperties.shouldFillMaxWidth) {
                    Modifier.fillMaxWidth()
                } else {
                    Modifier
                }
            ),
        text = title,
        color = kti_softblack.copy(alpha = 0.8f),
        fontWeight = FontWeight.W300,
        fontSize = 16.sp
    )
}

data class BottomSheetTitleProperties(
    val textStyle: TextStyle,
    val textAlign: TextAlign?,
    val shouldFillMaxWidth: Boolean,
)

@Composable
fun bottomSheetTitlePropertiesFromDefault(
    textStyle: TextStyle = TextStyle.Default,
    textAlign: TextAlign? = null,
    shouldFillMaxWidth: Boolean = false,
): BottomSheetTitleProperties {
    return BottomSheetTitleProperties(
        textStyle = textStyle,
        textAlign = textAlign,
        shouldFillMaxWidth = shouldFillMaxWidth,
    )
}
