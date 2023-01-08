package view

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.CompoundVisual
import tools.aqua.bgw.visual.ImageVisual
import tools.aqua.bgw.visual.TextVisual
import java.awt.Color

class QuickMenuGameScene : MenuScene(600,1080) {

    val quitButton = Button(width = 300, height = 100, posX = 100, posY = 900,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(font = Font(size = 60, color = Color.RED, family = "Calibri"),
                text = "Quit")))

    val musicToggleButton = Button(width = 140, height = 140, posX = 470, posY = 880,
        visual = ImageVisual("music_enabled.png")
    )

    val soundToggleButton = Button(width = 140, height = 140, posX = 660, posY = 880,
        visual = ImageVisual("sound_enabled.png")
    )

   // val backToGameScene



    init{
        addComponents(
        quitButton,musicToggleButton,soundToggleButton
        )

    }


}