package view

import tools.aqua.bgw.components.layoutviews.GridPane
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.UIComponent
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

class CreditsScene : BoardGameScene(1920,1080) {
    private val mainGrid: GridPane<GridPane<UIComponent>> = GridPane( 950, 520, columns = 1, rows = 4)
    private val creditsGrid = GridPane<UIComponent>(columns = 1, rows = 1)
    private val membersGrid = GridPane<UIComponent>(columns = 1, rows = 8)
    private val thanksGrid = GridPane<UIComponent>(columns = 1, rows = 1)
    private val buttonsGrid = GridPane<UIComponent>(columns = 3, rows = 1)

    private val labelFont = Font(50, Color.WHITE)
    private val buttonTextFont = Font(30, color = Color.WHITE)

    private val creditsLabel = Label(
        text = "CREDITS",
        height = 200,
        width = 1000,
        font = labelFont,
    )

    private val memberNames = listOf<String>("Leonard Sander", "Mohamed Aziz Feriani", "Jonah Hadt", "Tobias Grabert", "Altayebabdalsalam I. M. Ikhlawi",
        "Anastasiia Korzhylova", "Ashma Poudyal","Hang Nguyen Nguyen")

    private val membersLabel = mutableListOf<Label>()

    private val thanksLabel = Label(
        text = "SPECIAL THANKS",
        height = 200,
        width = 1000,
        font = labelFont,
    )

    val quitButton: Button = Button(
        width = 300, height = 100,
        text = "Quit",
        font = buttonTextFont,
    ).apply {
        visual = ColorVisual(194,64,64,255)
    }

    val musicButton: Button = Button(
        width = 300, height = 100,
        text = "Music",
        font = buttonTextFont
    ).apply {
        visual = ColorVisual(186,136,133,255)
    }

    val soundButton: Button = Button(
        width = 300, height = 100,
        text = "Sound",
        font = buttonTextFont
    ).apply {
        visual = ColorVisual(186,136,133,255)
    }

    init {
        creditsGrid[0,0] = creditsLabel
        mainGrid[0,0] = creditsGrid

        membersGrid.setColumnWidth(0,1080)
        for(i in 0..7){
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

        buttonsGrid.setColumnWidths(330)
        buttonsGrid[0,0] = quitButton
        buttonsGrid[1,0] = musicButton
        buttonsGrid[2,0] = soundButton
        mainGrid[0,3] = buttonsGrid

        background = ColorVisual(155,94,95,255)
        addComponents(mainGrid)
    }
}