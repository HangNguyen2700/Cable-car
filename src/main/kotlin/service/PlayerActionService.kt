package service

import edu.udo.cs.sopra.ntf.GameStateVerificationInfo
import edu.udo.cs.sopra.ntf.TurnMessage
import entity.Player
import entity.Tile

class PlayerActionService(private val rootService: RootService) : AbstractRefreshingService() {

    fun placeTile(fromHand: Boolean, posX: Int, posY: Int) {
        // add new Turn
        val newTurn = rootService.currentGame!!.currentTurn.copy()
        rootService.currentGame!!.currentTurn.nextTurn = newTurn
        newTurn.previousTurn = rootService.currentGame!!.currentTurn

        rootService.currentGame!!.currentTurn = newTurn


        if (!rootService.gameService.isLocalOnlyGame) {
            // network game, so a turnMessage has to be sent
            var tile: Tile? = null
            if (fromHand) {
                tile =
                    rootService.currentGame!!.currentTurn.players[rootService.currentGame!!.currentTurn.currentPlayerIndex].handTile
                // give player next tile
                rootService.currentGame!!.currentTurn.players[rootService.currentGame!!.currentTurn.currentPlayerIndex].handTile =
                    rootService.currentGame!!.currentTurn.gameField.tileStack.tiles.removeFirst()
            } else {
                tile = rootService.currentGame!!.currentTurn.gameField.tileStack.tiles.removeFirst()
            }
            rootService.networkService.sendTurnMessage(
                TurnMessage(
                    posX, posY,
                    !fromHand,
                    rootService.gameService.tileLookUp.indexOf(tile),
                    GameStateVerificationInfo(listOf(), listOf(), listOf())
                )
            )
        }

        // add tile to gameField
        if (isPositionLegal(posX, posY)) {
            rootService.currentGame!!.currentTurn.gameField.field[posX][posY]
            if (fromHand) {
                rootService.currentGame!!.currentTurn.gameField.field[posX][posY] =
                    rootService.currentGame!!.currentTurn.players[rootService.currentGame!!.currentTurn.currentPlayerIndex].handTile
                rootService.currentGame!!.currentTurn.players[rootService.currentGame!!.currentTurn.currentPlayerIndex].handTile =
                    rootService.currentGame!!.currentTurn.gameField.tileStack.tiles.removeFirst()
            } else {
                rootService.currentGame!!.currentTurn.gameField.field[posX][posY] =
                    rootService.currentGame!!.currentTurn.gameField.tileStack.tiles.removeFirst()
            }
        }

        rootService.gameService.nextPlayer()
    }

    /**
     * @author Jonah
     * Rotate a tile 90 degrees clockwise Sense
     *
     * @param tile The tile which should rotate
     */
    fun rotate(tile: Tile) {
        tile.rotationDegree = (tile.rotationDegree + 1) % 4

        for (i in 0 until 4 ) {
            val tempPortTuple : Pair<Int,Int> = tile.ports[i]

            var tempFirst = tempPortTuple.copy().first
            tempFirst = (tempFirst + 2) % 8

            var tempSecond= tempPortTuple.copy().second
            tempSecond = (tempSecond + 2) % 8

            tile.ports[i] = Pair(tempFirst, tempSecond)
        }

    }

