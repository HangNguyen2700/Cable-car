package entity

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import tools.aqua.bgw.visual.ColorVisual

class ColorTest {
    @Test
    fun testToString() {
        assertEquals("Yellow", Color.YELLOW.toString())
        assertEquals("Blue", Color.BLUE.toString())
        assertEquals("Orange", Color.ORANGE.toString())
        assertEquals("Green", Color.GREEN.toString())
        assertEquals("Purple", Color.PURPLE.toString())
        assertEquals("Black", Color.BLACK.toString())
    }

    @Test
    fun testToRGB() {
        assertEquals(ColorVisual.YELLOW, Color.YELLOW.toRGB())
        assertEquals(ColorVisual.BLUE, Color.BLUE.toRGB())
        assertEquals(ColorVisual.ORANGE, Color.ORANGE.toRGB())
        assertEquals(ColorVisual.GREEN, Color.GREEN.toRGB())
        assertEquals(ColorVisual(183, 0, 255), Color.PURPLE.toRGB())
        assertEquals(ColorVisual.BLACK, Color.BLACK.toRGB())
    }
}
