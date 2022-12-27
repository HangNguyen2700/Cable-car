package service

import entity.Player
import entity.Station
import entity.Tile
import service.message.TurnMessage

class PlayerActionService(private val rootService: RootService) : AbstractRefreshingService() {

    fun placeTile(player: Player, fromHand : Boolean, posX: Int, posY: Int) {
        if(!rootService.gameService.isLocalOnlyGame) {
            // network game, so a turnMessage has to be sent
            var tile: Tile? = null
            if(fromHand) {
                tile = rootService.currentGame!!.currentTurn.players[rootService.currentGame!!.currentTurn.currentPlayerIndex].handTile
                // give player next tile
                rootService.currentGame!!.currentTurn.players[rootService.currentGame!!.currentTurn.currentPlayerIndex].handTile =
                    rootService.currentGame!!.currentTurn.gameField.tileStack.tiles.removeFirst()
            } else {
                tile = rootService.currentGame!!.currentTurn.gameField.tileStack.tiles.removeFirst()
            }
            rootService.networkService.sendTurnMessage(TurnMessage(
                posX, posY,
                !fromHand,
                rootService.gameService.tileLookUp.indexOf(tile)
            ))
        }

        // add tile to gameField
        if (isPositionLegal(posX, posY)) {
            rootService.currentGame!!.currentTurn.gameField.field[posX][posY]
            if(fromHand) {
                rootService.currentGame!!.currentTurn.gameField.field[posX][posY] =
                    rootService.currentGame!!.currentTurn.players[rootService.currentGame!!.currentTurn.currentPlayerIndex].handTile
            } else {
                rootService.currentGame!!.currentTurn.gameField.field[posX][posY] =
                    rootService.currentGame!!.currentTurn.gameField.tileStack.tiles.removeFirst()
            }
        }

        rootService.gameService.nextPlayer()
    }

    fun rotate(tile: Tile) {
        tile.rotationDegree += 1
    }
    /**
     * Check if the position at (posX, posY) is legal to place a tile on.
     *
     * @param posX The x-coordinate of the position to check.
     * @param posY The y-coordinate of the position to check.
     * @return True if the position is legal to place a tile on, false otherwise.
     */
    fun isPositionLegal(posX: Int, posY: Int) : Boolean {
            val player : Player? = null
            val isFree = isSpotFree(posX,posY)
            val boardEdge = isConnectedToEdgeOfBoard(posX, posY)
            val tileEdge = isConnectedToTile(posX, posY)
            if (boardEdge || tileEdge) {
                if(isFree) {
                    player!!.paths.forEach { path ->
                        if ((path.tiles.size > 1 && path.complete) || player.handTile == null) {

                            return true
                        }
                    }
                }
            }
            return false

    }
    /**
     * Check if the spot at (posX, posY) is free.
     *
     * @param posX The x-coordinate of the spot to check.
     * @param posY The y-coordinate of the spot to check.
     * @return True if the spot is free, false if it is already occupied.
     */
    private fun isSpotFree(posX: Int, posY: Int): Boolean {
        rootService.currentGame?.currentTurn?.gameField?.tiles?.forEach { tile ->
            if (tile.posX == posX && tile.posY == posY) {
                return false
            }
        }
        return true
    }
    /**
     * Check if the spot at (posX, posY) is connected to the edge of the board.
     *
     * @param posX The x-coordinate of the spot to check.
     * @param posY The y-coordinate of the spot to check.
     * @return True if the spot is connected to the edge of the board, false otherwise.
     */
    private fun isConnectedToEdgeOfBoard(posX: Int, posY: Int): Boolean {
        if (  (posX == 0) && (posY in 0..7)
            ||(posX == 7) && (posY in 0..7)
            ||(posY == 0) && (posX in 0..7)
            ||(posY == 7) && (posX in 0..7)){
            return true
        }
        return false
    }
    /**
     * Check if there is an adjacent tile at the spot (posX, posY).
     *
     * @param posX The x-coordinate of the spot to check.
     * @param posY The y-coordinate of the spot to check.
     * @return True if there is an adjacent tile at the spot, false otherwise.
     */
    private fun isConnectedToTile(posX: Int, posY: Int): Boolean {

        rootService.currentGame?.currentTurn?.gameField?.tiles?.forEach { tile ->
            if (   tile.posX + 1 == posX
                || tile.posX - 1 == posX
                || tile.posY + 1 == posY
                || tile.posY - 1 == posY) {

                return true
            }
        }
        return false
    }

