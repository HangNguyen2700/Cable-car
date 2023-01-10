package view

import tools.aqua.bgw.animation.MovementAnimation
import tools.aqua.bgw.components.layoutviews.GridPane
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.UIComponent
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.CompoundVisual
import tools.aqua.bgw.visual.ImageVisual
import tools.aqua.bgw.visual.TextVisual
import java.awt.Color

/**
 *[mainGrid]:frame for the contents
 *[membersGrid]:frame for the members
 */

class CreditsScene : BoardGameScene(1920,1080) {

    private val mainGrid: GridPane<GridPane<UIComponent>> = GridPane( 950, 1500, columns = 1, rows = 4)
    private val creditsGrid = GridPane<UIComponent>(columns = 1, rows = 1)
    private val membersGrid = GridPane<UIComponent>(columns = 1, rows = 15)
    private val thanksGrid = GridPane<UIComponent>(columns = 1, rows = 1)

    private val labelFont = Font(50, Color.WHITE)

    private val creditsLabel = Label(height = 200, width = 1000, font = labelFont, text = "CREDITS")

    private val memberNames = listOf(
        "Leonard Sander", "", "Mohamed Aziz Feriani", "", "Jonah Hadt", "",
        "Tobias Grabert", "", "Altayebabdalsalam I. M. Ikhlawi",  "",
        "Anastasiia Korzhylova", "", "Ashma Poudyal", "", "Hang Nguyen Nguyen")

    private val membersLabel = mutableListOf<Label>()

    private val thanksLabel = Label(height = 200, width = 1000, font = labelFont, text = "SPECIAL THANKS")

    val backToTitleSceneButton = Button(width = 400, height = 100, posX = 100, posY = 930,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(font = Font(size = 60, color = Color.RED, family = "Calibri"), text = "Back to Title")))

    var timesClicked : Int = 0

    val musicToggleButton = Button(width = 140, height = 140, posX = 1720, posY = 880,
        visual = ImageVisual("music_enabled.png"))

    val soundToggleButton = Button(width = 140, height = 140, posX = 1520, posY = 880,
        visual = ImageVisual("sound_enabled.png"))

    init {
        creditsGrid[0,0] = creditsLabel
        mainGrid[0,0] = creditsGrid

        membersGrid.setColumnWidth(0,1080)
        for(i in 0..13){
            val memberLabel = Label(width = 1920, height = 1080,
                text = memberNames[i],
                font = labelFont
            )
            membersLabel.add(memberLabel)
            membersGrid[0,i] = membersLabel[i]
            membersGrid.setRowHeight(i,70)
        }
        mainGrid[0,1] = membersGrid

        thanksGrid[0,0] = thanksLabel
        mainGrid[0,2] = thanksGrid

        background = ColorVisual(0,0,0)
        addComponents(mainGrid,backToTitleSceneButton, soundToggleButton, musicToggleButton)
    }

    fun trigger() {
        println("trigger credits roll animation")
        playAnimation(MovementAnimation(
            componentView = creditsGrid,
            byY = -2000,
            duration = 20000
        ))

        playAnimation(MovementAnimation(
            componentView = membersGrid,
            byY = -2000,
            duration = 20000
        ))
    }
}