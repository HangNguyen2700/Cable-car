package view

import entity.Player
import entity.Tile
import service.AbstractRefreshingService

/**
 * This interface provides a mechanism for the service layer classes to communicate
 * (usually to the view classes) that certain changes have been made to the entity
 * layer, so that the user interface can be updated accordingly.
 *
 * Default (empty) implementations are provided for all methods, so that implementing
 * UI classes only need to react to events relevant to them.
 *
 * @see AbstractRefreshingService
 *
 */

interface Refreshable {

    /**
     * perform refreshes that are necessary after a new game started
     */
    fun refreshAfterStartGame() {}

    fun refreshAfterDrawStackEmpty() {}

    fun refreshAfterJoinGameInitialized()

    fun refreshAfterTileRotation(tile: Tile)

    fun refreshAfterPlayerJoinedInWaitSession(playerName:String){}

    fun refreshAfterPlayerLeftInWaitSession(playerName:String){}

    /**
     * Refresh after current player placed tile and next player is called     *
     */
    fun refreshAfterPlaceTile(){}

    /**
     * refreshes Turn after undo is engaged
     */

    fun refreshAfterUndo(){}

    /**
     * refreshes Turn after redo is engaged
     */

    fun refreshAfterRedo(){}

    /**
     * refreshes after game is finished
     */

    fun refreshAfterGameFinished(){}

    /**
     * Refresh game config except player name and numbers
     */
    fun refreshAfterRestartGame(playerNames: MutableList<Player>){}
}