package sectonone.droidsoft.ap.compose

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import sectonone.droidsoft.ap.theme.ktiColors
import sectonone.droidsoft.ap.theme.kti_softblack
import sectonone.droidsoft.ap.theme.kti_text_icons

@Composable
fun KTIText(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(
        color = kti_text_icons,
        lineHeight = 13.sp,
        fontSize = 12.sp,
        fontFamily = FontFamily.Default,
        letterSpacing = TextUnit(-0.01f, TextUnitType.Sp)
    ),
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.White,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    Text(
        modifier = modifier,
        text = text,
        maxLines = maxLines,
        overflow = overflow,
        style = textStyle,
        textAlign = textAlign,
        color = color
    )
}

@Composable
fun KTITextNew(
    text: String,
    fontSize: TextUnit = 14.sp,
    fontWeight: FontWeight = FontWeight.W400,
    modifier: Modifier = Modifier,
    color: Color = ktiColors.textMain,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    textAlign: TextAlign = TextAlign.Start,
    lineHeight: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
) {
    Text(
        text = text,
//        fontFamily = fontFamilyResource(getForWeight(fontWeight)),
        fontWeight = fontWeight,
        maxLines = maxLines,
        overflow = overflow,
        textAlign = textAlign,
        modifier = modifier,
        fontSize = fontSize,
        color = color,
        lineHeight = lineHeight,
        fontStyle = fontStyle,
    )
}

//@Composable
//private fun getForWeight(fontWeight: FontWeight): FontResource = when (fontWeight) {
//    FontWeight.W300 -> SharedRes.fonts.kanit.light
//    FontWeight.W400 -> SharedRes.fonts.kanit.regular
//    FontWeight.W500 -> SharedRes.fonts.kanit.medium
//    FontWeight.W600 -> SharedRes.fonts.kanit.semibold
//    FontWeight.W700 -> SharedRes.fonts.kanit.bold
//    else -> SharedRes.fonts.kanit.regular
//}