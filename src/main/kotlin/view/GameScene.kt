package view

import entity.Player
import entity.Tile
import entity .Turn
import service.CardImageLoader
import service.GameService
import service.PlayerActionService
import service.RootService
import tools.aqua.bgw.components.ComponentView
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.layoutviews.GridPane
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import java.awt.Color

/**
 * main scene
 *
 * Attributes:
 * [handTileLabel]: initializes player's current tile(s) and drawn tile (/tilestack)
 * [rotateButton]: rotates chosen tile by 90 degrees before placing on game field
 */

class GameScene(private val rootService: RootService) : BoardGameScene(1920, 1080), Refreshable {

    private val cardImageLoader = CardImageLoader()

    private lateinit var gameService: GameService
    private lateinit var playerActionService: PlayerActionService
    private var currentTurn: Turn? = null
    private var playerList = listOf<Player>()
    private var currentTile: Tile? = null

    private var isDrawStackTileChosen : Boolean? = null

    private val labelFont = Font(50, Color.BLACK, family = "Calibri")
    private val playerScoreFont = Font(40, Color.BLACK, family = "Calibri")
    private val playerScoreHighlightedFont = Font(40, Color.GREEN, family = "Calibri")
    private val buttonTextFont = Font(30, color = Color.WHITE, family = "Calibri")

    private val mainGrid = GridPane<GridPane<GridPane<GridPane<ComponentView>>>>(
        posX = 60, posY = 40, columns = 3, rows = 1, layoutFromCenter = false
    )
    private val outerPlayersGrid = GridPane<GridPane<GridPane<ComponentView>>>(columns = 1, rows = 1)
    private val playersGrid = GridPane<GridPane<ComponentView>>(columns = 1, rows = 6, spacing = 50.0)
    private val boardGrid = GridPane<GridPane<GridPane<ComponentView>>>(columns = 1, rows = 3)
    private val topStationGrid = GridPane<ComponentView>(columns = 8, rows = 1)
    private val leftStationGrid = GridPane<ComponentView>(columns = 1, rows = 8)
    private val rightStationGrid = GridPane<ComponentView>(columns = 1, rows = 8)
    private val bottomStationGrid = GridPane<ComponentView>(columns = 8, rows = 1)
    private val mainBoardGrid = GridPane<ComponentView>(columns = 8, rows = 8)
    private val outerMyGrid = GridPane<GridPane<GridPane<ComponentView>>>(columns = 1, rows = 1)
    private val handTilesGrid = GridPane<ComponentView>(columns = 1, rows = 2)
    private val drawnStackGrid = GridPane<ComponentView>(columns = 1, rows = 2)
    private val buttonsGrid = GridPane<ComponentView>(columns = 2, rows = 2)
    private val myGrid = GridPane<GridPane<ComponentView>>(columns = 1, rows = 3)

    private val topBoardGrid = GridPane<GridPane<ComponentView>>(columns = 3, rows = 1)
    private val middleBoardGrid = GridPane<GridPane<ComponentView>>(columns = 3, rows = 1)
    private val bottomBoardGrid = GridPane<GridPane<ComponentView>>(columns = 3, rows = 1)

    private var currentTileCardView: CardView? = null

    private val tileBackImage = ImageVisual("tile_back.png")

    private val handTileLabel = Label(height = 100, width = 300, font = labelFont, text = "Hand Tile").apply { isDisabled = true; opacity = 0.0 }

    private val handTileCardView = CardView(height = 180, width = 180, front = ColorVisual.WHITE, back = tileBackImage
    ).apply {
        isDisabled = true; opacity = 0.0
        flip()
        onMouseClicked = {
            if (isDrawStackTileChosen == null) {
                currentTileCardView = this
                currentTile = currentTurn?.players?.get(currentTurn!!.currentPlayerIndex)?.handTile
                isDrawStackTileChosen = false
            }
        }
    }

    private val drawnTilesLabel = Label(height = 100, width = 300, font = labelFont, text = "Draw Stack").apply { isDisabled = true; opacity = 0.0 }

