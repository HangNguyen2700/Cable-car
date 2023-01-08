package view

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import java.awt.Color

class QuickMenuGameScene : MenuScene(600, 1080) {

    val MenuText = Label(
        width = 213, height = 104, posX = 143, posY = 195,
        text = "Menu",
        font = Font(size = 48, color = Color.WHITE, family = "Calibri", fontStyle = Font.FontStyle.ITALIC)
    )


    val soundToggleText = Label(
        width = 213, height = 100, posX = 143, posY = 402,
        text = "Sound on/off",
        font = Font(size = 30, color = Color.WHITE, family = "Calibri", fontStyle = Font.FontStyle.ITALIC)
    )

    val soundToggleButton = Button(
        width = 100, height = 100, posX = 423, posY = 384,
        visual = ImageVisual("sound_enabled.png")
    )

    val musicToggleText = Label(
        width = 213, height = 57, posX = 143, posY = 558,
        text = "Music on/off",
        font = Font(size = 30, color = Color.WHITE, family = "Calibri", fontStyle = Font.FontStyle.ITALIC)
    )

    val musicToggleButton = Button(
        width = 100, height = 100, posX = 420, posY = 520,
        visual = ImageVisual("music_enabled.png")
    )

    val mainMenu = Button(
        width = 213, height = 57, posX = 143, posY = 718,
        text = "Main Menu",
        font = Font(size = 30, color = Color.WHITE, family = "Calibri", fontStyle = Font.FontStyle.ITALIC)
    )

    val exitMenu = Button(
        width = 50, height = 50, posX = 437, posY = 81,
        visual = ImageVisual("Quit.png")
    ).apply{
        onMouseClicked={MenuPopUp()}
    }
    val quitButton = Button(
        width = 213, height = 57, posX = 143, posY = 876,
        text = "Quit",
        font = Font(size = 30, color = Color.WHITE, family = "Calibri", fontStyle = Font.FontStyle.ITALIC)
    )

    init {
        background = ColorVisual(155, 94, 95, 255)
        addComponents(

            MenuText, exitMenu,
            musicToggleButton, soundToggleButton, soundToggleText, musicToggleText,
            quitButton, mainMenu

        )

    }


}