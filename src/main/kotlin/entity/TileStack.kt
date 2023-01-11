package entity

data class TileStack(var tiles : MutableList<Tile>) {
    fun copy(): TileStack {
        return TileStack(tiles.toMutableList())
    }
}