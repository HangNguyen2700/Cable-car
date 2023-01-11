package entity

data class Player(val name : String) {

    var cars = mutableListOf<Int>()
    var color : Color? = null
    var paths = mutableListOf<Path>()
    var handTile : Tile? = null
    var score = 0

    fun copy(): Player {
        val nPlayer = Player(name)
        nPlayer.cars = cars.toMutableList()
        nPlayer.color = color
        nPlayer.paths = paths.toMutableList()
        nPlayer.handTile = handTile
        nPlayer.score = score
        return nPlayer
    }

}