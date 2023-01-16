package view

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.CheckBox
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.CompoundVisual
import tools.aqua.bgw.visual.ImageVisual
import tools.aqua.bgw.visual.TextVisual
import java.awt.Color

class NetworkJoinScene : MenuScene(1920, 1080){

    val topLabel = Label(width = 1920, height = 200, posY = 100,
        visual = TextVisual(font = Font(size = 100, color = Color.WHITE, family = "Calibri"),
            text = ("JOIN A GAME")))

    val backToMainMenuSceneButton = Button(width = 600, height = 100, posX = 100, posY = 880,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(font = Font(size = 60, color = Color.RED, family = "Calibri"), text = "Back to Main Menu")))

    val sessionIDLabel = Label(width = 600, height = 100, posX = 180, posY = 350,
        visual = TextVisual(font = Font(size = 100, color = Color.WHITE, family = "Calibri"),
            text = "SessionID"))

    val sessionIDTextField = TextField(width = 300, height = 80, posX = 330, posY = 500, prompt = "Enter SessionID",
        font = Font(size = 40, family = "Calibri")
    ).apply { onKeyTyped = { parametersInput() } }

    val secretLabel = Label(width = 600, height = 100, posX = 1140, posY = 350,
        visual = TextVisual(font = Font(size = 100, color = Color.WHITE, family = "Calibri"),
            text = "Secret"))

    val secretTextField = TextField(width = 300, height = 80, posX = 1290, posY = 500, prompt = "Enter Secret",
        font = Font(size = 40, family = "Calibri")
    ).apply { onKeyTyped = { parametersInput() } }

    val aiGameButton = Button(width = 370, height = 100, posX = 775, posY = 500,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(font = Font(size = 40, color = Color.RED, family = "Calibri"), text = "AI Tournament Mode"))
    ).apply { onMouseClicked = { aiGameCheckBox.isChecked = !aiGameCheckBox.isChecked } }

    val aiGameCheckBox = CheckBox(1175, 533)

    val joinGameButton = Button(width = 400, height = 100, posX = 760, posY = 850,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(font = Font(size = 60, color = Color.RED, family = "Calibri"), text = "Join"))
    ).apply { isDisabled = true; opacity = 0.0; onMouseClicked = { isDisabled = true; opacity = 0.0 } }

    val quitButton = Button(width = 140, height = 140, posX = 1720, posY = 60,
        visual = ImageVisual("quit_button.png"))

    val musicToggleButton = Button(width = 140, height = 140, posX = 1520, posY = 60,
        visual = ImageVisual("music_enabled.png"))

    val soundToggleButton = Button(width = 140, height = 140, posX = 1320, posY = 60,
        visual = ImageVisual("sound_enabled.png"))

    init {
        background = ColorVisual(0,0,0)
        opacity = 0.3
        addComponents(
            topLabel, backToMainMenuSceneButton,
            sessionIDLabel, sessionIDTextField, secretLabel, secretTextField,
            aiGameButton, aiGameCheckBox,
            quitButton, musicToggleButton, soundToggleButton,
            joinGameButton,
        )
    }

    fun parametersInput() {
        if (joinGameButton.isDisabled &&
            sessionIDTextField.text != "" && secretTextField.text != "") {
            joinGameButton.isDisabled = false; joinGameButton.opacity = 1.0
        }
    }
}