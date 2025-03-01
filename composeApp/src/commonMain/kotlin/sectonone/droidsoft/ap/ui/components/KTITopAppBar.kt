@file:OptIn(ExperimentalResourceApi::class)

package sectonone.droidsoft.ap.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import sectonone.droidsoft.ap.theme.KTIColors
import sectonone.droidsoft.ap.theme.KTITheme
import sectonone.droidsoft.ap.theme.LocalThemeIsDark
import sectonone.droidsoft.ap.theme.ktiColors
import sectonone.droidsoft.ap.theme.kti_grey

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
fun KTIChatTopAppBar(themeToggle: Boolean = false, progress: () -> Float) {
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
//            KTIAvatarWithAnimation()
            HorizontalSpacer(16.dp)
            LinearProgressIndicator(modifier = Modifier.width(256.dp), color = Color.Green, trackColor = kti_grey, progress = { progress.invoke() })
//            Column {
//                KTITextNew("Mr Interviewer", fontSize = 16.sp, fontWeight = FontWeight.W500, color = ktiColors.textMain)
//                KTITextNew("Online", fontSize = 12.sp, color = ktiColors.textVariant2)
//            }
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
        HorizontalSpacer(width = 8.dp)
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
