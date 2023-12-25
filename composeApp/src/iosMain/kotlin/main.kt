import androidx.compose.ui.window.ComposeUIViewController
import sectonone.droidsoft.ap.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
