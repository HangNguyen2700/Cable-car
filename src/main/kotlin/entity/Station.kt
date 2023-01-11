package entity

data class Station(val departure : Player) {

    val arrival : Player? = null

    fun copy(): Station {
        return Station(departure)
    }
}