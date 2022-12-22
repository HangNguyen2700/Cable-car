package view

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.CompoundVisual
import tools.aqua.bgw.visual.TextVisual
import java.awt.Color

class MainMenuScene : MenuScene(1920,1080) {
    private val buttonTextFont = Font(50, color = Color.WHITE, family = "Calibri")

    private val menuLabel = Label(width = 600, height = 100, posX = 650, posY = 60,
        visual = CompoundVisual(
            TextVisual(
                font = Font(size = 60, color = Color.WHITE, family = "Calibri"),
                text = "MENU"
            )
        )
    )

    private val nameField = TextField(width = 400, height = 80, posX = 250, posY = 500,
        prompt = "Enter your Name",
        font = Font(size = 40, family = "Calibri"),
    )

    private val nameError = Label(width = 1200, height = 600, posX = 360, posY = 150,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.8 },
            TextVisual(
                font = Font(size = 60, color = Color(186,136,133,255), family = "Calibri"),
                text = "Enter Name Before Starting Game",
                offsetY = -200
            )
        )
    )

    private val closeNameErrorButton = Button(width = 300, height = 100, posX = 810, posY = 450,
        font = buttonTextFont,
        visual = ColorVisual.RED,
        text = "close",
    ).apply {
        onMouseClicked = {
            removeComponents(nameError,this)
        }
    }

    private val newGameLabel = Label(width = 600, height = 100, posX = 1100, posY = 230,
        visual = CompoundVisual(
            TextVisual(
                font = Font(size = 50, color = Color.WHITE, family = "Calibri"),
                text = "START NEW GAME"
            )
        )
    )

    private val joinButton = Button(width = 400, height = 100, posX = 1200, posY = 360,
        visual = CompoundVisual(
            ColorVisual(186,136,133,255),
            TextVisual(
                font = buttonTextFont,
                alignment = Alignment.CENTER,
                text = "Join Game"
            )
        )
    ).apply {
        onMouseClicked = {
            if (nameField.text == "") {
                addComponents(nameError, closeNameErrorButton)
            }
        }
    }

    private val hostButton = Button(width = 400, height = 100, posX = 1200, posY = 510,
        visual = CompoundVisual(
            ColorVisual(186,136,133,255),
            TextVisual(
                font = buttonTextFont,
                text = "Host Game"
            )
        )
    ).apply {
        onMouseClicked = {
            if (nameField.text == "") {
                addComponents(nameError, closeNameErrorButton)
            }
        }
    }

    private val hotseatButton = Button(width = 400, height = 100, posX = 1200, posY = 660,
        visual = CompoundVisual(
            ColorVisual(186,136,133,255),
            TextVisual(
                font = buttonTextFont,
                text = "Hotseat Mode"
            )
        )
    ).apply {
        onMouseClicked = {
            if (nameField.text == "") {
                addComponents(nameError, closeNameErrorButton)
            }
        }
    }

    private val creditsButton = Button(width = 300, height = 100, posX = 1150, posY = 900,
        visual = CompoundVisual(
            ColorVisual(108,139,116,255),
            TextVisual(
                font = buttonTextFont,
                text = "Credits"
            )
        )
    )

    val backToTitleSceneButton = Button(width = 300, height = 100, posX = 1500, posY = 900,
        visual = CompoundVisual(
            ColorVisual(108,139,116,255),
            TextVisual(
                font = buttonTextFont,
                text = "Back to Title"
            )
        )
    )

    private val quitButton = Button(width = 300, height = 100, posX = 100, posY = 900,
        visual = CompoundVisual(
            ColorVisual(194,64,64,255),
            TextVisual(
                font = buttonTextFont,
                text = "Quit"
            )
        )
    )

    private val musicToggleButton = Button(width = 300, height = 100, posX = 450, posY = 900,
        visual = CompoundVisual(
            ColorVisual(186,136,133,255),
            TextVisual(
                font = buttonTextFont,
                text = "Music"
            )
        )
    )

    private val soundToggleButton = Button(width = 300, height = 100, posX = 800, posY = 900,
        visual = CompoundVisual(
            ColorVisual(186,136,133,255),
            TextVisual(
                font = buttonTextFont,
                text = "Sound"
            )
        )
    )

    init{
        background = ColorVisual(155,94,95,255)
        addComponents(
            menuLabel,
            nameField,
            newGameLabel, joinButton, hostButton, hotseatButton,
            creditsButton, backToTitleSceneButton,
            quitButton, musicToggleButton, soundToggleButton)
//        opacity = 0.0
    }

}