    fun buildPaths (player : Player, placedTile: Tile) {
        var checkAgain = false
        val currentGame = rootService.currentGame
        checkNotNull(currentGame)
        for (path in player.paths) {

            if (path.complete) continue
            checkAgain = true
            while(checkAgain) {
                var lastPort = path.lastPort
                var inPort = 0

                checkAgain = false
                if (path.tiles.isNotEmpty()) {

                    val lastTile = path.tiles.last()
                    if ((lastPort == 2 || lastPort == 3) && checkRight(placedTile,lastTile)) {
                        path.tiles.add(placedTile)
                        inPort = if (lastPort == 2) 7
                        else 6
                        for (i in placedTile.ports)
                        {
                            if (i.first == inPort)
                            {
                                path.lastPort = i.second
                                break
                            }
                        }
                        checkAgain = true
                    }
                    if ((lastPort == 0 || lastPort == 1) && checkTop(placedTile,lastTile)) {
                        path.tiles.add(placedTile)
                        inPort = if (lastPort == 0) 5
                        else 4
                        for (i in placedTile.ports)
                        {
                            if (i.first == inPort)
                            {
                                path.lastPort = i.second
                                break
                            }
                        }
                        checkAgain = true
                    }
                    if ((lastPort == 6 || lastPort == 7) && checkLeft(placedTile,lastTile)) {
                        path.tiles.add(placedTile)
                        inPort = if (lastPort == 6) 3
                        else 2
                        for (i in placedTile.ports)
                        {
                            if (i.first == inPort)
                            {
                                path.lastPort = i.second
                                break
                            }
                        }
                        checkAgain = true
                    }
                    if ((lastPort == 4 || lastPort == 5) && checkBottom(placedTile,lastTile)) {
                        path.tiles.add(placedTile)
                        inPort = if (lastPort == 4) 1
                        else 0
                        for (i in placedTile.ports)
                        {
                            if (i.first == inPort)
                            {
                                path.lastPort = i.second
                                break
                            }
                        }
                        checkAgain = true
                    }

                }

                if (placedTile.posX == 0 ||
                    placedTile.posX == 7 ||
                    placedTile.posY == 0 ||
                    placedTile.posY == 7 ) {
                    path.tiles.add(placedTile)
                     inPort = when (path.startPos) {

                        1,2,3,4,5,6,7,8 -> 4
                        9,10,11,12,13,14,15,16 -> 2
                        17,18,19,20,21,22,23,24 -> 0
                        25,26,27,28,29,30,31,32 -> 6
                        else -> throw IllegalStateException("Invalid start position")
                    }
                     lastPort = placedTile.ports[inPort].second
                    checkAgain = true
                }
            }
            if (path.tiles.last().equals(path.startPos)) { //mit x und y

                player.score += path.tiles.count()
                path.complete = true
            }
            if (      (path.tiles.last().posX==4 && path.tiles.last().posY==4)
                    ||(path.tiles.last().posX==4 && path.tiles.last().posY==5)
                    ||(path.tiles.last().posX==5 && path.tiles.last().posY==4)
                    ||(path.tiles.last().posX==5 && path.tiles.last().posY==5))
                {player.score += path.tiles.count()}
            }
        }


    private fun checkRight(placedTile: Tile, lastTile: Tile):Boolean
    {
        return (placedTile.posY == lastTile.posY+1)&&(placedTile.posX == lastTile.posX)
    }
    private fun checkLeft(placedTile: Tile, lastTile: Tile):Boolean
    {
        return (placedTile.posY == lastTile.posY-1)&&(placedTile.posX == lastTile.posX)
    }
    private fun checkTop(placedTile: Tile, lastTile: Tile):Boolean
    {
        return (placedTile.posY == lastTile.posY)&&(placedTile.posX == lastTile.posX+1)
    }
    private fun checkBottom(placedTile: Tile, lastTile: Tile):Boolean
    {
        return (placedTile.posY == lastTile.posY)&&(placedTile.posX == lastTile.posX-1)
    }

}