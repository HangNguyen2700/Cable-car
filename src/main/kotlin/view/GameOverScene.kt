package view

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.CompoundVisual
import tools.aqua.bgw.visual.ImageVisual
import tools.aqua.bgw.visual.TextVisual
import java.awt.Color

/**
 *
 * GameOverScene displays score after game over.
 * [endGameLabel] End Game Header
 * [musicToggleButton],[soundToggleButton] : music and sound on/off
 * [p1Name],[p2Name],[p3Name],[p4Name],[p5Name],[p6Name] aligns players Name
 * [playerImg1],[playerImg1],[playerImg1],[playerImg1]:contains the image for players
 * [first],[second],[third] aligns Players Rank
 * [crown] crown and crown position
 * [restartButton] restarts the current game
 * [mainMenuButton] return to Main Menu
 * [quitButton] exit the game
 *
 */

class GameOverScene : BoardGameScene(1920, 1080) {

    private val labelFont = Font(60, Color.WHITE, family = "Calibri")
    private val playerLabelFont = Font(60, Color.WHITE, family = "Calibri", fontStyle = Font.FontStyle.OBLIQUE)

    private val endGameLabel = Label(
        height = 200, width = 400, posX = 800, font = labelFont,
        text = "End Game"
    )

    val musicToggleButton = Button(
        width = 140, height = 140, posX = 1620, posY = 880,
        visual = ImageVisual("music_enabled.png")
    )

    val soundToggleButton = Button(
        width = 140, height = 140, posX = 1320, posY = 880,
        visual = ImageVisual("sound_enabled.png")
    )

   //aligns player names
    private var p1Name = Label(
        width = 300, height = 35, posX = 175, posY = 675,
        font = playerLabelFont, text = "Loid"
    )
    private var p2Name = Label(
        width = 300, height = 35, posX = 425, posY = 675,
        font = playerLabelFont, text = "Anya"
    )
    private var p3Name = Label(
        width = 300, height = 35, posX = 675, posY = 675,
        font = playerLabelFont, text = "Yor"
    )
    private var p4Name = Label(
        width = 300, height = 35, posX = 925, posY = 675,
        font = playerLabelFont, text = "Franky"
    )
    private var p5Name = Label(
        width = 300, height = 35, posX = 1175, posY = 675,
        font = playerLabelFont, text = "Sylvia"
    )
    private var p6Name = Label(
        width = 300, height = 35, posX = 1425, posY = 675,
        font = playerLabelFont, text = "Henry"
    )

    //aligns player image
    private val playerImg1 = Label(
        width = 469, height = 469, posX = 425, posY = 275,
        visual = ImageVisual("Player1.png")
    )
    private val playerImg2 = Label(
        width = 469, height = 469, posX = 675, posY = 275,
        visual = ImageVisual("Player2.png")
    )
    private val playerImg3 = Label(
        width = 469, height = 469, posX = 925, posY = 275,
        visual = ImageVisual("Player3.png")
    )
    private val playerImg4 = Label(
        width = 469, height = 469, posX = 1175, posY = 275,
        visual = ImageVisual("Player4.png")
    )

    //aligns players Rank
    private val first = Label(
        width = 75, height = 35, posX = 675, posY = 225,
        visual = ImageVisual("1st.png")
    )
    private val second = Label(
        width = 75, height = 35, posX = 925, posY = 225,
        visual = ImageVisual("2nd.png")
    )
    private val third = Label(
        width = 75, height = 35, posX = 1175, posY = 225,
        visual = ImageVisual("3rd.png")
    )

    private val crown = Button(
        width = 100, height = 50, posX = 675, posY = 268,
        visual = ImageVisual("Crown.png")
    )

    private val restartButton = Button(
        width = 400, height = 100, posX = 181, posY = 880,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Restart Game"
            )
        )
    )

    // ? Back to main Menu via quit Button or mainMenu Button ?

    val mainMenuButton = Button(
        width = 400, height = 100, posX = 724, posY = 880,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Main Menu"
            )
        )
    )


    val quitButton = Button(
        width = 140, height = 140, posX = 1700, posY = 40,
        visual = ImageVisual("quit_button.png")
    )


    init {
        addComponents(
            endGameLabel,
            soundToggleButton, musicToggleButton,
            p1Name, p2Name, p3Name, p4Name, p5Name, p6Name,
            playerImg1, playerImg2, playerImg3, playerImg4,
            crown, first, second, third,
            restartButton, mainMenuButton, quitButton
        )
        background = ColorVisual(175, 238, 238)
        opacity = 0.4
    }


    /*
    override fun refreshAfterGameOver(){

        val rootService=RootService()
        val game=rootService.currentGame

        val winner:List<Player> = rootService.gameService.findWinner()

        p1Name.text=winner[0].name
        p2Name.text=winner[1].name
        p3Name.text=winner[2].name
        p4Name.text=winner[3].name
        p5Name.text=winner[4].name
        p6Name.text=winner[5].name

    }

     */

}