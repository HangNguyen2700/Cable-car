package entity

data class GameField(var stations : MutableList<Station>, var tiles : MutableList<Tile>, var tileStack : TileStack) {
    var field: Array<Array<Tile?>> = Array(10) { Array(10) {null} }
}