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

    private val playerTile = LabeledStackView(posX = 1400, posY = 975, label = "player's tile").apply {
        onDragGestureEntered = {}
        onDragGestureExited = {}
    }

    private val otherPlayerTile1 = LabeledStackView(posX = 150, posY = 180, label = "other player's tile 1")
    private val otherPlayerTile2 = LabeledStackView(posX = 150, posY = 190, label = "other player's tile 2")
    private val otherPlayerTile3 = LabeledStackView(posX = 150, posY = 200, label = "other player's tile 3")

    private val otherPlayerTile4 = LabeledStackView(posX = 50, posY = 400, label = "other player's tile 4")
    private val otherPlayerTile5 = LabeledStackView(posX = 50, posY = 410, label = "other player's tile 5")
    private val otherPlayerTile6 = LabeledStackView(posX = 50, posY = 420, label = "other player's tile 6")

    private val otherPlayerTile7 = LabeledStackView(posX = 150, posY = 620, label = "other player's tile 7")
    private val otherPlayerTile8 = LabeledStackView(posX = 150, posY = 630, label = "other player's tile 8")
    private val otherPlayerTile9 = LabeledStackView(posX = 150, posY = 640, label = "other player's tile 9")

    private val otherPlayerTile10 = LabeledStackView(posX = 1650, posY = 180, label = "other player's tile 10")
    private val otherPlayerTile11 = LabeledStackView(posX = 1650, posY = 190, label = "other player's tile 11")
    private val otherPlayerTile12 = LabeledStackView(posX = 1650, posY = 200, label = "other player's tile 12")

    private val otherPlayerTile13 = LabeledStackView(posX = 1750, posY = 400, label = "other player's tile 13")
    private val otherPlayerTile14 = LabeledStackView(posX = 1750, posY = 410, label = "other player's tile 14")
    private val otherPlayerTile15 = LabeledStackView(posX = 1750, posY = 420, label = "other player's tile 15")

    private val otherPlayerTile16 = LabeledStackView(posX = 1650, posY = 620, label = "other player's tile 16")
    private val otherPlayerTile17 = LabeledStackView(posX = 1650, posY = 630, label = "other player's tile 17")
    private val otherPlayerTile18 = LabeledStackView(posX = 1650, posY = 640, label = "other player's tile 18")

    init {
        background = ColorVisual(155,94,95,255)
        addComponents(undoButton, redoButton, playerTile,
            otherPlayerTile1, otherPlayerTile2, otherPlayerTile3,
            otherPlayerTile4, otherPlayerTile5, otherPlayerTile6,
            otherPlayerTile7, otherPlayerTile8, otherPlayerTile9,
            otherPlayerTile10, otherPlayerTile11, otherPlayerTile12,
            otherPlayerTile13, otherPlayerTile14, otherPlayerTile15,
            otherPlayerTile16, otherPlayerTile17, otherPlayerTile18,
            )
    }
}