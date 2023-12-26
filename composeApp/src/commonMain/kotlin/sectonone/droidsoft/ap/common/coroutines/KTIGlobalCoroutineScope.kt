package sectonone.droidsoft.ap.common.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

interface KTIGlobalCoroutineScope : CoroutineScope

internal class KTIGlobalCoroutineScopeImpl internal constructor(
    dispatcherProvider: DispatcherProvider,
) : KTIGlobalCoroutineScope {

    override val coroutineContext: CoroutineContext =
        SupervisorJob() + dispatcherProvider.default + CoroutineExceptionHandler { _, _ -> }
}
