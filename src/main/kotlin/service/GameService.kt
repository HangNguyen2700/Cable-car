package service

import entity.*
import java.io.File

class GameService(private val rootService: RootService) : AbstractRefreshingService() {
    var isLocalOnlyGame = false
    var isHostedGame = false
    var tileLookUp = mutableListOf<Tile>()

    fun startNewGame(players: List<String>) {
        if(rootService.currentGame == null) {
            rootService.currentGame = Game(
                Turn(
                    GameField(
                        mutableListOf(),
                        mutableListOf(),
                        TileStack(mutableListOf())
                    ), mutableListOf()
                )
            )
            // add given players
            for (player in players) {
                rootService.currentGame!!.currentTurn.players.add(Player(player))
            }

            readTileCSV()
            // TODO maybe remove if else
            if (isLocalOnlyGame) {
                distributeTiles()
                playersToPositions()
            } else {
                if (isHostedGame) {
                    distributeTiles()
                    playersToPositions()
                } else {
                    // joined game where init data is coming from network
                    distributeTiles()
                    playersToPositions()
                }
            }

        } else {
            println("a game is currently in progress")
        }
    }

    fun undo() {

    }

    fun redo() {

    }

    fun nextPlayer() {
        if(rootService.currentGame!!.currentTurn.currentPlayerIndex ==
            rootService.currentGame!!.currentTurn.players.size) {
            rootService.currentGame!!.currentTurn.currentPlayerIndex = 0
        } else {
            rootService.currentGame!!.currentTurn.currentPlayerIndex.inc()
        }
    }

    fun findWinner() {

    }


    fun endGame() {

    }

    private fun distributeTiles() {
        println("Tiles distributed to players")
        if(isLocalOnlyGame) {
            // we have to generate the drawStack and give each player a tile
            rootService.currentGame!!.currentTurn.gameField.tileStack.tiles = tileLookUp.toMutableList() // create copy
            rootService.currentGame!!.currentTurn.gameField.tileStack.tiles.shuffle()
            for (player in rootService.currentGame!!.currentTurn.players) {
                player.handTile = rootService.currentGame!!.currentTurn.gameField.tileStack.tiles.removeFirst()
            }
        } else if(isHostedGame) {
            // we have to generate the drawStack, create and send the gameInitMessage and then give each player a tile
            rootService.currentGame!!.currentTurn.gameField.tileStack.tiles = tileLookUp.toMutableList() // create copy
            rootService.currentGame!!.currentTurn.gameField.tileStack.tiles.shuffle()

            sendGameInitMessage(rootService.currentGame!!.currentTurn.gameField.tileStack.tiles)

            for (player in rootService.currentGame!!.currentTurn.players) {
                player.handTile = rootService.currentGame!!.currentTurn.gameField.tileStack.tiles.removeFirst()
            }
        } else {
            // this is a joined game, we only have to give each player a tile from GameField.TileStack
            for (player in rootService.currentGame!!.currentTurn.players) {
                player.handTile = rootService.currentGame!!.currentTurn.gameField.tileStack.tiles.removeFirst()
            }
        }
        println("Tiles distributed")
    }

    private fun sendGameInitMessage(drawStack: List<Tile>) {
        TODO("Not yet implemented")
    }

    private fun playersToPositions() {

    }

    fun readTileCSV() {
        // read file lines into lines array
        val file = File("./tiles.csv").inputStream()
        val reader = file.bufferedReader()
        val lines = mutableListOf<String>()
        reader.lineSequence().forEach {
            lines.add(it)
        }

        // add ints from lines array into 2D IntArray
        val matrix: Array<IntArray> = Array(60) { IntArray(8) }
        for (i in 0 until lines.size) {
            val lineSplit = lines[i].split(",")
            for (j in lineSplit.indices) {
                matrix[i][j] = lineSplit[j].toInt()
            }
        }

        // create PairList from matrix with row = tile
        val tilePairList: MutableList<MutableList<Pair<Int, Int>>> = mutableListOf()
        for (i in matrix.indices) {
            tilePairList.add(mutableListOf())
            for (j in 0..matrix[0].size-2 step 2) {
                tilePairList[i].add(Pair(matrix[i][j], matrix[i][j+1]))
            }
        }

        // create 1D tileList from 2D PairList
        val tileList = mutableListOf<Tile>()
        for (tilePairs in tilePairList) {
            var tile = Tile(tilePairs)
        }

        // create tileMap
        this.tileLookUp = tileList
    }
}