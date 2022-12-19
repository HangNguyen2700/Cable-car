package view

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.CheckBox
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.CompoundVisual
import tools.aqua.bgw.visual.TextVisual
import java.awt.Color

class LobbyScene : MenuScene(1920,1080) {

    val backToMainMenuSceneButton = Button(width = 600, height = 100, posX = 660, posY = 40,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Back to Main Menu"
            )
        )
    )

    val playerLabel = Label(width = 300, height = 100, posX = 100, posY = 100,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.1 },
            TextVisual(
                font = Font(size = 60, color = Color.BLUE, family = "Calibri"),
                text = "Players"
            )
        )
    )

    val playerBoxLabel1 = Label(width = 800, height = 80, posX = 100, posY = 250,
        visual = CompoundVisual(
            ColorVisual(63,255,63).apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.BLACK, family = "Calibri"),
                text = "",
                alignment = Alignment.CENTER_LEFT,
                offsetX = 20
            )
        )
    )

    val playerBoxLabel2 = Label(width = 800, height = 80, posX = 100, posY = 350,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.2 },
            TextVisual(
                font = Font(size = 60, color = Color.BLACK, family = "Calibri"),
                text = "",
                alignment = Alignment.CENTER_LEFT,
                offsetX = 20
            )
        )
    )

    val playerBoxLabel3 = Label(width = 800, height = 80, posX = 100, posY = 450,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.2 },
            TextVisual(
                font = Font(size = 60, color = Color.BLACK, family = "Calibri"),
                text = "",
                alignment = Alignment.CENTER_LEFT,
                offsetX = 20
            )
        )
    )

    val playerBoxLabel4 = Label(width = 800, height = 80, posX = 100, posY = 550,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.2 },
            TextVisual(
                font = Font(size = 60, color = Color.BLACK, family = "Calibri"),
                text = "",
                alignment = Alignment.CENTER_LEFT,
                offsetX = 20
            )
        )
    )

    val playerBoxLabel5 = Label(width = 800, height = 80, posX = 100, posY = 650,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.2 },
            TextVisual(
                font = Font(size = 60, color = Color.BLACK, family = "Calibri"),
                text = "",
                alignment = Alignment.CENTER_LEFT,
                offsetX = 20
            )
        )
    )

    val playerBoxLabel6 = Label(width = 800, height = 80, posX = 100, posY = 750,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.2 },
            TextVisual(
                font = Font(size = 60, color = Color.BLACK, family = "Calibri"),
                text = "",
                alignment = Alignment.CENTER_LEFT,
                offsetX = 20
            )
        )
    )


    val shuffleTurnOrderButton = Button(width = 600, height = 100, posX = 1200, posY = 200,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Shuffle Turn Order"
            )
        )
    ).apply { onMouseClicked = { shufflePlayerSequenceCheckbox.isChecked = !shufflePlayerSequenceCheckbox.isChecked } }

    val shufflePlayerSequenceCheckbox = CheckBox(1830, 233)

    val allowTileRotationButton = Button(width = 600, height = 100, posX = 1200, posY = 330,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Allow Tile Rotation"
            )
        )
    ).apply { onMouseClicked = { allowTileRotationCheckbox.isChecked = !allowTileRotationCheckbox.isChecked } }

    val allowTileRotationCheckbox = CheckBox(1830, 363)

    val quitButton = Button(width = 300, height = 100, posX = 100, posY = 900,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Quit"
            )
        )
    )

    val musicToggleButton = Button(width = 300, height = 100, posX = 450, posY = 900,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Music"
            )
        )
    )

    val soundToggleButton = Button(width = 300, height = 100, posX = 800, posY = 900,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Sound"
            )
        )
    )

    init {
        addComponents(
            backToMainMenuSceneButton,
            playerLabel,
            playerBoxLabel1,playerBoxLabel2,playerBoxLabel3,playerBoxLabel4,playerBoxLabel5,playerBoxLabel6,
            shuffleTurnOrderButton, shufflePlayerSequenceCheckbox,
            allowTileRotationButton, allowTileRotationCheckbox,
            quitButton, musicToggleButton, soundToggleButton
        )
        opacity = 0.0
    }
}