package ai
import entity.Tile
import entity.Turn
import service.PlayerActionService
import java.lang.IllegalStateException

class AiActionService {
    companion object {
        /**
         * Checks if position the AI chose for tile placement is legal.
         */
        fun isPositionLegal(turn: Turn, posX: Int, posY: Int): Boolean {
            val isFree = (turn.gameField.field[posX][posY] == null)
            return isFree && PlayerActionService.isConnectedToTile(turn.gameField.field, posX, posY)
        }

        /**
         * Simulates a game move from for a given player in the AI analysis.
         */
        fun doMove(turn: Turn, move: Move, playerIndex: Int) : Turn {
            // add new Turn
            val newTurn = turn.copy()
            var tile: Tile?

            println("Draw from stack: " + move.shouldDrawFromStack)
            println("Stack is empty: " + turn.gameField.tileStack.tiles.isEmpty())
            println("Hand tile is null: " + (turn.players[playerIndex].handTile == null))

            // add tile to gameField
            if (!isPositionLegal(newTurn, move.posX, move.posY))
                throw IllegalStateException("Attempted tile placement is illegal!")

            if (!move.shouldDrawFromStack) {
                // tile from hand
                tile = newTurn.players[playerIndex].handTile
                if (tile != null) {
                    // rotate tile if needed
                    if (move.rotationsNo != 0) {
                        val tempId = tile.id
                        val tempOriginalPorts = tile.originalPorts

                        while (move.rotationsNo > tile!!.rotationDegree) {
                            val rotDeg = tile.rotationDegree
                            tile = PlayerActionService.rotate(tile)
                            tile.rotationDegree = (rotDeg + 1) % 4
                        }
                        tile.id = tempId
                        tile.originalPorts = tempOriginalPorts
                    }
                    tile.posX = move.posX
                    tile.posY = move.posY

                    // put tile onto Field
                    newTurn.gameField.field[move.posX][move.posY] = tile

                    // give player new tile from tileStack
                    if (newTurn.gameField.tileStack.tiles.isNotEmpty()){
                        newTurn.players[playerIndex].handTile = newTurn.gameField.tileStack.tiles.removeFirst()
                    }
                    else newTurn.players[playerIndex].handTile = null
                }
                else throw Exception("Hand tile is null!")
            }
            else {
                // tile from tileStack
                if (newTurn.gameField.tileStack.tiles.isNotEmpty()){
                    tile = newTurn.gameField.tileStack.tiles.removeFirst()

                    // rotate tile if needed
                    if (move.rotationsNo != 0) {
                        val tempId = tile.id
                        val tempOriginalPorts = tile.originalPorts

                        while (move.rotationsNo > tile!!.rotationDegree) {
                            val rotDeg = tile.rotationDegree
                            tile = PlayerActionService.rotate(tile)
                            tile.rotationDegree = (rotDeg + 1) % 4
                        }
                        tile.id = tempId
                        tile.originalPorts = tempOriginalPorts
                    }
                    tile.posX = move.posX
                    tile.posY = move.posY

                    // put tile onto the field
                    newTurn.gameField.field[move.posX][move.posY] = tile
                }
                else {
                    val newMove = Move(false, move.rotationsNo, move.posX, move.posY)
                    return doMove(newTurn, newMove, playerIndex)
                }
            }
            PlayerActionService.buildPathsAnastasiia(newTurn)
            return newTurn
        }
    }
}