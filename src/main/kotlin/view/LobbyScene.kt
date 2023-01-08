package view

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.CheckBox
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.CompoundVisual
import tools.aqua.bgw.visual.ImageVisual
import tools.aqua.bgw.visual.TextVisual
import java.awt.Color

/**
 * shows LobbyScene when clicked on Hot Seat Mode (Main Menu Scene)
 *
 * [playersJoined]: counter representing amount of players in Table
 * [realAISelection]: stores player input in addPlayer dialogue
 * [colorsPicked]: stores if a color is claimed by a player
 * [backToMainMenuSceneButton]: button to return to the main Menu
 * [playerLabel]: Header for the Player List
 * [shuffleTurnOrderButton], [shuffleTurnOrderCheckbox]: Checkbox for order shuffling option
 * [allowTileRotationButton], [allowTileRotationCheckbox]: Checkbox for tile rotation option
 * [playerLabel],[playerBoxLabel]: Table showing player Names on the left
 * [deletePlayerLabel]: deletes a player Name from the Table containing player Names
 * [colorPicker]: Buttons to pick leftover color for players
 * [playerColorLabel]:  Color square appears in player Table to display picked color for player
 * [addPlayerButton]: Button for addPlayer dialogue to start. only appears if players < 6
 * [aiPlayerButton], [aiPlayerButtonTrigger]: highlighted Button; if selected smartAI Checkbox appears
 * [hotseatPlayerButton], [hotseatPlayerButtonTrigger]: highlighted Button
 * [smartAI], [smartAICheckbox]: Checkbox for big smart AI option
 * [nameFields]: appears in corresponding line in player table for input in addPlayer dialogue
 * [confirmButton]: Button to appear in addPlayer dialogue when necessary options are input
 * [quitButton]: exits the Game
 * [musicToggleButton], [soundToggleButton]: toggles music/sound on off
 */

class LobbyScene : MenuScene(1920, 1080) {

    var playersJoined = 0

    var realAISelection: Int? = null

    var colorsPicked = mutableListOf(false, false, false, false, false, false)
    var colorPos = 0

    val backToMainMenuSceneButton = Button(
        width = 600, height = 100, posX = 660, posY = 40, visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 }, TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"), text = "Back to Main Menu")))

