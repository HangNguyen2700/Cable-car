package entity

data class Player(val name : String, val cars : MutableList<Int>) {

    init {
        var color : Color
        var paths = mutableListOf<Path>()
        var handTile : Tile
        var score = 0
    }

}