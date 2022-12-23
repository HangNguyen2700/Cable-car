package entity

data class Turn(var gameField: GameField, var players: MutableList<Player>) {

    init {
        var previousTurn : Turn
        var nextTurn : Turn
        var playerIndex : Int
    }

}