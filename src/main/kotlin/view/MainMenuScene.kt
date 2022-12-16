package view

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.CompoundVisual
import tools.aqua.bgw.visual.TextVisual
import java.awt.Color

class MainMenuScene : MenuScene(1920,1080) {

    private val joinButton = Button(width = 300, height = 100, posX = 1500, posY = 300,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Join Game"
            )
        )
    )

    private val hostButton = Button(width = 300, height = 100, posX = 1500, posY = 450,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Host Game"
            )
        )
    )

    private val hotseatButton = Button(width = 300, height = 100, posX = 1500, posY = 600,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Hotseat Mode"
            )
        )
    )

    private val creditsButton = Button(width = 300, height = 100, posX = 1500, posY = 900,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Credits"
            )
        )
    )

    private val nameField = TextField(width = 400, height = 80, posX = )

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
        addComponents(joinButton, hostButton, hotseatButton, creditsButton, quitButton, musicToggleButton, soundToggleButton)
    }

}