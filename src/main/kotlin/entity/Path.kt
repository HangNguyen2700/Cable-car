package entity

data class Path(val tiles : MutableList<Tile>, val lastPort : Int, val complete : Boolean, val startPos : Int) {
}