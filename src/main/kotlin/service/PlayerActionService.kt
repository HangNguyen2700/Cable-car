package service

import entity.Player
import entity.Station
import entity.Tile

class PlayerActionService(private val rootService: RootService) : AbstractRefreshingService() {

    fun placeTile(player: Player, fromHand: Boolean, posX: Int, posY: Int) {

    }

    fun rotate(tile: Int) {

    }

    fun isPositionLegal(posX: Int, posY: Int) {

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
                    placedTile.posX == 8 ||
                    placedTile.posY == 0 ||
                    placedTile.posY == 8 ) {
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
            if ((path.tiles.last().posX==4 && path.tiles.last().posY==4)
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