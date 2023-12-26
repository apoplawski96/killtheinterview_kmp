package sectonone.droidsoft.ap.screens.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sectonone.droidsoft.ap.compose.KTITextNew
import sectonone.droidsoft.ap.theme.kti_accent
import sectonone.droidsoft.ap.theme.kti_softblack
import sectonone.droidsoft.ap.theme.kti_softwhite

@Composable
fun ListScreenScoreBar(score: Int, total: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(kti_softwhite.copy(alpha = 0.8f))
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            KTITextNew(
                text = "Question answered: $score/$total",
                fontSize = 13.sp,
                fontWeight = FontWeight.W400,
                color = kti_softblack,
                modifier = Modifier.padding(start = 12.dp, top = 0.dp, bottom = 8.dp, end = 12.dp)
            )
        }
        Divider(color = kti_accent.copy(0.8f), thickness = 1.dp)
    }
}