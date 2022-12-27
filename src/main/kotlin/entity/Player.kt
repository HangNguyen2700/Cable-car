package entity

data class Player(val name : String) {

    var cars = mutableListOf<Int>()
    var color : Color? = null
    var paths = mutableListOf<Path>()
    var handTile : Tile? = null
    var score = 0


}