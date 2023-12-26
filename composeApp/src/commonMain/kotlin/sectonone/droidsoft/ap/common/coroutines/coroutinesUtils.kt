package sectonone.droidsoft.ap.common.coroutines

import kotlinx.coroutines.coroutineScope

suspend fun uiScope(block: suspend () -> Unit) {
    coroutineScope {

    }
}