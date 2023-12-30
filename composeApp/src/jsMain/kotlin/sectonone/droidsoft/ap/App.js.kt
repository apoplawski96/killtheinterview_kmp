package sectonone.droidsoft.ap

import kotlinx.browser.window

internal actual fun openUrl(url: String?) {
    url?.let { window.open(it) }
}

internal fun hello() {

}