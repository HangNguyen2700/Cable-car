package entity

enum class Color {
    RED,
    BLUE,
    GREEN,
    PINK,
    WHITE,
    BLACK,
    ;

    override fun toString() = when(this) {
        RED -> "Red"
        BLUE -> "Blue"
        GREEN -> "Green"
        PINK -> "Pink"
        WHITE -> "White"
        BLACK -> "Black"
    }
}