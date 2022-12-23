package entity

data class Path(var tiles : MutableList<Tile>, var lastPort : Int,
                var complete : Boolean,
                var startPos : Int) {
}