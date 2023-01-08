package view

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

class ConfirmQuitMenuScene: MenuScene(1920,1080)  {

    private val popUpText = Label(width = 1920, height = 400,  posY = 200,
        font = Font(size = 100, color = Color.RED, family = "Calibri"),
        text = "Are you sure about that?")

    val yesButton = Button(width = 300, height = 100, posX = 370, posY = 600,
        visual = ColorVisual.WHITE.apply { transparency = 0.7 },
        font = Font(size = 60, color = Color.RED, family = "Calibri"),
        text = "Yes")

    val noButton = Button(width = 300, height = 100, posX = 1260, posY = 600,
        visual = ColorVisual.WHITE.apply { transparency = 0.7 },
        font = Font(size = 60, color = Color.GREEN, family = "Calibri"),
        text = "No")

    val backgroundLabel = Label(width = 1600, height = 900, posX = 160, posY = 90,
        visual = ColorVisual.WHITE).apply { opacity = 0.3 }

   init {
       opacity = 0.0
       addComponents(backgroundLabel, popUpText, yesButton, noButton)
   }

}