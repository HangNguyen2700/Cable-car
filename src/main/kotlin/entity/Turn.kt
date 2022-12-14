package entity

data class Turn(val gameField: GameField, val players: MutableList<Player>) {

    init {
        var previousTurn : Turn
        var nextTurn : Turn
        var playerIndex : Int
    }

}