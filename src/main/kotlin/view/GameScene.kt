package view

import entity.Player
import entity.Tile
import service.CardImageLoader
import tools.aqua.bgw.components.ComponentView
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.layoutviews.GridPane
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.CompoundVisual
import tools.aqua.bgw.visual.ImageVisual
import java.awt.Color

class GameScene : BoardGameScene(1920,1080) {
    private val mockTile = Tile(mutableListOf(Pair(0, 7), Pair(1, 6), Pair(2, 5), Pair(3, 4)), tilePos = 2)
    private val mockDrawnTile = Tile(mutableListOf(Pair(0, 5), Pair(1, 2), Pair(3, 6), Pair(4, 7)), tilePos = 3)
    private var currentTile: Tile? = null
    private var isDrawn = false

    private val mainGrid =
        GridPane<GridPane<GridPane<GridPane<ComponentView>>>>(0, 50, columns = 3, rows = 1, layoutFromCenter = false)
    private val outerOtherPlayersGrid: GridPane<GridPane<GridPane<ComponentView>>> = GridPane(columns = 1, rows = 1)
    private val boardGrid: GridPane<GridPane<GridPane<ComponentView>>> = GridPane(columns = 1, rows = 3)
    private val outerMyGrid: GridPane<GridPane<GridPane<ComponentView>>> = GridPane(columns = 1, rows = 1)

    private val labelFont = Font(50, Color.WHITE)
    private val buttonTextFont = Font(30, color = Color.WHITE)

    private val otherPlayersGrid = GridPane<GridPane<ComponentView>>(columns = 1, rows = 6)
    private val otherPlayers = mutableListOf<Player>(Player("p1"), Player("p2"), Player("p3"), Player("p4"))

    private val myInfo = Player("p0")
    private val myInfoGrid = GridPane<ComponentView>(columns = 3, rows = 1)
    private val myTilesGrid = GridPane<ComponentView>(columns = 1, rows = 3)
    private val drawnStackGrid = GridPane<ComponentView>(columns = 1, rows = 2)
    private val buttonsGrid = GridPane<ComponentView>(columns = 2, rows = 2)
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

    private val cardImageLoader = CardImageLoader()

    private var currentTileCardView: CardView? = null

    private val myTileCardView = CardView(
        height = 100,
        width = 100,
        front = ImageVisual(cardImageLoader.frontImage(mockTile.tilePos)),
        back = ImageVisual(cardImageLoader.backImage)
    ).apply {
        onMouseClicked = {
            flip()
            currentTileCardView = this
            currentTile = mockTile
        }
    }

    private val drawnTilesLabel = Label(
        text = "Drawn Tiles",
        height = 100,
        width = 300,
        font = labelFont,
    )

    private val drawnTilesCardView0 = CardView(
        height = 100,
        width = 100,
        front = ImageVisual(cardImageLoader.frontImage(mockDrawnTile.tilePos)),
        back = ImageVisual(cardImageLoader.backImage)
    ).apply {
        onMouseClicked = {
            flip()
            currentTileCardView = this
            myTileCardView.isDisabled = true
            currentTile = mockDrawnTile
            isDrawn = true
        }
    }

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

