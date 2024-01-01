package sectonone.droidsoft.ap.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.painterResource
import sectonone.droidsoft.ap.theme.KTITheme
import sectonone.droidsoft.ap.theme.ktiColors

@Composable
fun KTIIcon(
    imageResource: ImageVector,
    modifier: Modifier = Modifier,
    size: Dp = Dp.Unspecified,
    tint: Color = ktiColors.textMain,
) {
    Icon(imageVector = imageResource, contentDescription = null, modifier = modifier then Modifier.size(size), tint = tint)
}

@Composable
fun KTIIllustration(
    resourcePath: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Image(
        painter = painterResource(resourcePath),
        contentDescription = contentDescription,
        modifier = modifier
    )
}

@Composable
fun KTIIconButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
) {
    IconButton(onClick = onClick, modifier = modifier, enabled = enabled) {
        icon.invoke()
    }
}

//@Composable
//fun KTIIconButton(
//    onClick: () -> Unit,
//    imageResource: ImageResource,
//    size: Dp,
//    tint: Color,
//    modifier: Modifier = Modifier,
//    contentDescription: String? = null,
//) {
//    IconButton(onClick = onClick) {
//        KTIIcon(
//            imageResource = imageResource,
//            size = size,
//            tint = tint,
//            contentDescription = contentDescription,
//            modifier = modifier
//        )
//    }
//}

@Composable
fun KTIBackIcon(tint: Color = ktiColors.textMain) {
    Icon(Icons.Filled.ArrowBack, "Back Icon", tint = tint)
}

@Composable
fun KTIBackButton() {
    val navigator = LocalNavigator.currentOrThrow
    KTIIconButton(onClick = { navigator.pop() }) {
        KTIBackIcon()
    }
}