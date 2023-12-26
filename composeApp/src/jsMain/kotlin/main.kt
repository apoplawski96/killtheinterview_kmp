import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import sectonone.droidsoft.ap.App
import org.jetbrains.skiko.wasm.onWasmReady
import sectonone.droidsoft.ap.JSApp

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        CanvasBasedWindow("KillTheInterviewKMP") {
            JSApp()
        }
    }
}
