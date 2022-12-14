package service

/**
 * The refreshing service for the UI
 */
abstract class AbstractRefreshingService {


    /**
     * adds a [Refreshable] to the list that gets called
     * whenever [onAllRefreshables] is used.
     *
    fun addRefreshable(newRefreshable : Refreshable) {
    refreshables += newRefreshable
    }

     */

    /**
     * Executes the passed method (usually a lambda) on all
     * [Refreshable]s registered with the service class that
     * extends this [AbstractRefreshingService]

    fun onAllRefreshables(method: Refreshable.() -> Unit) =
    refreshables.forEach { it.method() }

    */
}