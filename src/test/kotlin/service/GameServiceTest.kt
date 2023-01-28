package service

import com.soywiz.korio.dynamic.KDynamic.Companion.int
import entity.Player
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import service.*
import entity.*

import org.junit.jupiter.api.Assertions.*

internal class GameServiceTest {
    private lateinit var rootService : RootService
    private lateinit var gameService : GameService
    private lateinit var playerActionService: PlayerActionService

    @BeforeEach
    fun setUp() {
        rootService = RootService()
        gameService = rootService.gameService

        playerActionService = rootService.playerActionService
    }

    @Test
    fun nextPlayer() {
        //var c = rootService.currentGame!!.currentTurn.players
        val p1 : Player = Player("sam")
        p1.cars = mutableListOf(1,2,3,4,5,6)
        p1.color= Color.GREEN
        p1.score = 12

        val p2 : Player = Player("games")
        p2.cars = mutableListOf(11,12,14,15,16)
        p2.color= Color.YELLOW
        p2.score = 12
        val p3 : Player = Player("mark")
        p3.cars = mutableListOf(7,8,9,10,20,22)
        p3.color= Color.BLACK
        p3.score = 12
        var players = mutableListOf<Player>(p1,p2,p3)
        assertTrue(players[0].name == "sam")

        assertTrue(==1 )
        /*
        c = mutableListOf<Player>(p1,p2,p3)
        var current = 0
        //current!!.currentTurn.players = players
        current = c[0].int
        println(c.size)
        println(current)

         */
    }
}