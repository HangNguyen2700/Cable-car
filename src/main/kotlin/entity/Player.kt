package entity

data class Player(var name : String, var cars : MutableList<Int>) {


    var color : Color? = null
    var handTile : Tile? = null
    var score = 0
    var paths = mutableListOf<Path>()

}