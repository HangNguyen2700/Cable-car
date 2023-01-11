package entity

data class Turn(var gameField: GameField, var players: MutableList<Player>) {

    var previousTurn : Turn? = null
    var nextTurn : Turn? = null
    var currentPlayerIndex : Int = 0

    fun copy(): Turn{
        val nTurn = Turn(gameField.copy(), players.toMutableList())
        nTurn.currentPlayerIndex = currentPlayerIndex
        return nTurn
    }
}