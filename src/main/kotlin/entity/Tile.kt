package entity

data class Tile(var ports : MutableList<Pair<Int,Int>>/*, val image : ImageVisual*/) {

    var rotationDegree = 0
    var posX : Int = 0
    var posY : Int = 0

}