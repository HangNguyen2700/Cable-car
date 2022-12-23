package entity

data class Player(val name : String) {

    val cars = mutableListOf<Int>()
    var color : Color? = null
    var paths = mutableListOf<Path>()
    var handTile : Tile? = null
    var score = 0


}