    /**
     * @author Ikhlawi
     * Check if the position at (posX, posY) is legal to place a tile on.
     *
     * @param posX The x-coordinate of the position to check.
     * @param posY The y-coordinate of the position to check.
     * @return True if the position is legal to place a tile on, false otherwise.
     */
     fun isPositionLegal(posX: Int, posY: Int): Boolean {
        /*
        val player: Player? = null
        val isFree = isSpotFree(posX, posY)
        val tileEdge = isConnectedToTile(posX, posY)

        if (isFree) {
            if (tileEdge) {
                /*for (path in player!!.paths) {
                    rootService.gameService.nextPlayer()
                    if ((path.tiles.size > 1 && path.complete) || rootService.currentGame!!.currentTurn.players[rootService.currentGame!!.currentTurn.currentPlayerIndex].handTile == null) {
                        return true
                    }
                }*/
            return true
            }
        }
        */
        return true
    }

    /**
     * @author Ikhlawi
     * Check if the spot at (posX, posY) is free.
     *
     * @param posX The x-coordinate of the spot to check.
     * @param posY The y-coordinate of the spot to check.
     * @return True if the spot is free, false if it is already occupied.
     */
    private fun isSpotFree(posX: Int, posY: Int): Boolean {

       return rootService.currentGame!!.currentTurn.gameField.field[posX][posY] == null
    }

    /**
     * @author Ikhlawi
     * Check if there is an adjacent tile at the spot (posX, posY).
     *
     * @param posX The x-coordinate of the spot to check.
     * @param posY The y-coordinate of the spot to check.
     * @return True if there is an adjacent tile at the spot, false otherwise.
     */
    private fun isConnectedToTile(posX: Int, posY: Int): Boolean {

        var currentField = rootService.currentGame!!.currentTurn.gameField.field

        return (currentField[posX][posY + 1] != null ||
                currentField[posX][posY - 1] != null ||
                currentField[posX + 1][posY] != null ||
                currentField[posX - 1][posY] != null)
    }
    /**
     * @author Ikhlawi
     *
     * */
    fun buildPaths(player: Player, placedTile: Tile) {
        var checkAgain = false
        val currentGame = rootService.currentGame
        checkNotNull(currentGame)
        for (path in player.paths) {

            if (path.complete) continue
            checkAgain = true
            while (checkAgain) {
                var lastPort = path.lastPort
                var inPort = 0

                checkAgain = false
                if (path.tiles.isNotEmpty()) {

                    val lastTile = path.tiles.last()
                    if ((lastPort == 2 || lastPort == 3) && checkRight(placedTile, lastTile)) {
                        path.tiles.add(placedTile)
                        inPort = if (lastPort == 2) 7
                        else 6
                        for (i in placedTile.ports) {
                            if (i.first == inPort) {
                                path.lastPort = i.second
                                break
                            }
                        }
                        checkAgain = true
                    }
                    if ((lastPort == 0 || lastPort == 1) && checkTop(placedTile, lastTile)) {
                        path.tiles.add(placedTile)
                        inPort = if (lastPort == 0) 5
                        else 4
                        for (i in placedTile.ports) {
                            if (i.first == inPort) {
                                path.lastPort = i.second
                                break
                            }
                        }
                        checkAgain = true
                    }
                    if ((lastPort == 6 || lastPort == 7) && checkLeft(placedTile, lastTile)) {
                        path.tiles.add(placedTile)
                        inPort = if (lastPort == 6) 3
                        else 2
                        for (i in placedTile.ports) {
                            if (i.first == inPort) {
                                path.lastPort = i.second
                                break
                            }
                        }
                        checkAgain = true
                    }
                    if ((lastPort == 4 || lastPort == 5) && checkBottom(placedTile, lastTile)) {
                        path.tiles.add(placedTile)
                        inPort = if (lastPort == 4) 1
                        else 0
                        for (i in placedTile.ports) {
                            if (i.first == inPort) {
                                path.lastPort = i.second
                                break
                            }
                        }
                        checkAgain = true
                    }

                }

                if (placedTile.posX == 1 ||
                    placedTile.posX == 8 ||
                    placedTile.posY == 1 ||
                    placedTile.posY == 8
                ) {
                    path.tiles.add(placedTile)
                    inPort = when (path.startPos) {

                        1, 2, 3, 4, 5, 6, 7, 8 -> 4
                        9, 10, 11, 12, 13, 14, 15, 16 -> 2
                        17, 18, 19, 20, 21, 22, 23, 24 -> 0
                        25, 26, 27, 28, 29, 30, 31, 32 -> 6
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
            if ((path.tiles.last().posX == 4 && path.tiles.last().posY == 4)
                || (path.tiles.last().posX == 4 && path.tiles.last().posY == 5)
                || (path.tiles.last().posX == 5 && path.tiles.last().posY == 4)
                || (path.tiles.last().posX == 5 && path.tiles.last().posY == 5)
            ) {
                player.score += path.tiles.count()
            }
        }
    }

    /**
     * @author Ikhlawi
     */
    private fun checkRight(placedTile: Tile, lastTile: Tile): Boolean {
        return (placedTile.posY == lastTile.posY + 1) && (placedTile.posX == lastTile.posX)
    }
    /**
     * @author Ikhlawi
     */
    private fun checkLeft(placedTile: Tile, lastTile: Tile): Boolean {
        return (placedTile.posY == lastTile.posY - 1) && (placedTile.posX == lastTile.posX)
    }
    /**
     * @author Ikhlawi
     */
    private fun checkTop(placedTile: Tile, lastTile: Tile): Boolean {
        return (placedTile.posY == lastTile.posY) && (placedTile.posX == lastTile.posX + 1)
    }
    /**
     * @author Ikhlawi
     */
    private fun checkBottom(placedTile: Tile, lastTile: Tile): Boolean {
        return (placedTile.posY == lastTile.posY) && (placedTile.posX == lastTile.posX - 1)
    }

}