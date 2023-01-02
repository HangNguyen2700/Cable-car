package view

import entity.Player
import service.CardImageLoader
import tools.aqua.bgw.components.ComponentView
import tools.aqua.bgw.components.gamecomponentviews.CardView
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

class GameScene0 : BoardGameScene(1920,1080) {
    private val mainGrid = GridPane<GridPane<GridPane<GridPane<ComponentView>>>>(0, 0, columns = 3, rows = 1, layoutFromCenter = false)
    private val outerOtherPlayersGrid : GridPane<GridPane<GridPane<ComponentView>>> = GridPane(columns = 1, rows = 1)
    private val boardGrid : GridPane<GridPane<GridPane<ComponentView>>> = GridPane(columns = 3, rows = 1)
    private val outerMyGrid : GridPane<GridPane<GridPane<ComponentView>>> = GridPane(columns = 1, rows = 1)

    private val labelFont = Font(50, Color.WHITE)
    private val buttonTextFont = Font(30, color = Color.WHITE)

    private val otherPlayersGrid = GridPane<GridPane<ComponentView>>(columns = 1, rows = 6)
    private val otherPlayers = mutableListOf<Player>(Player("p1"), Player("p2"), Player("p3"), Player("p4"))

    private val myInfo = Player("p0")
    private val myInfoGrid = GridPane<ComponentView>(columns = 3, rows = 1)
    private val myTilesGrid = GridPane<ComponentView>(columns = 1, rows = 3)
    private val drawnStackGrid = GridPane<ComponentView>(columns = 1, rows = 2)
    private val buttonsGrid = GridPane<ComponentView>(columns = 3, rows = 1)
    private val myGrid = GridPane<GridPane<ComponentView>>(columns = 1, rows = 4)

    private val topBoardGrid = GridPane<GridPane<ComponentView>>(columns = 3, rows = 1)
    private val middleBoardGrid = GridPane<GridPane<ComponentView>>(columns = 3, rows = 1)
    private val bottomBoardGrid = GridPane<GridPane<ComponentView>>(columns = 3, rows = 1)

    private val topStationGrid = GridPane<ComponentView>(columns = 8, rows = 1)
    private val leftStationGrid = GridPane<ComponentView>(columns = 1, rows = 8)
    private val rightStationGrid = GridPane<ComponentView>(columns = 1, rows = 8)
    private val bottomStationGrid = GridPane<ComponentView>(columns = 8, rows = 1)
    private val mainBoardGrid = GridPane<ComponentView>(columns = 8, rows = 8)

    private val myNameLabel = Label(
        text = myInfo.name,
        /*height = 200,
        width = 1000,*/
        font = labelFont,
    )
    private val myScoreLabel = Label(
        text = myInfo.score.toString(),
        /*height = 200,
        width = 1000,*/
        font = labelFont,
    )
    private val myColorLabel = Label(
        height = 50,
        width = 50,
        visual = CompoundVisual(
            ColorVisual.GREEN  //TODO: change player color: != null
        )
    )

    val cardImageLoader = CardImageLoader()

    private val myTile = CardView(
            height = 100,
            width = 100,
            front = ImageVisual(
                cardImageLoader.backImage
            ),
            back = ImageVisual(cardImageLoader.backImage))

    private val currentTile = myTile

    private val drawnTilesLabel = Label(
        text = "Drawn Tiles",
        height = 100,
        width = 300,
        font = labelFont,
    )

    private val drawnTiles0 = CardView(
        height = 100,
        width = 100,
        front = ImageVisual( cardImageLoader.backImage),
        back = ImageVisual(cardImageLoader.backImage)
    )
//    private val drawnTiles1 = CardView(
//        height = 100,
//        width = 100,
//        front = ImageVisual(
//            cardImageLoader.backImage
//        ),
//        back = ImageVisual(cardImageLoader.backImage)
//    )
//    private val drawnTiles2 = CardView(
//        height = 100,
//        width = 100,
//        front = ImageVisual(
//            cardImageLoader.backImage
//        ),
//        back = ImageVisual(cardImageLoader.backImage)
//    )

    val undoButton: Button = Button(
        width = 200, height = 50,
        text = "Undo",
        font = buttonTextFont
    ).apply {
        visual = ColorVisual(186,136,133,255)
    }

    val redoButton: Button = Button(
        width = 200, height = 50,
        text = "Redo",
        font = buttonTextFont
    ).apply {
        visual = ColorVisual(186,136,133,255)
    }

    init {
        // TODO: changed players number
        for(i in 0 until otherPlayers.size){
            val otherPlayerGrid = GridPane<ComponentView>( columns = 3, rows = 1)
            val otherPlayerNameLabel = Label(
                text = otherPlayers[i].name,
                /*height = 200,
                width = 1000,*/
                font = labelFont,
            )
            otherPlayerGrid[1,0] = otherPlayerNameLabel
            val otherPlayerScoreLabel = Label(
                text = otherPlayers[i].score.toString(),
                /*height = 200,
                width = 1000,*/
                font = labelFont,
            )
            otherPlayerGrid[2,0] = otherPlayerScoreLabel
            val otherPlayerColorLabel = Label(
                height = 50,
                width = 50,
                visual = CompoundVisual(
                    ColorVisual.GREEN  //TODO: change player color: != null
                )
            )
            otherPlayerGrid[0,0] = otherPlayerColorLabel
            otherPlayersGrid[0,i] = otherPlayerGrid
        }
        outerOtherPlayersGrid[0,0] = otherPlayersGrid
        mainGrid[0,0] = outerOtherPlayersGrid

        myInfoGrid[1,0] = myNameLabel
        myInfoGrid[2,0] = myScoreLabel
        myInfoGrid[0,0] = myColorLabel

        myTilesGrid[0,0] = myTile

        myGrid[0,0] = myInfoGrid
        myGrid[0,1] = myTilesGrid

        drawnStackGrid[0,0] = drawnTilesLabel
        drawnStackGrid[0,1] = drawnTiles0
        myGrid[0,2] = drawnStackGrid

        buttonsGrid[0,0] = undoButton
        buttonsGrid[2,0] = redoButton
        myGrid[0,3] = buttonsGrid
        outerMyGrid[0,0] = myGrid
        mainGrid[2,0] = outerMyGrid

        for(i in 0..7){
            for(j in 0..7){
                val boardCellLabel = CardView(
                    height = 100,
                    width = 100,
                    front = ColorVisual.GREEN,
                    back = ImageVisual(cardImageLoader.backImage)
                ).apply {
                    onMouseClicked = {
//                        this.frontVisual = currentTile.frontVisual
                        this.frontVisual = ColorVisual.WHITE
                        showFront()
                    }
                }
                mainBoardGrid[i,j] = boardCellLabel
            }
        }

        topBoardGrid[1,0] = topStationGrid
        middleBoardGrid[0,0] = leftStationGrid
        middleBoardGrid[1,0] = mainBoardGrid
        middleBoardGrid[2,0] = rightStationGrid
        bottomBoardGrid[1,0] = bottomStationGrid
        boardGrid[0,0] = topBoardGrid
        boardGrid[1,0] = middleBoardGrid
        boardGrid[2,0] = bottomBoardGrid
        mainGrid[1,0] = boardGrid

        background = ColorVisual(155,94,95,255)
        addComponents(mainGrid)
    }
}