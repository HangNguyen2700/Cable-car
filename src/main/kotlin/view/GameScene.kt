package view

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

class GameScene : BoardGameScene(1920,1080) {
    private val buttonTextFont = Font(30, color = Color.WHITE)

    val undoButton: Button = Button(
        width = 150, height = 50, posX = 430, posY = 975,
        text = "Undo",
        font = buttonTextFont
    ).apply {
        visual = ColorVisual(186,136,133,255)
        onMouseClicked = {}
    }

    val redoButton: Button = Button(
        width = 150, height = 50, posX = 600, posY = 975,
        text = "Redo",
        font = buttonTextFont
    ).apply {
        visual = ColorVisual(186,136,133,255)
        onMouseClicked = {}
    }

    private val playerCard = LabeledStackView(posX = 1400, posY = 975, label = "player's card").apply {
        onDragGestureEntered = {}
        onDragGestureExited = {}
    }

    private val otherPlayerCard1 = LabeledStackView(posX = 150, posY = 180, label = "other player's card 1")
    private val otherPlayerCard2 = LabeledStackView(posX = 150, posY = 190, label = "other player's card 2")
    private val otherPlayerCard3 = LabeledStackView(posX = 150, posY = 200, label = "other player's card 3")

    private val otherPlayerCard4 = LabeledStackView(posX = 50, posY = 400, label = "other player's card 4")
    private val otherPlayerCard5 = LabeledStackView(posX = 50, posY = 410, label = "other player's card 5")
    private val otherPlayerCard6 = LabeledStackView(posX = 50, posY = 420, label = "other player's card 6")

    private val otherPlayerCard7 = LabeledStackView(posX = 150, posY = 620, label = "other player's card 7")
    private val otherPlayerCard8 = LabeledStackView(posX = 150, posY = 630, label = "other player's card 8")
    private val otherPlayerCard9 = LabeledStackView(posX = 150, posY = 640, label = "other player's card 9")

    private val otherPlayerCard10 = LabeledStackView(posX = 1650, posY = 180, label = "other player's card 10")
    private val otherPlayerCard11 = LabeledStackView(posX = 1650, posY = 190, label = "other player's card 11")
    private val otherPlayerCard12 = LabeledStackView(posX = 1650, posY = 200, label = "other player's card 12")

    private val otherPlayerCard13 = LabeledStackView(posX = 1750, posY = 400, label = "other player's card 13")
    private val otherPlayerCard14 = LabeledStackView(posX = 1750, posY = 410, label = "other player's card 14")
    private val otherPlayerCard15 = LabeledStackView(posX = 1750, posY = 420, label = "other player's card 15")

    private val otherPlayerCard16 = LabeledStackView(posX = 1650, posY = 620, label = "other player's card 16")
    private val otherPlayerCard17 = LabeledStackView(posX = 1650, posY = 630, label = "other player's card 17")
    private val otherPlayerCard18 = LabeledStackView(posX = 1650, posY = 640, label = "other player's card 18")

    init {
        background = ColorVisual(155,94,95,255)
        addComponents(undoButton, redoButton, playerCard,
            otherPlayerCard1, otherPlayerCard2, otherPlayerCard3,
            otherPlayerCard4, otherPlayerCard5, otherPlayerCard6,
            otherPlayerCard7, otherPlayerCard8, otherPlayerCard9,
            otherPlayerCard10, otherPlayerCard11, otherPlayerCard12,
            otherPlayerCard13, otherPlayerCard14, otherPlayerCard15,
            otherPlayerCard16, otherPlayerCard17, otherPlayerCard18,
            )
    }
}