package view

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.CompoundVisual
import tools.aqua.bgw.visual.TextVisual
import java.awt.Color

/**
 * main menu hub displayed after title scene
 *
 * [backToTitleSceneButton]: Button for people who like the title screen
 * [nameFieldLabel], [nameField]: player name input prompt
 * [nameErrorLabel], [closeNameErrorButton]: displays error message
 * [newGameLabel], [joinButton], [hostButton], [hotseatButton]:
 * new Game Buttons with check for a name being input via CCApplication
 * [creditsButton]: transition to Credits Scene
 * [quitButton]: exits the Game
 * [musicToggleButton], [soundToggleButton]: toggles music/sound on off
 */

class MainMenuScene : MenuScene(1920,1080) {

    val backToTitleSceneButton = Button(width = 400, height = 100, posX = 760, posY = 40,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "back to Title")))

    private val nameFieldLabel = Label(width = 600, height = 100, posX = 100, posY = 250,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.1 },
            TextVisual(font = Font(size = 60, color = Color.BLUE, family = "Calibri"),
                text = "Player Name")))

    val nameField = TextField(width = 400, height = 80, posX = 200, posY = 400,
        prompt = "Enter your Name",
        font = Font(size = 40, family = "Calibri"))

    private val nameErrorLabel = Label(width = 1200, height = 600, posX = 360, posY = 150,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.8 },
            TextVisual(font = Font(size = 60, color = Color.BLUE, family = "Calibri"), offsetY = -200,
                text = "Enter Name Before Starting Game",))
    ).apply { onMouseClicked = { nameErrorClose() } }

    private val closeNameErrorButton = Button(width = 300, height = 100, posX = 810, posY = 450,
        font = Font(size = 40, family = "Calibri"),
        visual = ColorVisual(255,40,40),
        text = "close",
    ).apply { onMouseClicked = { nameErrorClose() } }

    private val newGameLabel = Label(width = 600, height = 100, posX = 1200, posY = 250,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.1 },
            TextVisual(font = Font(size = 60, color = Color.BLUE, family = "Calibri"),
                text = "Start New Game")))

    val joinButton = Button(width = 400, height = 100, posX = 1300, posY = 400,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Join Game")))

    val hostButton = Button(width = 400, height = 100, posX = 1300, posY = 550,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Host Game")))

    val hotseatButton = Button(width = 400, height = 100, posX = 1300, posY = 700,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Hotseat Mode")))

    private val creditsButton = Button(width = 300, height = 100, posX = 1500, posY = 900,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Credits")))

    val quitButton = Button(width = 300, height = 100, posX = 100, posY = 900,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Quit")))

    val musicToggleButton = Button(width = 300, height = 100, posX = 450, posY = 900,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Music")))

    val soundToggleButton = Button(width = 300, height = 100, posX = 800, posY = 900,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Sound")))

    init{
        addComponents(
            nameField, nameFieldLabel,
            newGameLabel, joinButton, hostButton, hotseatButton,
            creditsButton, backToTitleSceneButton,
            quitButton, soundToggleButton, musicToggleButton
        )
        opacity = 0.0
    }

    /**
     * displays name error and disables new game buttons
     */

    fun nameErrorDisplay() {
        addComponents(nameErrorLabel, closeNameErrorButton)
        nameField.isDisabled = true
        joinButton.isDisabled = true
        hostButton.isDisabled = true
        hotseatButton.isDisabled = true
    }

    /**
     * closes error message and enables new game buttons again
     */

    private fun nameErrorClose() {
        removeComponents(nameErrorLabel,closeNameErrorButton)
        nameField.isDisabled = false
        joinButton.isDisabled = false
        hostButton.isDisabled = false
        hotseatButton.isDisabled = false
    }

}