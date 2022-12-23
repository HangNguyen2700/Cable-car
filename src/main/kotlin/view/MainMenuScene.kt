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

    private val nameField = TextField(width = 400, height = 80, posX = 200, posY = 400,
        prompt = "Enter your Name",
        font = Font(size = 40, family = "Calibri"),
    )

    private val nameError = Label(width = 1200, height = 600, posX = 360, posY = 150,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.8 },
            TextVisual(
                font = Font(size = 60, color = Color.BLUE, family = "Calibri"),
                text = "Enter Name Before Starting Game",
                offsetY = -200
            )
        )
    )

    private val closeNameErrorButton = Button(width = 300, height = 100, posX = 810, posY = 450,
        font = Font(size = 40, family = "Calibri"),
        visual = ColorVisual(255,40,40),
        text = "close",
    ).apply {
        onMouseClicked = {
            removeComponents(nameError,this)
        }
    }

    private val newGameLabel = Label(width = 600, height = 100, posX = 1100, posY = 150,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.1 },
            TextVisual(
                font = Font(size = 60, color = Color.BLUE, family = "Calibri"),
                text = "Start New Game"
            )
        )
    )

    private val joinButton = Button(width = 400, height = 100, posX = 1200, posY = 300,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"),
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

    private val hostButton = Button(width = 400, height = 100, posX = 1200, posY = 450,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"),
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

    private val hotseatButton = Button(width = 400, height = 100, posX = 1200, posY = 600,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"),
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

    private val creditsButton = Button(width = 300, height = 100, posX = 1500, posY = 900,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Credits"
            )
        )
    )

    val backToTitleSceneButton = Button(width = 400, height = 100, posX = 760, posY = 40,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "back to Title"
            )
        )
    )

    private val quitButton = Button(width = 300, height = 100, posX = 100, posY = 900,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Quit"
            )
        )
    )

    private val musicToggleButton = Button(width = 300, height = 100, posX = 450, posY = 900,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Music"
            )
        )
    )

    private val soundToggleButton = Button(width = 300, height = 100, posX = 800, posY = 900,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Sound"
            )
        )
    )

    init{
        addComponents(
            nameField,
            newGameLabel, joinButton, hostButton, hotseatButton,
            creditsButton, backToTitleSceneButton,
            quitButton, musicToggleButton, soundToggleButton)
        opacity = 0.0
    }

}