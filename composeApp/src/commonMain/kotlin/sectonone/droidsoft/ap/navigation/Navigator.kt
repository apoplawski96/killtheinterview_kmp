package sectonone.droidsoft.ap.navigation

import sectonone.droidsoft.ap.navigation.model.NavigationCommand
import sectonone.droidsoft.ap.navigation.model.NavigationDestination
import kotlinx.coroutines.flow.SharedFlow

interface Navigator {

    val commands: SharedFlow<NavigationCommand>

    fun navigateBack()

    fun navigate(
        destination: NavigationDestination,
        singleTop: Boolean = true,
    )

    fun switchBackstack(
        rootDestination: NavigationDestination,
    )

    fun popUpTo(
        destination: NavigationDestination,
        inclusive: Boolean = false,
        saveState: Boolean = false,
    )
}