    val shuffleTurnOrderButton = Button(
        width = 600, height = 100, posX = 1200, posY = 200, visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 }, TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"), text = "Shuffle Turn Order"))
    ).apply { onMouseClicked = { shuffleTurnOrderCheckbox.isChecked = !shuffleTurnOrderCheckbox.isChecked } }

    val shuffleTurnOrderCheckbox = CheckBox(1830, 233)

    val allowTileRotationButton = Button(
        width = 600, height = 100, posX = 1200, posY = 330, visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 }, TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"), text = "Allow Tile Rotation" ))
    ).apply { onMouseClicked = { allowTileRotationCheckbox.isChecked = !allowTileRotationCheckbox.isChecked } }

    val allowTileRotationCheckbox = CheckBox(1830, 363)

    val playerLabel = Label(
        width = 300, height = 100, posX = 100, posY = 100, visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.1 }, TextVisual(
                font = Font(size = 60, color = Color.BLUE, family = "Calibri"), text = "Players")))

    val playerBoxLabel = mutableListOf(
        Label(width = 800, height = 80, posX = 100, posY = 250, visual = CompoundVisual(
                ColorVisual(63, 255, 63).apply { transparency = 0.3 }, TextVisual(
                    font = Font(size = 60, color = Color.BLACK, family = "Calibri"),
                    text = "", alignment = Alignment.CENTER_LEFT, offsetX = 20))),
        Label(width = 800, height = 80, posX = 100, posY = 350, visual = CompoundVisual(
            ColorVisual(63, 255, 63).apply { transparency = 0.3 }, TextVisual(
                font = Font(size = 60, color = Color.BLACK, family = "Calibri"),
                text = "", alignment = Alignment.CENTER_LEFT, offsetX = 20))),
        Label(width = 800, height = 80, posX = 100, posY = 450, visual = CompoundVisual(
            ColorVisual(63, 255, 63).apply { transparency = 0.3 }, TextVisual(
                font = Font(size = 60, color = Color.BLACK, family = "Calibri"),
                text = "", alignment = Alignment.CENTER_LEFT, offsetX = 20))),
        Label(width = 800, height = 80, posX = 100, posY = 550, visual = CompoundVisual(
            ColorVisual(63, 255, 63).apply { transparency = 0.3 }, TextVisual(
                font = Font(size = 60, color = Color.BLACK, family = "Calibri"),
                text = "", alignment = Alignment.CENTER_LEFT, offsetX = 20))),
        Label(width = 800, height = 80, posX = 100, posY = 650, visual = CompoundVisual(
            ColorVisual(63, 255, 63).apply { transparency = 0.3 }, TextVisual(
                font = Font(size = 60, color = Color.BLACK, family = "Calibri"),
                text = "", alignment = Alignment.CENTER_LEFT, offsetX = 20))),
        Label(width = 800, height = 80, posX = 100, posY = 750, visual = CompoundVisual(
            ColorVisual(63, 255, 63).apply { transparency = 0.3 }, TextVisual(
                font = Font(size = 60, color = Color.BLACK, family = "Calibri"),
                text = "", alignment = Alignment.CENTER_LEFT, offsetX = 20))))

    private val deletePlayerLabel = listOf(
        Button(width = 80, height = 80, posX = 800.0, posY = 250.0,
            visual = TextVisual(text = "-", font = Font(size = 20, family = "Calibri"))
        ).apply { onMouseClicked = { deletePlayer(playersJoined,colorPos) } },
        Button(width = 80, height = 80, posX = 800.0, posY = 350.0,
            visual = TextVisual(text = "-", font = Font(size = 20, family = "Calibri"))
        ).apply { onMouseClicked = { deletePlayer(playersJoined,colorPos) } },
        Button(width = 80, height = 80, posX = 800.0, posY = 450.0,
            visual = TextVisual(text = "-", font = Font(size = 20, family = "Calibri"))
        ).apply { onMouseClicked = { deletePlayer(playersJoined,colorPos) } },
        Button(width = 80, height = 80, posX = 800.0, posY = 550.0,
            visual = TextVisual(text = "-", font = Font(size = 20, family = "Calibri"))
        ).apply { onMouseClicked = { deletePlayer(playersJoined,colorPos) } },
        Button(width = 80, height = 80, posX = 800.0, posY = 650.0,
            visual = TextVisual(text = "-", font = Font(size = 20, family = "Calibri"))
        ).apply { onMouseClicked = { deletePlayer(playersJoined,colorPos) } })

    val colorPicker = listOf(
        Button(width = 40, height = 40, visual = entity.Color.YELLOW.toRGB()).apply {
            onMouseClicked = { colorPicked(this) }
            isDisabled = true; opacity = 0.0 },
        Button(width = 40, height = 40, visual = entity.Color.BLUE.toRGB()).apply {
            onMouseClicked = { colorPicked(this) }
            isDisabled = true; opacity = 0.0 },
        Button(width = 40, height = 40, visual = entity.Color.ORANGE.toRGB()).apply {
            onMouseClicked = { colorPicked(this) }
            isDisabled = true; opacity = 0.0 },
        Button(width = 40, height = 40, visual = entity.Color.GREEN.toRGB()).apply {
            onMouseClicked = { colorPicked(this) }
            isDisabled = true; opacity = 0.0 },
        Button(width = 40, height = 40, visual = entity.Color.PURPLE.toRGB()).apply {
            onMouseClicked = { colorPicked(this) }
            isDisabled = true; opacity = 0.0 },
        Button(width = 40, height = 40, visual = entity.Color.BLACK.toRGB()).apply {
            onMouseClicked = { colorPicked(this) }
            isDisabled = true; opacity = 0.0 })

    val playerColorLabel = mutableListOf(
        Label(width = 80, height = 80), Label(width = 80, height = 80), Label(width = 80, height = 80),
        Label(width = 80, height = 80), Label(width = 80, height = 80), Label(width = 80, height = 80))

    val addPlayerButton = Button(width = 80, height = 80, text = "+").apply { onMouseClicked = { addPlayer() } }

    val aiPlayerButton = Button(width = 270, height = 100, posX = 1200, posY = 500,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(font = Font(size = 60, color = Color.RED, family = "Calibri"), text = "AI")))

    val aiPlayerButtonTrigger = Label(width = 270, height = 100, posX = 1200, posY = 500,
        visual = ColorVisual(63, 255, 63)
    ).apply {
        opacity = 0.0
        onMouseEntered = { if (realAISelection == null) opacity = 0.5 }
        onMouseExited = { if (realAISelection == null) opacity = 0.0 }
        onMouseClicked = { realAISelection = 0; realAISelected() }
    }

    val hotseatPlayerButton = Button(width = 270, height = 100, posX = 1550, posY = 500,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(font = Font(size = 60, color = Color.RED, family = "Calibri"), text = "real")))

    val hotseatPlayerButtonTrigger = Label(width = 270, height = 100, posX = 1550, posY = 500,
        visual = ColorVisual(63, 255, 63)
    ).apply {
        opacity = 0.0
        onMouseEntered = { if (realAISelection == null) opacity = 0.5 }
        onMouseExited = { if (realAISelection == null) opacity = 0.0 }
        onMouseClicked = { realAISelection = 1; realAISelected() }
    }

    val smartAI = Button(width = 270, height = 100, posX = 1200, posY = 630,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(font = Font(size = 60, color = Color.RED, family = "Calibri"), text = "big smart"))
    ).apply {
        isDisabled = true; opacity = 0.0
        onMouseClicked = { smartAICheckbox.isChecked = !smartAICheckbox.isChecked }
    }

    val smartAICheckbox = CheckBox(1500, 663).apply { isDisabled = true; opacity = 0.0 }

    val nameFields = mutableListOf(
        TextField(width = 280, height = 80, posX = 100, posY = 350, prompt = "Enter Name",
            font = Font(size = 40, family = "Calibri"),
        ).apply { onKeyTyped = { playerConfigured() } },
        TextField(width = 280, height = 80, posX = 100, posY = 450, prompt = "Enter Name",
            font = Font(size = 40, family = "Calibri"),
        ).apply { onKeyTyped = { playerConfigured() } },
        TextField(width = 280, height = 80, posX = 100, posY = 550, prompt = "Enter Name",
            font = Font(size = 40, family = "Calibri"),
        ).apply { onKeyTyped = { playerConfigured() } },
        TextField(width = 280, height = 80, posX = 100, posY = 650, prompt = "Enter Name",
            font = Font(size = 40, family = "Calibri"),
        ).apply { onKeyTyped = { playerConfigured() } },
        TextField(width = 280, height = 80, posX = 100, posY = 750, prompt = "Enter Name",
            font = Font(size = 40, family = "Calibri"),
        ).apply { onKeyTyped = { playerConfigured() } },
        TextField(width = 280, height = 80, posX = 100, posY = 850, prompt = "Enter Name",
            font = Font(size = 40, family = "Calibri"),
        ).apply { onKeyTyped = { playerConfigured() } },)

    val confirmButton = Button(width = 400, height = 100, posX = 1200, posY = 793,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(font = Font(size = 60, color = Color.RED, family = "Calibri"), text = "Confirm"))
    ).apply { onMouseClicked = { playerAddFinished() } }

    val quitButton = Button(width = 140, height = 140, posX = 1700, posY = 40,
        visual = ImageVisual("quit_button.png"))

    val musicToggleButton = Button(width = 140, height = 140, posX = 430, posY = 880,
        visual = ImageVisual("music_enabled.png"))

    val soundToggleButton = Button(width = 140, height = 140, posX = 600, posY = 880,
        visual = ImageVisual("sound_enabled.png"))

    init {
        addComponents(
            backToMainMenuSceneButton, playerLabel,
            playerBoxLabel[0], playerBoxLabel[1], playerBoxLabel[2],
            playerBoxLabel[3], playerBoxLabel[4], playerBoxLabel[5],
            shuffleTurnOrderButton, shuffleTurnOrderCheckbox, allowTileRotationButton, allowTileRotationCheckbox,
            quitButton,
            musicToggleButton, soundToggleButton,
            colorPicker[0], colorPicker[1], colorPicker[2], colorPicker[3], colorPicker[4], colorPicker[5],
            deletePlayerLabel[0],deletePlayerLabel[1],deletePlayerLabel[2],
            deletePlayerLabel[3],deletePlayerLabel[4]
        )
        for (i in 0..4) {
            deletePlayerLabel[i].isDisabled = true
            deletePlayerLabel[i].opacity = 0.0
        }
        opacity = 0.0
        showColorPicker(playersJoined)
       // deletePlayerLabel[playersJoined]
    }

    /**
     * displays leftover player color selection in corresponding line in player table
     */

    fun showColorPicker(pos: Int) {

        for (i in 0..5) {
            if (!colorsPicked[i]) {
                colorPicker[i].isDisabled = false
                colorPicker[i].opacity = 1.0
            }
        }
        colorPicker[0].apply { posX = 700.0; posY = 250.0 + pos * 100.0 }
        colorPicker[1].apply { posX = 700.0; posY = 290.0 + pos * 100.0 }
        colorPicker[2].apply { posX = 740.0; posY = 250.0 + pos * 100.0 }
        colorPicker[3].apply { posX = 740.0; posY = 290.0 + pos * 100.0 }
        colorPicker[4].apply { posX = 780.0; posY = 250.0 + pos * 100.0 }
        colorPicker[5].apply { posX = 780.0; posY = 290.0 + pos * 100.0 }
    }

    /**
     * stores color selection after removing selection buttons and displays it in player table
     */

    fun colorPicked(button: Button) {
        //players[playersJoined].color = color
        for (i in 0..5) {
            if (!colorsPicked[i]) {
                colorPicker[i].isDisabled = true
                colorPicker[i].opacity = 0.0
            }
        }

        colorPos = colorPicker.indexOf(button)
        println(colorPos)

        colorPicker[colorPicker.indexOf(button)].isDisabled = true
        colorPicker[colorPicker.indexOf(button)].opacity = 0.0
        colorsPicked[colorPicker.indexOf(button)] = true

        var flag =1
        if (playersJoined >=1) {
            deletePlayerLabel[playersJoined].isDisabled = false
            deletePlayerLabel[playersJoined].opacity = 1.0

            for (i in flag..playersJoined-1) {
                deletePlayerLabel[i].isDisabled = true
                deletePlayerLabel[i].opacity = 0.0
            }
        }

        addComponents(playerColorLabel[playersJoined])

        playerColorLabel[playersJoined].apply {
            posX = 700.0
            posY = 250.0 + 100.0 * playersJoined
            visual = button.visual
        }

        println(colorsPicked)

        playersJoined++
        if (playersJoined < 6) {
            addComponents(addPlayerButton)
            addPlayerButton.apply { posX = 120.0; posY = 250.0 + 100.0 * playersJoined }
        }

    }

    /**
     * addPlayer dialogue start
     */

    fun addPlayer() {
        addComponents(
            aiPlayerButton, aiPlayerButtonTrigger, hotseatPlayerButton, hotseatPlayerButtonTrigger,
            smartAI, smartAICheckbox,
            nameFields[playersJoined - 1],
            confirmButton
        )
        removeComponents(addPlayerButton)
    }

    /**
     * clears playerName from the Table containing players Name
     */

    fun deletePlayer(pos: Int, colorPos: Int) {

        println("colorPos" + colorPos)
        colorsPicked[colorPos] = false
        //colorPicker[colorPos].isDisabled = false
        //colorPicker[colorPos].opacity = 1.0
        if (playersJoined >= 1) {
            playerBoxLabel[playersJoined - 1].visual = CompoundVisual(ColorVisual.WHITE.apply { transparency = 0.1 },
                TextVisual(
                    font = Font(size = 60, color = Color.BLACK, family = "Calibri"), text = ""
                )
            )
            removeComponents(
                nameFields[playersJoined - 2]

            )

            nameFields[pos - 1].text = ""
            nameFields[pos].text = ""

            println("colorpoos" + colorsPicked[colorPos])

            removeComponents(nameFields[pos - 1])
            removeComponents(playerColorLabel[pos - 1])
            removeComponents(deletePlayerLabel[pos - 1])

            addPlayerButton.reposition(100, playerBoxLabel[pos - 1].posY)
            playersJoined--
            println(playersJoined)

        }
    }

    /**
     * method for highlighting real/AI Buttons and (dis)appearing smartAI Checkbox
     */

    fun realAISelected() {
        playerConfigured()
        if (realAISelection == 0) {
            hotseatPlayerButtonTrigger.opacity = 0.0
            aiPlayerButtonTrigger.opacity = 0.5
            if (smartAI.isDisabled) {
                smartAI.isDisabled = false; smartAI.opacity = 1.0
                smartAICheckbox.isDisabled = false; smartAICheckbox.opacity = 1.0
            }
        } else {
            hotseatPlayerButtonTrigger.opacity = 0.5
            aiPlayerButtonTrigger.opacity = 0.0
            if (!smartAI.isDisabled) {
                smartAI.isDisabled = true; smartAI.opacity = 0.0
                smartAICheckbox.isDisabled = true; smartAICheckbox.opacity = 0.0
            }
        }
    }

    /**
     * necessary conditions check for confirmButton to appear
     */

    fun playerConfigured() {
        if (confirmButton.isDisabled && nameFields[playersJoined - 1].text != "" && realAISelection != null) {
            confirmButton.isDisabled = false; confirmButton.opacity = 1.0
        }
    }

    /**
     * after confirmButton clicked: input name with player type appended display in player table with green entry.
     * reset and removing components for addPlayer dialogue
     * call of colorPicker to finish add/configure Player dialogue loop
     */

    fun playerAddFinished() {
        playerBoxLabel[playersJoined].visual = CompoundVisual(
            ColorVisual(63, 255, 63).apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.BLACK, family = "Calibri"),
                text = (nameFields[playersJoined - 1].text +
                        if (realAISelection == 0)
                            if (smartAICheckbox.isChecked) " (AI)"
                            else " (dumb)"
                        else ""),
                alignment = Alignment.CENTER_LEFT, offsetX = 20))

        aiPlayerButtonTrigger.opacity = 0.0; hotseatPlayerButtonTrigger.opacity = 0.0
        smartAICheckbox.opacity = 0.0; smartAICheckbox.isDisabled = true
        smartAI.opacity = 0.0; smartAI.isDisabled = true
        realAISelection = null
        smartAICheckbox.isChecked = false
        removeComponents(
            aiPlayerButton, aiPlayerButtonTrigger, hotseatPlayerButton, hotseatPlayerButtonTrigger,
            smartAI, smartAICheckbox,
            nameFields[playersJoined - 1],
            confirmButton
        )

        showColorPicker(playersJoined)
    }

}