    private val drawnTilesCardView = CardView(height = 180, width = 180, front = ColorVisual.WHITE, back = tileBackImage
    ).apply {
        isDisabled = true; opacity = 0.0
        onMouseClicked = {
            if (isDrawStackTileChosen == null) {
                flip()
                handTileCardView.flip()
                currentTileCardView = this
                handTileCardView.isDisabled = true
                currentTile = currentTurn?.gameField?.tileStack?.tiles?.first()
                isDrawStackTileChosen = true
            }
        }
    }

    private val undoButton = Button(width = 150, height = 50, font = buttonTextFont, text = "Undo"
    ).apply { visual = ColorVisual(186, 136, 133); onMouseClicked = { gameService.undo() } }.apply { isDisabled = true; opacity = 0.0 }

    private val redoButton = Button(width = 150, height = 50, font = buttonTextFont, text = "Redo"
    ).apply { visual = ColorVisual(186, 136, 133); onMouseClicked = { gameService.undo() } }.apply { isDisabled = true; opacity = 0.0 }

    private val rotateButton = Button(width = 150, height = 50, font = buttonTextFont, text = "Rotate",
    ).apply {
        isDisabled = true; opacity = 0.0
        isVisible = false
        visual = ColorVisual(186, 136, 133, 255)
        onMouseClicked = {
            if (currentTile != null && currentTileCardView != null) {
                currentTile!!.rotationDegree += 90
                currentTileCardView!!.rotation = currentTile!!.rotationDegree.toDouble() } } }

    val quickMenuButton = Button(width = 140, height = 140, posX = 40, posY = 40,
        visual = ImageVisual("quick_menu_button.png"))

    private val playerScoreBGLabel = Label(width = 420, height = 580, posY = 250, visual = ColorVisual.WHITE
        ).apply { opacity = 0.8 }

    init {

        // displays all players' information

        outerPlayersGrid[0, 0] = playersGrid
        playersGrid.setColumnWidths(400)
        mainGrid[0, 0] = outerPlayersGrid

        // displays player hand tiles
        handTilesGrid[0, 0] = handTileLabel
        handTilesGrid[0, 1] = handTileCardView
        handTilesGrid.setRowHeight(1, 150)
        myGrid[0, 0] = handTilesGrid

        //display drawn tile (-stack)
        drawnStackGrid[0, 0] = drawnTilesLabel
        drawnStackGrid[0, 1] = drawnTilesCardView
        drawnStackGrid.setRowHeight(1, 150)
        myGrid[0, 1] = drawnStackGrid


        //displays buttons
        buttonsGrid[0, 0] = undoButton
        buttonsGrid[1, 0] = redoButton
        buttonsGrid[0, 1] = rotateButton
        buttonsGrid.setRowHeights(70)
        buttonsGrid.setColumnWidths(200)
        myGrid[0, 2] = buttonsGrid

        myGrid.setRowHeights(300)
        myGrid.setColumnWidths(500)
        outerMyGrid[0, 0] = myGrid
        mainGrid[2, 0] = outerMyGrid

        // displays game board with stations
        topBoardGrid[1, 0] = topStationGrid
        middleBoardGrid[0, 0] = leftStationGrid
        middleBoardGrid[1, 0] = mainBoardGrid
        middleBoardGrid[2, 0] = rightStationGrid
        bottomBoardGrid[1, 0] = bottomStationGrid
        boardGrid[0, 0] = topBoardGrid
        boardGrid[0, 1] = middleBoardGrid
        boardGrid[0, 2] = bottomBoardGrid
        mainGrid[1, 0] = boardGrid

        background = ImageVisual("game_scene.png")
        addComponents(playerScoreBGLabel, mainGrid, quickMenuButton)
    }

    override fun refreshAfterStartGame() {
        gameService = rootService.gameService
        playerActionService = rootService.playerActionService
        playerList = rootService.currentGame!!.currentTurn.players
        currentTurn = rootService.currentGame!!.currentTurn

        handTileLabel.opacity = 1.0; handTileLabel.isDisabled = false
        handTileCardView.opacity = 1.0; handTileCardView.isDisabled = false
        drawnTilesLabel.opacity = 1.0;drawnTilesLabel.isDisabled = false
        drawnTilesCardView.opacity = 1.0
        undoButton.opacity = 1.0
        redoButton.opacity = 1.0
        rotateButton.opacity = 1.0
        drawnTilesLabel.isDisabled = false
        drawnTilesCardView.isDisabled = false
        undoButton.isDisabled = false
        redoButton.isDisabled = false
        rotateButton.isDisabled = false

        showPlayers()
        initGameBoard()
        initStationPosition()

        setTileFront(handTileCardView, currentTurn!!.players[currentTurn!!.currentPlayerIndex].handTile!!)
        setTileFront(drawnTilesCardView, currentTurn!!.gameField.tileStack.tiles.first())
        rotateButton.isVisible = gameService.rotationAllowed
        handTileCardView.isVisible = isMyTurn()
    }

