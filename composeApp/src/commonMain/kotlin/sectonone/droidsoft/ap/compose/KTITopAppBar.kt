@file:OptIn(ExperimentalResourceApi::class)

package sectonone.droidsoft.ap.compose

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import sectonone.droidsoft.ap.theme.KTITheme
import sectonone.droidsoft.ap.theme.LocalThemeIsDark
import sectonone.droidsoft.ap.theme.ktiColors

@Composable
fun KTITopAppBar(
    title: String? = null,
    themeToggle: Boolean = false,
    iconsSection: @Composable () -> Unit = { TopBarIconsSection(themeToggle) },
    isNested: Boolean = true,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        LeftSection(title = title, isNested = isNested)
        Row(verticalAlignment = Alignment.CenterVertically) {
            iconsSection.invoke()
        }
    }
}

@Composable
fun KTIChatTopAppBar(themeToggle: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().background(KTITheme.colors.appBars),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            KTIBackButton()
            KTIAvatarWithAnimation()
            KTIHorizontalSpacer(16.dp)
            Column {
                KTITextNew("Mr Interviewer", fontSize = 16.sp, fontWeight = FontWeight.W500, color = ktiColors.textMain)
                KTITextNew("Online", fontSize = 12.sp, color = ktiColors.textVariant2)
            }
        }
        TopBarIconsSection(themeToggle)
    }
}

@Composable
private fun RowScope.LeftSection(
    isNested: Boolean,
    title: String? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (isNested) KTIBackButton()
        KTIHorizontalSpacer(width = 8.dp)
        title?.let { KTITextNew(text = title, fontSize = 18.sp, fontWeight = FontWeight.W500) }
    }
}

@Composable
private fun ThemeToggle() {
    var isDark by LocalThemeIsDark.current
    IconButton(
        onClick = { isDark = !isDark }
    ) {
        Icon(
            modifier = Modifier.padding(8.dp).size(20.dp),
            imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
            contentDescription = null
        )
    }
}

@Composable
private fun TopBarIconsSection(themeToggle: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        if (themeToggle) ThemeToggle()
    }
}
