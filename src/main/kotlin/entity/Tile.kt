package entity

data class Tile(val ports : MutableList<Pair<Int,Int>>) {

    var rotationDegree = 0
    var posX = 0
    var posY = 0

    fun copy(): Tile {
        val nTile = Tile(ports.toMutableList())
        nTile.rotationDegree = rotationDegree
        nTile.posX = posX
        nTile.posY = posY
        return nTile
    }
}