    private fun initStationArray() : Array<Array<Pair<entity.Color, Boolean>>>{
        val numOfPlayers = 5 // TODO: get number of players
        val stations = Array(4) { Array(8) { Pair(entity.Color.YELLOW, false) } }
        when (numOfPlayers) {
            2 -> {
                for(i in 0..1){
                    for(j in 0..7){
                        if ((i * 8 + j) % 2 == 0) {
                            stations[i][j] = Pair(entity.Color.YELLOW, false)
                        } else {
                            stations[i][j] = Pair(entity.Color.BLUE, false)
                        }
                    }
                }
                for(i in 2..3){
                    for(j in 0..7){
                        if ((i * 8 + j) % 2 == 0) {
                            stations[i][j] = Pair(entity.Color.BLUE, false)
                        } else {
                            stations[i][j] = Pair(entity.Color.YELLOW, false)
                        }
                    }
                }
                return stations
            }
            3 -> {
                stations[0][0] = Pair(entity.Color.YELLOW, false)
                stations[0][3] = Pair(entity.Color.YELLOW, false)
                stations[0][5] = Pair(entity.Color.YELLOW, false)
                stations[1][2] = Pair(entity.Color.YELLOW, false)
                stations[1][6] = Pair(entity.Color.YELLOW, false)
                stations[2][4] = Pair(entity.Color.YELLOW, false)
                stations[2][1] = Pair(entity.Color.YELLOW, false)
                stations[3][7] = Pair(entity.Color.YELLOW, false)
                stations[3][4] = Pair(entity.Color.YELLOW, false)
                stations[3][1] = Pair(entity.Color.YELLOW, false)

                stations[0][1] = Pair(entity.Color.BLUE, false)
                stations[0][6] = Pair(entity.Color.BLUE, false)
                stations[1][0] = Pair(entity.Color.BLUE, false)
                stations[1][3] = Pair(entity.Color.BLUE, false)
                stations[1][5] = Pair(entity.Color.BLUE, false)
                stations[2][5] = Pair(entity.Color.BLUE, false)
                stations[2][2] = Pair(entity.Color.BLUE, false)
                stations[3][5] = Pair(entity.Color.BLUE, false)
                stations[3][3] = Pair(entity.Color.BLUE, false)
                stations[3][0] = Pair(entity.Color.BLUE, false)

                stations[0][2] = Pair(entity.Color.ORANGE, false)
                stations[0][4] = Pair(entity.Color.ORANGE, false)
                stations[0][7] = Pair(entity.Color.ORANGE, false)
                stations[1][1] = Pair(entity.Color.ORANGE, false)
                stations[1][4] = Pair(entity.Color.ORANGE, false)
                stations[2][6] = Pair(entity.Color.ORANGE, false)
                stations[2][3] = Pair(entity.Color.ORANGE, false)
                stations[2][0] = Pair(entity.Color.ORANGE, false)
                stations[3][6] = Pair(entity.Color.ORANGE, false)
                stations[3][2] = Pair(entity.Color.ORANGE, false)

                stations[1][7] = Pair(entity.Color.BLACK, false)
                stations[2][7] = Pair(entity.Color.BLACK, false)
                return stations
            }
            4 -> {
                stations[0][3] = Pair(entity.Color.YELLOW, false)
                stations[0][6] = Pair(entity.Color.YELLOW, false)
                stations[1][2] = Pair(entity.Color.YELLOW, false)
                stations[1][7] = Pair(entity.Color.YELLOW, false)
                stations[2][4] = Pair(entity.Color.YELLOW, false)
                stations[2][1] = Pair(entity.Color.YELLOW, false)
                stations[3][5] = Pair(entity.Color.YELLOW, false)
                stations[3][0] = Pair(entity.Color.YELLOW, false)

                stations[0][2] = Pair(entity.Color.BLUE, false)
                stations[0][7] = Pair(entity.Color.BLUE, false)
                stations[1][3] = Pair(entity.Color.BLUE, false)
                stations[1][6] = Pair(entity.Color.BLUE, false)
                stations[2][5] = Pair(entity.Color.BLUE, false)
                stations[2][0] = Pair(entity.Color.BLUE, false)
                stations[3][4] = Pair(entity.Color.BLUE, false)
                stations[3][1] = Pair(entity.Color.BLUE, false)

                stations[0][0] = Pair(entity.Color.ORANGE, false)
                stations[0][5] = Pair(entity.Color.ORANGE, false)
                stations[1][1] = Pair(entity.Color.ORANGE, false)
                stations[1][4] = Pair(entity.Color.ORANGE, false)
                stations[2][6] = Pair(entity.Color.ORANGE, false)
                stations[2][3] = Pair(entity.Color.ORANGE, false)
                stations[3][7] = Pair(entity.Color.ORANGE, false)
                stations[3][2] = Pair(entity.Color.ORANGE, false)

                stations[0][1] = Pair(entity.Color.GREEN, false)
                stations[0][4] = Pair(entity.Color.GREEN, false)
                stations[1][0] = Pair(entity.Color.GREEN, false)
                stations[1][5] = Pair(entity.Color.GREEN, false)
                stations[2][7] = Pair(entity.Color.GREEN, false)
                stations[2][2] = Pair(entity.Color.GREEN, false)
                stations[3][6] = Pair(entity.Color.GREEN, false)
                stations[3][3] = Pair(entity.Color.GREEN, false)

                return stations
            }
            5 -> {
                stations[0][0] = Pair(entity.Color.YELLOW, false)
                stations[0][4] = Pair(entity.Color.YELLOW, false)
                stations[1][1] = Pair(entity.Color.YELLOW, false)
                stations[1][5] = Pair(entity.Color.YELLOW, false)
                stations[2][2] = Pair(entity.Color.YELLOW, false)
                stations[3][4] = Pair(entity.Color.YELLOW, false)

                stations[0][5] = Pair(entity.Color.BLUE, false)
                stations[1][3] = Pair(entity.Color.BLUE, false)
                stations[2][6] = Pair(entity.Color.BLUE, false)
                stations[2][1] = Pair(entity.Color.BLUE, false)
                stations[3][5] = Pair(entity.Color.BLUE, false)
                stations[3][0] = Pair(entity.Color.BLUE, false)

                stations[0][2] = Pair(entity.Color.ORANGE, false)
                stations[0][6] = Pair(entity.Color.ORANGE, false)
                stations[1][6] = Pair(entity.Color.ORANGE, false)
                stations[2][5] = Pair(entity.Color.ORANGE, false)
                stations[3][7] = Pair(entity.Color.ORANGE, false)
                stations[3][3] = Pair(entity.Color.ORANGE, false)

                stations[0][1] = Pair(entity.Color.GREEN, false)
                stations[1][0] = Pair(entity.Color.GREEN, false)
                stations[1][4] = Pair(entity.Color.GREEN, false)
                stations[2][3] = Pair(entity.Color.GREEN, false)
                stations[3][6] = Pair(entity.Color.GREEN, false)
                stations[3][2] = Pair(entity.Color.GREEN, false)

                stations[0][3] = Pair(entity.Color.PURPLE, false)
                stations[0][7] = Pair(entity.Color.PURPLE, false)
                stations[1][2] = Pair(entity.Color.PURPLE, false)
                stations[2][4] = Pair(entity.Color.PURPLE, false)
                stations[2][0] = Pair(entity.Color.PURPLE, false)
                stations[3][1] = Pair(entity.Color.PURPLE, false)

                stations[1][7] = Pair(entity.Color.BLACK, false)
                stations[2][7] = Pair(entity.Color.BLACK, false)
                return stations
            }
            6 -> {
                stations[0][0] = Pair(entity.Color.YELLOW, false)
                stations[0][4] = Pair(entity.Color.YELLOW, false)
                stations[1][1] = Pair(entity.Color.YELLOW, false)
                stations[2][5] = Pair(entity.Color.YELLOW, false)
                stations[3][5] = Pair(entity.Color.YELLOW, false)

                stations[0][1] = Pair(entity.Color.BLUE, false)
                stations[1][2] = Pair(entity.Color.BLUE, false)
                stations[2][6] = Pair(entity.Color.BLUE, false)
                stations[3][7] = Pair(entity.Color.BLUE, false)
                stations[3][3] = Pair(entity.Color.BLUE, false)

                stations[0][3] = Pair(entity.Color.ORANGE, false)
                stations[0][7] = Pair(entity.Color.ORANGE, false)
                stations[1][5] = Pair(entity.Color.ORANGE, false)
                stations[2][3] = Pair(entity.Color.ORANGE, false)
                stations[3][6] = Pair(entity.Color.ORANGE, false)

                stations[0][5] = Pair(entity.Color.GREEN, false)
                stations[1][6] = Pair(entity.Color.GREEN, false)
                stations[2][4] = Pair(entity.Color.GREEN, false)
                stations[2][0] = Pair(entity.Color.GREEN, false)
                stations[3][1] = Pair(entity.Color.GREEN, false)

                stations[0][2] = Pair(entity.Color.PURPLE, false)
                stations[1][0] = Pair(entity.Color.PURPLE, false)
                stations[1][4] = Pair(entity.Color.PURPLE, false)
                stations[2][1] = Pair(entity.Color.PURPLE, false)
                stations[3][2] = Pair(entity.Color.PURPLE, false)

                stations[0][6] = Pair(entity.Color.BLACK, false)
                stations[1][3] = Pair(entity.Color.BLACK, false)
                stations[2][2] = Pair(entity.Color.BLACK, false)
                stations[3][4] = Pair(entity.Color.BLACK, false)
                stations[3][0] = Pair(entity.Color.BLACK, false)

                stations[1][7] = Pair(entity.Color.BLACK, false)
                stations[2][7] = Pair(entity.Color.BLACK, false)
                return stations
            }

            else -> {
                return emptyArray()
            }
        }
    }

