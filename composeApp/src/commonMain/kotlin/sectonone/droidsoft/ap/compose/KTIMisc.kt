package sectonone.droidsoft.ap.compose

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jetbrains.compose.resources.painterResource
import sectonone.droidsoft.ap.theme.white
import sectonone.droidsoft.ap.theme.white30
import sectonone.droidsoft.ap.theme.white50
import sectonone.droidsoft.ap.theme.white80

@Composable
fun KTIAvatarWithAnimation(
    avatarResource: Painter = painterResource("avatar.png"),
    size: Dp = 36.dp,
    strokeWidth: Float = 4f
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotationAnimation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing))
    )
    val rainbowColorsBrush = Brush.horizontalGradient(
        listOf(
            Color.Red,
            Color.Magenta,
            Color.Blue,
            Color.Cyan,
            Color.Green,
            Color.Yellow,
        )
    )
    Image(
        painter = avatarResource,
        contentDescription = "",
        modifier = Modifier.clip(CircleShape).size(size).drawBehind {
            rotate(rotationAnimation.value) {
                drawCircle(rainbowColorsBrush, style = Stroke(strokeWidth))
            }
        }
    )
}

@Composable
fun AnimatedPagerIndicator(
    numberOfPages: Int,
    selectedPage: Int,
    modifier: Modifier = Modifier,
    selectedColor: Color = white50,
    defaultColor: Color = white50,
    defaultRadius: Dp = 6.dp,
    selectedLength: Dp = 32.dp,
    space: Dp = 5.dp,
    animationDurationInMillis: Int = 500,
    autoScrollProgressProvider: () -> Float,
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space),
            modifier = modifier,
        ) {
            for (i in 0 until numberOfPages) {
                val isSelected = i == selectedPage
                PageIndicatorDot(
                    isSelected = isSelected,
                    selectedColor = selectedColor,
                    defaultColor = defaultColor,
                    defaultRadius = defaultRadius,
                    selectedLength = selectedLength,
                    animationDurationInMillis = animationDurationInMillis,
                    progressColor = white,
                    autoScrollProgressProvider = autoScrollProgressProvider
                )
            }
        }
    }
}

@Composable
private fun PageIndicatorDot(
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    selectedColor: Color,
    defaultColor: Color,
    progressColor: Color,
    defaultRadius: Dp = 16.dp,
    selectedLength: Dp = 60.dp,
    animationDurationInMillis: Int = 500,
    autoScrollProgressProvider: () -> Float,
) {
    val color: Color by animateColorAsState(
        targetValue = if (isSelected) selectedColor else defaultColor,
        animationSpec = tween(animationDurationInMillis),
        label = "page indicator animation",
    )
    val dotWidth: Dp by animateDpAsState(
        targetValue = if (isSelected) {
            selectedLength
        } else {
            defaultRadius
        },
        animationSpec = tween(
            durationMillis = animationDurationInMillis,
        ),
        label = "page indicator animation"
    )

    Canvas(
        modifier = modifier.then(Modifier.size(width = dotWidth, height = defaultRadius))
    ) {
        // Draws dots for each page
        drawRoundRect(
            color = color,
            topLeft = Offset.Zero,
            size = Size(width = dotWidth.toPx(), height = defaultRadius.toPx()),
            cornerRadius = CornerRadius(x = defaultRadius.toPx(), y = defaultRadius.toPx())
        )
        // Draws progress indicator
        if (isSelected && autoScrollProgressProvider.invoke() < 1f) {
            drawRoundRect(
                color = progressColor,
                topLeft = Offset.Zero,
                size = Size(
                    width = maxOf(autoScrollProgressProvider.invoke() * dotWidth.toPx(), defaultRadius.toPx()),
                    height = defaultRadius.toPx()
                ),
                cornerRadius = CornerRadius(x = defaultRadius.toPx(), y = defaultRadius.toPx())
            )
        }
    }
}

@Composable
fun rememberAutoSwipeTimer(
    swipeInterval: Long,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    doAutoSwipe: suspend () -> Unit,
    shouldAutoSwipe: Boolean = true
): AutoSwipeTimer {
    val autoSwipeTimer = remember { AutoSwipeTimer(swipeInterval, coroutineScope, doAutoSwipe) }
    LaunchedEffect(shouldAutoSwipe) {
        if (shouldAutoSwipe) {
            autoSwipeTimer.start()
        } else {
            autoSwipeTimer.stop()
        }
    }
    return autoSwipeTimer
}

class AutoSwipeTimer (
    private val swipeInterval: Long,
    private val coroutineScope: CoroutineScope,
    private val doAutoSwipe: suspend () -> Unit,
) {

    private val _progress = MutableStateFlow(0.0f)
    val progress = _progress.asStateFlow()

    private var timerJob: Job? = null

    fun start() {
        stop()
        timerJob = launchTimer()
    }

    fun stop() {
        _progress.value = 0.0f
        timerJob?.cancel()
        timerJob = null
    }

    private fun launchTimer(): Job = coroutineScope.launch {
        while (isActive) {
            val startTime: Instant = Clock.System.now()
            while (Clock.System.now().toEpochMilliseconds() - startTime.toEpochMilliseconds() < swipeInterval) {
                val elapsed = Clock.System.now().toEpochMilliseconds() - startTime.toEpochMilliseconds()
                _progress.value = elapsed.toFloat() / swipeInterval
                delay(16)
            }
            _progress.value = 1.0f
            doAutoSwipe.invoke()
        }
    }
}