    fun hostGameWaitForPlayers(hostName :String, isHostAi : Boolean) {
        playerList += Player(hostName,if(isHostAi) true else null) //TODO: Join Netzwerk Differenzierung Spieler real/ai
        playerList.forEach { println(it.name) }
        showPlayers()
    }

    override fun refreshAfterPlayerJoinedInWaitSession(playerName:String){
        playerList += Player(playerName,null)
        playerList.forEach { println(it.name) }
        showPlayers()
    }

    override fun refreshAfterPlayerLeftInWaitSession(playerName:String){
        val toBeDeleted = playerList.find { it.name == playerName }
        playerList -= toBeDeleted!!
        playerList.forEach { println(it.name) }
        showPlayers()
    }

    private fun showPlayers() {

        for (i in playerList.indices) {

            val playerGrid = GridPane<ComponentView>(columns = 5, rows = 1)

            val playerColorLabel = Label(height = 50, width = 50,
                visual = when(i) {
                    0 -> ColorVisual.YELLOW; 1 -> ColorVisual.BLUE; 2 -> ColorVisual.ORANGE; 3 -> ColorVisual.GREEN
                    4 -> ColorVisual(183,0,255); else -> ColorVisual.BLACK }
            ).apply {  }

            playerGrid[0,0] = playerColorLabel

            playerGrid[1,0] = Label(width = 10, visual = ColorVisual(0,0,0,0))

            // highlight current player
            val playerNameLabel = Label(width = 270, height = 50, font = playerScoreFont, text = playerList[i].name,
                alignment = Alignment.CENTER_LEFT
            ).apply { if (currentTurn != null && i == currentTurn!!.currentPlayerIndex) font = playerScoreHighlightedFont }

            playerGrid[2, 0] = playerNameLabel

            // highlight current player
            val playerScoreLabel = Label(width = 70, height = 50, font = playerScoreFont,
                text = playerList[i].score.toString(), alignment = Alignment.CENTER_RIGHT
            ).apply { if (currentTurn != null && i == currentTurn!!.currentPlayerIndex) font = playerScoreHighlightedFont }

            playerGrid[3,0] = playerScoreLabel

            playerGrid[4,0] = Label(width = 100, visual = ColorVisual(0,0,0,0))

            playersGrid[0,i] = playerGrid
        }
    }

    /**
     * initializes all stations in 2d-array to positions around the game board
     * TODO: the amount of stations per players varies based on the amount of players
     */

    private fun initStationPosition() {
        val stations = initStationArray()

        for (i in 0..3) {

            for (j in 0..7) {

                val stationCardView = CardView(
                    height = 100, width = 100,
                    front = ColorVisual(0, 0, 0, 0),
                    back = ImageVisual(cardImageLoader.stationImage(stations[i][j].first, stations[i][j].second))
                ).apply {
                    if ((i == 1 || i == 2) && j == 7 && stations[i][j] == Pair(entity.Color.BLACK, false)) {
                        this.showFront()
                    }
                    when (i) {
                        0 -> this.rotation = 90.0
                        1 -> this.rotation = 180.0
                        2 -> this.rotation = 270.0
                        3 -> this.rotation = 0.0
                    }
                }

                when (i) {
                    0 -> topStationGrid[j, 0] = stationCardView
                    1 -> rightStationGrid[0, j] = stationCardView
                    2 -> bottomStationGrid[j, 0] = stationCardView
                    3 -> leftStationGrid[0, j] = stationCardView
                }
            }
        }
    }

    /**
     * 2D-array performs all stations around the game board and their car's colors according to player number
     * when number of players is 1,2,4: 32 cars with color
     * when number of players is 3,5,6: no car at positions 16 & 17 (30 cars with color)
     */

