package entity

data class Turn(val gameField: GameField, val players: MutableList<Player>) {

    var previousTurn : Turn? = null
    var nextTurn : Turn? = null
    var playerIndex : Int? = null

}