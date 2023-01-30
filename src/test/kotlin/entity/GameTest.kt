package entity

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class GameTest {
    @Test
    fun testCurrentTurn() {
        val stations = mutableListOf<Station>()
        val tiles = mutableListOf<Tile>()
        val tileStack = TileStack(tiles)

        val gameField = GameField(stations, tiles, tileStack)
        val players = mutableListOf(Player("s",null), Player("s1",null), Player("s2",null))
        val turn = Turn(gameField, players)
        val game = Game(turn)

        assertEquals(turn, game.currentTurn)
    }
}
