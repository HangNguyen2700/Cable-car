package ai
import service.PlayerActionService.*
import edu.udo.cs.sopra.ntf.GameStateVerificationInfo
import edu.udo.cs.sopra.ntf.TurnMessage
import entity.Tile
import entity.Turn
import service.PlayerActionService
import java.lang.IllegalStateException

class AiActionService {
    companion object {
        /**
         * Checks if the game is over in the current turn.
         */
        fun isGameOver(turn: Turn) : Boolean {
            var isFieldFull = true
            for (row in turn.gameField.field) {
                for (cell in row) {
                    if (cell == null) {
                        isFieldFull = false
                        break
                    }
                }
            }
            return isFieldFull || turn.gameField.tileStack.tiles.isEmpty()
        }

        /**
         * Checks if position the AI chose for tile placement is legal.
         */
        fun isPositionLegal(turn: Turn, posX: Int, posY: Int): Boolean {
            fun isConnectedToTile(turn: Turn, posX: Int, posY: Int): Boolean {
                if (posX in 1..8 && posY == 1 ||
                    posX in 1..8 && posY == 8 ||
                    posY in 1..8 && posX == 1 ||
                    posY in 1..8 && posX == 8 ||
                    posX in 3..6 && posY == 3 ||
                    posX in 3..6 && posY == 6 ||
                    posY in 3..6 && posX == 3 ||
                    posY in 3..6 && posX == 6) return true

                val currentField = turn.gameField.field
                return (currentField[posX][posY + 1] != null ||
                        currentField[posX][posY - 1] != null ||
                        currentField[posX + 1][posY] != null ||
                        currentField[posX - 1][posY] != null)
            }

            val isFree = (turn.gameField.field[posX][posY] == null)
            return isFree && isConnectedToTile(turn, posX, posY)
        }

        /**
         * Simulates a game move from for a given player in the AI analysis.
         */
        fun doMove(turn: Turn, move: Move, playerIndex: Int) : Turn {
            // add new Turn
            val newTurn = turn.copy()
            var tile: Tile?

            // add tile to gameField
            if (!isPositionLegal(newTurn, move.posX, move.posY))
                throw IllegalStateException("Attempted tile placement is illegal!")

            if (!move.shouldDrawFromStack) {
                // tile from hand
                tile = newTurn.players[playerIndex].handTile
                // rotate tile if needed
                if (move.rotationsNo != 0) {
                    val tempId = tile!!.id
                    val tempOriginalPorts = tile.originalPorts

                    while (move.rotationsNo > tile!!.rotationDegree) {
                        val rotDeg = tile.rotationDegree
                        tile = PlayerActionService.rotate(tile)
                        tile.rotationDegree = (rotDeg + 1) % 4
                    }
                    tile.id = tempId
                    tile.originalPorts = tempOriginalPorts
                }
                tile!!.posX = move.posX
                tile.posY = move.posY

                // put tile onto Field
                newTurn.gameField.field[move.posX][move.posY] = tile

                // give player new tile from tileStack
                newTurn.players[playerIndex].handTile =
                    newTurn.gameField.tileStack.tiles.removeFirst()
            }
            else {
                // tile from tileStack
                tile = newTurn.gameField.tileStack.tiles.removeFirst()
                // rotate tile if needed
                if (move.rotationsNo != 0) {
                    val tempId = tile!!.id
                    val tempOriginalPorts = tile.originalPorts

                    while (move.rotationsNo > tile!!.rotationDegree) {
                        val rotDeg = tile.rotationDegree
                        tile = PlayerActionService.rotate(tile)
                        tile.rotationDegree = (rotDeg + 1) % 4
                    }
                    tile.id = tempId
                    tile.originalPorts = tempOriginalPorts
                }
                tile!!.posX = move.posX
                tile.posY = move.posY

                // remove tile from tileStack and put it onto the field
                newTurn.gameField.field[move.posX][move.posY] = tile
            }
            return newTurn
        }
    }


}