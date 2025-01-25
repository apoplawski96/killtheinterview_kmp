package sectonone.droidsoft.ap.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.School
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import sectonone.droidsoft.ap.data.model.HomeScreenMenuItem
import sectonone.droidsoft.ap.theme.*

@OptIn(ExperimentalResourceApi::class)
@Composable
fun KTIButton(
    label: String,
    labelColor: Color = kti_softblack,
    backgroundColor: Color = kti_accent,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconResId: String? = null,
    iconRes: DrawableResource? = null,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 12.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier,
        enabled = enabled,
    ) {
        if (iconRes != null) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = "Button icon",
                tint = labelColor
            )
        }
        KTITextNew(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            color = labelColor,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}

@Composable
fun KTIButtonShared(
    label: String?,
    labelColor: Color = kti_softblack,
    backgroundColor: Color = kti_accent,
    backgroundColorDisabled: Color = backgroundColor.copy(alpha = 0.7f),
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    iconColor: Color = kti_softblack,
    enabled: Boolean = true,
) {
    Button(
        onClick = { if (enabled) onClick.invoke() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            disabledBackgroundColor = backgroundColorDisabled,
        ),
        contentPadding = PaddingValues(vertical = 4.dp, horizontal = 12.dp),
        shape = RoundedCornerShape(6.dp),
        modifier = modifier,
        enabled = enabled,
    ) {
        if (label != null) {
            KTITextNew(
                text = label,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
                color = labelColor,
                modifier = Modifier.padding(vertical = 12.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
        if (icon != null) {
            KTIIcon(imageResource = icon, tint = iconColor, size = 20.dp)
        }
    }
}

@Composable
fun KTIFloatingActionButton(
    onClick: () -> Unit,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    backgroundColor: Color = ktiColors.appBars,
    backgroundColorDisabled: Color = backgroundColor.copy(alpha = 0.7f),
    iconColor: Color = ktiColors.textMain,
    enabled: Boolean = true,
) {
    Button(
        onClick = { if (enabled) onClick.invoke() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            disabledBackgroundColor = backgroundColorDisabled,
        ),
        contentPadding = PaddingValues(8.dp),
        shape = CircleShape,
        modifier = modifier then Modifier.size(44.dp),
        enabled = enabled,
    ) {
        KTIIcon(imageResource = icon, tint = iconColor, size = 24.dp)
    }
}

@Composable
fun KTITextButton(
    onClick: () -> Unit,
    label: String,
    labelColor: Color,
    size: TextUnit,
    modifier: Modifier = Modifier,
) {
    TextButton(onClick = onClick) {
        KTITextNew(
            text = label,
            fontSize = size,
            fontWeight = FontWeight.W400,
            color = labelColor,
            modifier = modifier
        )
    }
}

@Composable
fun KTIButtonLong(
    label: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = {
            onClick.invoke()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        shape = RoundedCornerShape(24.dp),
        contentPadding = PaddingValues(),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .then(
                    Modifier.background(
                        brush = nightskyGradient,
                        shape = RoundedCornerShape(24.dp)
                    )
                )
                    then Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            KTITextNew(
                text = label,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                color = ktiColors.textMain,
                fontWeight = FontWeight.Normal
            )
            // TODO:
//            KTIIcon(
//                tint = when (item.value) {
//                    HomeScreenMenuItem.LEARN_QUESTIONS, HomeScreenMenuItem.BOOKMARKS -> ktiColors.textMain
//                    HomeScreenMenuItem.CHAT_INTERVIEW -> ktiColors.secondary
//                },
//                imageResource = when (item.value) {
//                    HomeScreenMenuItem.LEARN_QUESTIONS -> Icons.Default.School
//                    HomeScreenMenuItem.CHAT_INTERVIEW -> Icons.Default.PlayArrow
//                    HomeScreenMenuItem.BOOKMARKS -> Icons.Default.Bookmark
//                }
//            )
        }
    }
    VerticalSpacer(height = 12.dp)
}