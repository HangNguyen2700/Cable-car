package entity

data class GameField(var stations : MutableList<Station>, var tiles : MutableList<Tile>, var tileStack : TileStack) {
    var field: Array<Array<Tile?>> = Array(10) { Array(10) {null} }

    fun copy(): GameField {
        val nGameField = GameField(stations.toMutableList(), tiles.toMutableList(), tileStack.copy())
        for ((i, arr) in field.withIndex()) {
            for ((j, tile) in arr.withIndex()) {
                nGameField.field[i][j] = tile?.copy()
            }
        }
        return nGameField
    }
}