package entity

data class Tile(val ports : MutableList<Pair<Int,Int>>/*, val image : ImageVisual*/) {

    init {
        var rotationDegree = 0
        var posX : Int
        var posY : Int

    }
}