    private fun initStationArray(): Array<Array<Pair<entity.Color, Boolean>>> {
        val numOfPlayers = playerList.size
        val stations = Array(4) { Array(8) { Pair(entity.Color.YELLOW, false) } }
        when (numOfPlayers) {
            2 -> {
                for (i in 0..1) {
                    for (j in 0..7) {
                        if ((i * 8 + j) % 2 == 0) {
                            stations[i][j] = Pair(entity.Color.YELLOW, false)
                        } else {
                            stations[i][j] = Pair(entity.Color.BLUE, false)
                        }
                    }
                }
                for (i in 2..3) {
                    for (j in 0..7) {
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
                stations[0][0] = Pair(entity.Color.YELLOW, false); stations[0][3] = Pair(entity.Color.YELLOW, false)
                stations[0][5] = Pair(entity.Color.YELLOW, false); stations[1][2] = Pair(entity.Color.YELLOW, false)
                stations[1][6] = Pair(entity.Color.YELLOW, false); stations[2][4] = Pair(entity.Color.YELLOW, false)
                stations[2][1] = Pair(entity.Color.YELLOW, false); stations[3][7] = Pair(entity.Color.YELLOW, false)
                stations[3][4] = Pair(entity.Color.YELLOW, false); stations[3][1] = Pair(entity.Color.YELLOW, false)

                stations[0][1] = Pair(entity.Color.BLUE, false); stations[0][6] = Pair(entity.Color.BLUE, false)
                stations[1][0] = Pair(entity.Color.BLUE, false); stations[1][3] = Pair(entity.Color.BLUE, false)
                stations[1][5] = Pair(entity.Color.BLUE, false); stations[2][5] = Pair(entity.Color.BLUE, false)
                stations[2][2] = Pair(entity.Color.BLUE, false); stations[3][5] = Pair(entity.Color.BLUE, false)
                stations[3][3] = Pair(entity.Color.BLUE, false); stations[3][0] = Pair(entity.Color.BLUE, false)

                stations[0][2] = Pair(entity.Color.ORANGE, false); stations[0][4] = Pair(entity.Color.ORANGE, false)
                stations[0][7] = Pair(entity.Color.ORANGE, false); stations[1][1] = Pair(entity.Color.ORANGE, false)
                stations[1][4] = Pair(entity.Color.ORANGE, false); stations[2][6] = Pair(entity.Color.ORANGE, false)
                stations[2][3] = Pair(entity.Color.ORANGE, false); stations[2][0] = Pair(entity.Color.ORANGE, false)
                stations[3][6] = Pair(entity.Color.ORANGE, false); stations[3][2] = Pair(entity.Color.ORANGE, false)

                stations[1][7] = Pair(entity.Color.BLACK, false); stations[2][7] = Pair(entity.Color.BLACK, false)
                return stations
            }
            4 -> {
                stations[0][3] = Pair(entity.Color.YELLOW, false); stations[0][6] = Pair(entity.Color.YELLOW, false)
                stations[1][2] = Pair(entity.Color.YELLOW, false); stations[1][7] = Pair(entity.Color.YELLOW, false)
                stations[2][4] = Pair(entity.Color.YELLOW, false); stations[2][1] = Pair(entity.Color.YELLOW, false)
                stations[3][5] = Pair(entity.Color.YELLOW, false); stations[3][0] = Pair(entity.Color.YELLOW, false)

                stations[0][2] = Pair(entity.Color.BLUE, false); stations[0][7] = Pair(entity.Color.BLUE, false)
                stations[1][3] = Pair(entity.Color.BLUE, false); stations[1][6] = Pair(entity.Color.BLUE, false)
                stations[2][5] = Pair(entity.Color.BLUE, false); stations[2][0] = Pair(entity.Color.BLUE, false)
                stations[3][4] = Pair(entity.Color.BLUE, false); stations[3][1] = Pair(entity.Color.BLUE, false)

                stations[0][0] = Pair(entity.Color.ORANGE, false); stations[0][5] = Pair(entity.Color.ORANGE, false)
                stations[1][1] = Pair(entity.Color.ORANGE, false); stations[1][4] = Pair(entity.Color.ORANGE, false)
                stations[2][6] = Pair(entity.Color.ORANGE, false); stations[2][3] = Pair(entity.Color.ORANGE, false)
                stations[3][7] = Pair(entity.Color.ORANGE, false); stations[3][2] = Pair(entity.Color.ORANGE, false)

                stations[0][1] = Pair(entity.Color.GREEN, false); stations[0][4] = Pair(entity.Color.GREEN, false)
                stations[1][0] = Pair(entity.Color.GREEN, false); stations[1][5] = Pair(entity.Color.GREEN, false)
                stations[2][7] = Pair(entity.Color.GREEN, false); stations[2][2] = Pair(entity.Color.GREEN, false)
                stations[3][6] = Pair(entity.Color.GREEN, false); stations[3][3] = Pair(entity.Color.GREEN, false)

                return stations
            }
            5 -> {
                stations[0][0] = Pair(entity.Color.YELLOW, false); stations[0][4] = Pair(entity.Color.YELLOW, false)
                stations[1][1] = Pair(entity.Color.YELLOW, false); stations[1][5] = Pair(entity.Color.YELLOW, false)
                stations[2][2] = Pair(entity.Color.YELLOW, false); stations[3][4] = Pair(entity.Color.YELLOW, false)

                stations[0][5] = Pair(entity.Color.BLUE, false); stations[1][3] = Pair(entity.Color.BLUE, false)
                stations[2][6] = Pair(entity.Color.BLUE, false); stations[2][1] = Pair(entity.Color.BLUE, false)
                stations[3][5] = Pair(entity.Color.BLUE, false); stations[3][0] = Pair(entity.Color.BLUE, false)

                stations[0][2] = Pair(entity.Color.ORANGE, false); stations[0][6] = Pair(entity.Color.ORANGE, false)
                stations[1][6] = Pair(entity.Color.ORANGE, false); stations[2][5] = Pair(entity.Color.ORANGE, false)
                stations[3][7] = Pair(entity.Color.ORANGE, false); stations[3][3] = Pair(entity.Color.ORANGE, false)

                stations[0][1] = Pair(entity.Color.GREEN, false); stations[1][0] = Pair(entity.Color.GREEN, false)
                stations[1][4] = Pair(entity.Color.GREEN, false); stations[2][3] = Pair(entity.Color.GREEN, false)
                stations[3][6] = Pair(entity.Color.GREEN, false); stations[3][2] = Pair(entity.Color.GREEN, false)

                stations[0][3] = Pair(entity.Color.PURPLE, false); stations[0][7] = Pair(entity.Color.PURPLE, false)
                stations[1][2] = Pair(entity.Color.PURPLE, false); stations[2][4] = Pair(entity.Color.PURPLE, false)
                stations[2][0] = Pair(entity.Color.PURPLE, false); stations[3][1] = Pair(entity.Color.PURPLE, false)

                stations[1][7] = Pair(entity.Color.BLACK, false); stations[2][7] = Pair(entity.Color.BLACK, false)
                return stations
            }
            6 -> {
                stations[0][0] = Pair(entity.Color.YELLOW, false); stations[0][4] = Pair(entity.Color.YELLOW, false)
                stations[1][1] = Pair(entity.Color.YELLOW, false); stations[2][5] = Pair(entity.Color.YELLOW, false)
                stations[3][5] = Pair(entity.Color.YELLOW, false)

                stations[0][1] = Pair(entity.Color.BLUE, false); stations[1][2] = Pair(entity.Color.BLUE, false)
                stations[2][6] = Pair(entity.Color.BLUE, false); stations[3][7] = Pair(entity.Color.BLUE, false)
                stations[3][3] = Pair(entity.Color.BLUE, false)

                stations[0][3] = Pair(entity.Color.ORANGE, false); stations[0][7] = Pair(entity.Color.ORANGE, false)
                stations[1][5] = Pair(entity.Color.ORANGE, false); stations[2][3] = Pair(entity.Color.ORANGE, false)
                stations[3][6] = Pair(entity.Color.ORANGE, false)

                stations[0][5] = Pair(entity.Color.GREEN, false); stations[1][6] = Pair(entity.Color.GREEN, false)
                stations[2][4] = Pair(entity.Color.GREEN, false); stations[2][0] = Pair(entity.Color.GREEN, false)
                stations[3][1] = Pair(entity.Color.GREEN, false)

                stations[0][2] = Pair(entity.Color.PURPLE, false); stations[1][0] = Pair(entity.Color.PURPLE, false)
                stations[1][4] = Pair(entity.Color.PURPLE, false); stations[2][1] = Pair(entity.Color.PURPLE, false)
                stations[3][2] = Pair(entity.Color.PURPLE, false)

                stations[0][6] = Pair(entity.Color.BLACK, false); stations[1][3] = Pair(entity.Color.BLACK, false)
                stations[2][2] = Pair(entity.Color.BLACK, false); stations[3][4] = Pair(entity.Color.BLACK, false)
                stations[3][0] = Pair(entity.Color.BLACK, false)

                stations[1][7] = Pair(entity.Color.BLACK, false); stations[2][7] = Pair(entity.Color.BLACK, false)
                return stations
            }
            else -> {
                return emptyArray()
            }
        }
    }

    /**
     * initializes game board,
     * each board cell is a card view, click on board cell to place tile
     */

    private fun initGameBoard() {
        val mainStationPos = 3..4

        for (i in 0..7) for (j in 0..7) {

            lateinit var boardCellLabel: CardView

            if (i in mainStationPos && j in mainStationPos) {
                boardCellLabel = CardView(height = 100, width = 100,
                    front = ColorVisual.GREEN, back = ColorVisual(0, 0, 0, 0))

            } else {
                boardCellLabel = CardView(height = 100, width = 100,
                    front = ColorVisual.GREEN, back = ColorVisual.WHITE
                ).apply {

                    val boardCellTile = rootService.currentGame!!.currentTurn.gameField.field[i][j]
                    if (boardCellTile != null)
                        setTileFront(boardCellLabel, boardCellTile)

                    onMouseClicked = {
                        if (playerActionService.isPositionLegal(i+1, j+1) && isDrawStackTileChosen != null && currentTileCardView!!.currentSide == CardView.CardSide.FRONT) {
                            setTileFront(boardCellLabel,currentTile!!)
                            this.rotation = currentTile!!.rotationDegree.toDouble()
                            showFront()
                            playerActionService.placeTile(!isDrawStackTileChosen!!, i+1, j+1)
                        } else {
                            //TODO: playNopeSound()
                        }
                        /*if (!isDrawnTilePlaced) {
                            TODO: set myTile = drawnTiles[0]
                        }*/

                    }

                }
            }
            mainBoardGrid[i, j] = boardCellLabel
        }
    }



    override fun refreshAfterTileRotation(tile: Tile) {
        TODO("Not yet implemented")
    }

    override fun refreshAfterPlaceTile() {
        gameService = rootService.gameService
        playerActionService = rootService.playerActionService
        playerList = rootService.currentGame!!.currentTurn.players
        currentTurn = rootService.currentGame!!.currentTurn

        showPlayers()
        setTileFront(drawnTilesCardView, currentTurn!!.gameField.tileStack.tiles.first())
        setTileFront(handTileCardView, currentTurn!!.players[currentTurn!!.currentPlayerIndex].handTile!!)
        handTileCardView.isVisible = isMyTurn()
        handTileCardView.isDisabled = false
        handTileCardView.showFront()
        isDrawStackTileChosen = null
        //handTileCardView.flip()
        drawnTilesCardView.isDisabled = false
        drawnTilesCardView.showBack()
    }

    override fun refreshAfterUndo() {
        playerList = rootService.currentGame!!.currentTurn.players
        currentTurn = rootService.currentGame!!.currentTurn

        showPlayers()
        initGameBoard()
        setTileFront(drawnTilesCardView, currentTurn!!.gameField.tileStack.tiles.first())
        setTileFront(handTileCardView, currentTurn!!.players[currentTurn!!.currentPlayerIndex].handTile!!)
        handTileCardView.isVisible = isMyTurn()
        handTileCardView.isDisabled = false
    }

    override fun refreshAfterRedo() {
        playerList = rootService.currentGame!!.currentTurn.players
        currentTurn = rootService.currentGame!!.currentTurn

        showPlayers()
        initGameBoard()
        setTileFront(drawnTilesCardView, currentTurn!!.gameField.tileStack.tiles.first())
        setTileFront(handTileCardView, currentTurn!!.players[currentTurn!!.currentPlayerIndex].handTile!!)
        handTileCardView.isVisible = isMyTurn()
        handTileCardView.isDisabled = false
    }

   // override fun refreshAfterGameFinished() {

   // }

    private fun setTileFront(tileCardView: CardView, tile: Tile){
        if (tile == Tile(mutableListOf(Pair(0,1),Pair(2,7),Pair(3,4),Pair(5,6))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(0,0))
        else if (tile == Tile(mutableListOf(Pair(0,7),Pair(1,4),Pair(2,3),Pair(5,6))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(1,0))
        else if (tile == Tile(mutableListOf(Pair(0,7),Pair(1,2),Pair(3,6),Pair(4,5))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(2,0))
        else if (tile == Tile(mutableListOf(Pair(0,5),Pair(1,2),Pair(3,4),Pair(6,7))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(3,0))

        else if (tile == Tile(mutableListOf(Pair(0,5),Pair(1,2),Pair(3,6),Pair(4,7))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(0,1))
        else if (tile == Tile(mutableListOf(Pair(0,5),Pair(1,6),Pair(2,7),Pair(3,4))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(1,1))
        else if (tile == Tile(mutableListOf(Pair(0,3),Pair(1,4),Pair(2,7),Pair(5,6))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(2,1))
        else if (tile == Tile(mutableListOf(Pair(0,7),Pair(1,4),Pair(2,5),Pair(3,6))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(3,1))

        else if (tile == Tile(mutableListOf(Pair(0,1),Pair(2,5),Pair(3,4),Pair(6,7))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(0,2))
        else if (tile == Tile(mutableListOf(Pair(0,1),Pair(2,3),Pair(4,7),Pair(5,6))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(1,2))
        else if (tile == Tile(mutableListOf(Pair(0,7),Pair(1,6),Pair(2,3),Pair(4,5))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(2,2))
        else if (tile == Tile(mutableListOf(Pair(0,3),Pair(1,2),Pair(4,5),Pair(6,7))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(3,2))

        else if (tile == Tile(mutableListOf(Pair(0,1),Pair(2,5),Pair(3,6),Pair(4,7))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(0,3))
        else if (tile == Tile(mutableListOf(Pair(0,5),Pair(1,6),Pair(2,3),Pair(4,7))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(1,3))
        else if (tile == Tile(mutableListOf(Pair(0,3),Pair(1,6),Pair(2,7),Pair(4,5))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(2,3))
        else if (tile == Tile(mutableListOf(Pair(0,3),Pair(1,4),Pair(2,5),Pair(6,7))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(3,3))

        else if (tile == Tile(mutableListOf(Pair(0,7),Pair(1,6),Pair(2,5),Pair(3,4))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(0,4))
        else if (tile == Tile(mutableListOf(Pair(0,3),Pair(1,2),Pair(4,7),Pair(5,6))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(1,4))

        else if (tile == Tile(mutableListOf(Pair(0,5),Pair(1,4),Pair(2,3),Pair(6,7))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(2,4))
        else if (tile == Tile(mutableListOf(Pair(0,1),Pair(2,7),Pair(3,6),Pair(4,5))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(3,4))

        else if (tile == Tile(mutableListOf(Pair(0,1),Pair(2,3),Pair(4,5),Pair(6,7))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(0,5))

        else if (tile == Tile(mutableListOf(Pair(0,7),Pair(1,2),Pair(3,4),Pair(5,6))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(1,5))

        else if (tile == Tile(mutableListOf(Pair(0,5),Pair(1,4),Pair(2,7),Pair(3,6))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(2,5))

        else if (tile == Tile(mutableListOf(Pair(0,3),Pair(1,6),Pair(2,5),Pair(4,7))))
            tileCardView.frontVisual = ImageVisual(cardImageLoader.frontImage(3,5))

        else println(tile.ports + " TIME TO SCREAM!!")
    }



    private fun isMyTurn() = gameService.isLocalOnlyGame ||
            currentTurn?.currentPlayerIndex?.let { currentTurn?.players?.get(it)?.name } == rootService.networkService.playerName

}