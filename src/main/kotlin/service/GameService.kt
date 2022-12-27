package service

import entity.Color
import entity.Player
import entity.Turn


class GameService(private val rootService: RootService) : AbstractRefreshingService() {

    fun startNewGame(players: List<Player>) {

    }

    private var gameCounter = 0

    private fun gamePointer(): MutableList<Turn> {
        val gameStates: MutableList<Turn> = mutableListOf()
        gameStates.add(rootService.currentGame!!.currentTurn.copy())
        gameCounter = gameStates.size - 1
        return gameStates
    }
    /**
     * Reverts the game to the previous state. If the current game state has been modified,
     * removes all future game states from the game history.
     *
     * @throws IllegalArgumentException if there is no previous game state.
     */
    fun undo() {
        if (gameCounter > 0) {
            val previousGameState = gamePointer()[gameCounter - 1]
            if (rootService.currentGame!!.currentTurn != previousGameState) {
                // Current game state has been modified, clear future game states
                gamePointer().subList(gameCounter, gamePointer().size).clear()
            }
            gameCounter--
            rootService.currentGame!!.currentTurn = previousGameState
        } else {
            throw IllegalArgumentException("There is no previous game")
        }
    }

    /**
     * Moves the game to the next state.
     *
     * @throws IllegalArgumentException if there is no next game state.
     */
    fun redo() {
        if (gameCounter < gamePointer().size - 1) {
            gameCounter++
            rootService.currentGame!!.currentTurn = gamePointer()[gameCounter]
        } else {
            throw IllegalArgumentException("There is no after game")
        }
    }

    fun nextPlayer() {

    }

    /**
     * findWinner() is a function that finds the winner of the current game.
     *
     * @return a mutable list of Player objects representing the winners of the current game.
     */
    fun findWinner(): MutableList<Player> {

        // Get the maximum score of the players in the current game
        val maxScore = rootService.currentGame!!.currentTurn.players.maxOf { it.score }

        // Create a mutable list to store the winners
        val winners = mutableListOf<Player>()

        // Iterate through the players in the current game
        for (player in rootService.currentGame!!.currentTurn.players) {
            // If the player has the maximum score, add them to the winners list
            if (maxScore == player.score) {
                winners.add(player)
            }
        }
        // Return the winners list
        return winners
    }



    fun endGame() {

    }

    private fun distributeTiles() {


    }
    /**
     * Assigns number of cars and colors to the players based on the number of players.
     *
     * The number of cars and colors are assigned as follows:
     * - For 2 players: Yellow and Blue, alternating positions
     * - For 3 players: Yellow, Blue, and Orange
     * - For 4 players: Yellow, Blue, Orange, and Green
     * - For 5 players: Yellow, Blue, Orange, Green, and Purple
     * - For 6 players: Yellow, Blue, Orange, Green, Purple, and Black
     *
     */
    private fun playersToPositions()
    {
        val players  = rootService?.currentGame?.currentTurn?.players
        val colors = listOf(Color.YELLOW, Color.BLUE, Color.ORANGE, Color.GREEN, Color.PURPLE, Color.BLACK)
        when (players?.size) {
            2 ->{
                for (i in 1 until 33) {
                    if (i % 2 == 1) {
                        players[0].color = Color.YELLOW
                        players[0].cars = mutableListOf(i)

                    }
                    else {
                        players[1].color = Color.BLUE
                        players[1].cars = mutableListOf(i)
                    }
                }

            }
            3 -> {
                players[0].cars = mutableListOf(1 , 4, 6, 11, 15, 20, 23, 25, 28, 31)
                players[1].cars = mutableListOf(2, 7, 9, 12, 14, 19, 22, 27, 29, 32)
                players[2].cars = mutableListOf(3, 5, 8, 10, 13, 18, 21, 24, 26, 30)
                for ((index, player) in players.withIndex()) {
                    player.color = colors[index]
                }

            }
            4 ->  {
                players[0].cars = mutableListOf(4, 7, 11, 16, 20, 23, 27, 32)
                players[1].cars = mutableListOf(3, 8, 12, 15, 19, 24, 28, 31)
                players[2].cars = mutableListOf(1, 6, 10, 13, 18, 21, 25, 30)
                players[3].cars = mutableListOf(2, 5, 9, 14, 17, 22, 26, 29)
                for ((index, player) in players.withIndex()) {
                    player.color = colors[index]
                }
            }
            5 ->  {
                players[0].cars = mutableListOf(1, 5, 10, 14, 22, 28)
                players[1].cars = mutableListOf(6, 12, 18, 23, 27, 32)
                players[2].cars = mutableListOf(3, 7, 15, 19, 25, 29)
                players[3].cars = mutableListOf(2, 9, 13, 21, 26, 30)
                players[4].cars = mutableListOf(4, 8, 11, 20, 24, 31)
                for ((index, player) in players.withIndex()) {
                    player.color = colors[index]
                }
            }
            6 ->  {
                players[0].cars = mutableListOf(1, 5, 10, 19, 27)
                players[1].cars = mutableListOf(2, 11, 18, 25, 29)
                players[2].cars = mutableListOf(4, 8, 14, 21, 26)
                players[3].cars = mutableListOf(6, 15, 20, 24, 31)
                players[4].cars = mutableListOf(3, 9, 13, 23, 30)
                players[5].cars = mutableListOf(7, 12, 22, 28, 32)
                for ((index, player) in players.withIndex()) {
                    player.color = colors[index]
                }
            }
        }

    }
}