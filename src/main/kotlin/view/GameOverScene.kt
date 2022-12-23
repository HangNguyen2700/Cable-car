package view

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.CompoundVisual
import tools.aqua.bgw.visual.TextVisual
import java.awt.Color

/**
 *
 * GameOverScene displays score after game over.
 * [headLineLabel] End Game Header
 * [p1Name],[p2Name],[p3Name],[p4Name],[p5Name],[p6Name] aligns players Name
 * [first],[second],[third],[fourth],[fifth],[sixth] aligns score
 * [crown] crown and crown position
 * [restartButton] restarts the current game
 * [mainMenuButton] return to Main Menu
 * [quitButton] exit the game
 *
 */

class GameOverScene : BoardGameScene(1920,1080) {

    private val headLineLabel=Label(width = 300, height = 50, posX = 50, posY = 180,
        text = "End game",
        font = Font(size=24, color = Color.WHITE, family ="Tourney", fontStyle = Font.FontStyle.ITALIC))

    private var p1Name=Label(width = 300, height = 35, posX = 50, posY = 616,
        font = Font(size=24, color = Color.WHITE, family ="Tourney", fontStyle = Font.FontStyle.ITALIC))
    private var p2Name=Label(width=300,height=35,posX=50,posY=731,
        font = Font(size=24, color = Color.WHITE, family ="Tourney", fontStyle = Font.FontStyle.ITALIC))
    private var p3Name=Label(width=300,height=35,posX=50,posY=837,
        font = Font(size=24, color = Color.WHITE, family ="Tourney", fontStyle = Font.FontStyle.ITALIC))
    private var p4Name=Label(width=300,height=35,posX=50,posY=751,
        font = Font(size=24, color = Color.WHITE, family ="Tourney", fontStyle = Font.FontStyle.ITALIC))
    private var p5Name=Label(width=300,height=35,posX=50,posY=585,
        font = Font(size=24, color = Color.WHITE, family ="Tourney", fontStyle = Font.FontStyle.ITALIC))
    private var p6Name=Label(width=300,height=35,posX=50,posY=457,
        font = Font(size=24, color = Color.WHITE, family ="Tourney", fontStyle = Font.FontStyle.ITALIC))

    private val first=Label(width=300,height=35,posX=292,posY=616,
        font = Font(size=24, color = Color.WHITE, family ="Tourney", fontStyle = Font.FontStyle.ITALIC))
    private val second=Label(width=300,height=35,posX=50,posY=731,
        font = Font(size=24, color = Color.WHITE, family ="Tourney", fontStyle = Font.FontStyle.ITALIC))
    private val third=Label(width=300,height=35,posX=50,posY=837,
        font = Font(size=24, color = Color.WHITE, family ="Tourney", fontStyle = Font.FontStyle.ITALIC))
    private val fourth=Label(width=300,height=35,posX=50,posY=751,
        font = Font(size=24, color = Color.WHITE, family ="Tourney", fontStyle = Font.FontStyle.ITALIC))
    private val fifth=Label(width=300,height=35,posX=50,posY=585,
        font = Font(size=24, color = Color.WHITE, family ="Tourney", fontStyle = Font.FontStyle.ITALIC))
    private val sixth=Label(width=300,height=35,posX=50,posY=457,
        font = Font(size=24, color = Color.WHITE, family ="Tourney", fontStyle = Font.FontStyle.ITALIC))

    private val restartButton = Button(width = 223, height = 70, posX = 181, posY = 720,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri", fontStyle = Font.FontStyle.ITALIC),
                text = "Restart Game")))

    private val mainMenuButton = Button(width = 223, height = 70, posX = 181, posY = 823,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri", fontStyle = Font.FontStyle.ITALIC),
                text = "Main Menu")))

    val quitButton = Button(width = 223, height = 70, posX = 181, posY = 823,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri", fontStyle = Font.FontStyle.ITALIC),
                text = "Quit")))

    private val crown=Button(
        width=100, height = 50, posX = 894, posY = 346,
        //visual = ImageVisual("crown.png")
    )
    init{
        addComponents(
            restartButton, mainMenuButton,quitButton,
            p1Name, p2Name,
            crown,
            first, second
        )
    }

}