    private fun initStationPosition() {
        val stations = initStationArray()
        for(i in 0..3) {
            for (j in 0..7) {
                var stationCardView = CardView(
                    height = 100,
                    width = 100,
                    front = ColorVisual.LIGHT_GRAY,
                    back = ImageVisual(cardImageLoader.stationImage(stations[i][j].first, stations[i][j].second))
                ).apply{
                    if((i==1 || i ==2) && j==7 && stations[i][j]==Pair(entity.Color.BLACK, false) ){
                        this.showFront()
                    }
                    if(i==0) this.rotation = 90.0
                    else if(i==1) this.rotation = 180.0
                    else if(i==2) this.rotation = 270.0
                }

                if(i==0) topStationGrid[j, 0] = stationCardView
                else if(i==1) rightStationGrid[0,j] = stationCardView
                else if(i==2) bottomStationGrid[j,0] = stationCardView
                else if(i==3) leftStationGrid[0,j] = stationCardView
            }
        }
    }


    val undoButton: Button = Button(
        width = 150, height = 50,
        text = "Undo",
        font = buttonTextFont
    ).apply {
        visual = ColorVisual(186, 136, 133, 255)
    }

    val redoButton: Button = Button(
        width = 150, height = 50,
        text = "Redo",
        font = buttonTextFont
    ).apply {
        visual = ColorVisual(186, 136, 133, 255)
    }

