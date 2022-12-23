package entity

import tools.aqua.bgw.visual.ColorVisual

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

    fun toRGB() = when(this) {
        YELLOW -> ColorVisual.YELLOW
        BLUE -> ColorVisual.BLUE
        ORANGE -> ColorVisual.ORANGE
        GREEN -> ColorVisual.GREEN
        PURPLE -> ColorVisual(183,0,255)
        BLACK -> ColorVisual.BLACK
    }
}