package entity

enum class Color {
    YELLOW,
    BLUE,
    ORANGE,
    GREEN,
    PURPLE,
    BLACK
    ;

    override fun toString() = when(this) {
        YELLOW -> "Yellow"
        BLUE -> "Blue"
        ORANGE -> "Orange"
        GREEN -> "Green"
        PURPLE -> "Purple"
        BLACK -> "Black"
    }
}