    val rotateButton: Button = Button(
        width = 150, height = 50,
        text = "Rotate",
        font = buttonTextFont
    ).apply {
        visual = ColorVisual(186, 136, 133, 255)
        onMouseClicked = {
            if (currentTile != null && currentTileCardView != null) {
                currentTile!!.rotationDegree += 90
                currentTileCardView!!.rotation = currentTile!!.rotationDegree.toDouble()
            }
        }
    }



    init {
        // TODO: changed players number
        for (i in 0 until otherPlayers.size) {
            val otherPlayerGrid = GridPane<ComponentView>(columns = 3, rows = 1)
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
        otherPlayersGrid.setColumnWidths(400)
        mainGrid[0,0] = outerOtherPlayersGrid

        myInfoGrid[1,0] = myNameLabel
        myInfoGrid[2,0] = myScoreLabel
        myInfoGrid[0,0] = myColorLabel

        myTilesGrid[0, 0] = myTileCardView

        myGrid[0, 0] = myInfoGrid
        myGrid[0, 1] = myTilesGrid

        drawnStackGrid[0, 0] = drawnTilesLabel
        drawnStackGrid[0, 1] = drawnTilesCardView0
        myGrid[0, 2] = drawnStackGrid

        buttonsGrid[0, 0] = undoButton
        buttonsGrid[1, 0] = redoButton
        buttonsGrid[0, 1] = rotateButton
        buttonsGrid.setRowHeights(70)
        buttonsGrid.setColumnWidths(200)
        myGrid[0, 3] = buttonsGrid

        myGrid.setRowHeight(0,100)
        myGrid.setRowHeight(1, 100)
        myGrid.setRowHeight(2, 200)
        myGrid.setRowHeight(3, 300)
        myGrid.setColumnWidths(500)
        outerMyGrid[0, 0] = myGrid
        mainGrid[2, 0] = outerMyGrid

        val mainStationPos = 3..4
        for (i in 0..7) {
            for (j in 0..7) {
                var boardCellLabel: CardView? = null
                if (i in mainStationPos && j in mainStationPos) {
                    boardCellLabel = CardView(
                        height = 100,
                        width = 100,
                        front = ColorVisual.GREEN,
                        back = ColorVisual.LIGHT_GRAY
                    )
                } else {
                    boardCellLabel = CardView(
                        height = 100,
                        width = 100,
                        front = ColorVisual.GREEN,
                        back = ColorVisual.WHITE
                    ).apply {
                        onMouseClicked = {
                            if (currentTileCardView!!.currentSide == CardView.CardSide.FRONT) {
                                this.frontVisual = ImageVisual(cardImageLoader.frontImage(currentTile!!.tilePos))
                                this.rotation = currentTile!!.rotationDegree.toDouble()
                                showFront()
                                myTileCardView.showBack()
                                drawnTilesCardView0.showBack()
                                drawnTilesCardView0.isDisabled
                            }
                            if (isDrawn) {
                                //TODO: set myTile = drawnTiles[0]
                                println("play from drawn")
                            }
                        }
                    }
                }
                mainBoardGrid[i,j] = boardCellLabel
            }
        }

        initStationPosition()
        topBoardGrid[1,0] = topStationGrid
        middleBoardGrid[0,0] = leftStationGrid
        middleBoardGrid[1,0] = mainBoardGrid
        middleBoardGrid[2,0] = rightStationGrid
        bottomBoardGrid[1,0] = bottomStationGrid
        boardGrid[0,0] = topBoardGrid
        boardGrid[0,1] = middleBoardGrid
        boardGrid[0,2] = bottomBoardGrid
        mainGrid[1,0] = boardGrid

        background = ImageVisual("game_scene.png")
        addComponents(mainGrid)
    }
}