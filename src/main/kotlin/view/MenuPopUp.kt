package view

import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import java.awt.Color

class MenuPopUp: MenuScene(1020,580)  {

    val popUpText = Label(
        width = 750, height = 130, posX = 162, posY = 173,
        text = "Are you sure you want to quit?",
        font = Font(size = 48, color = Color.WHITE, family = "Calibri", fontStyle = Font.FontStyle.ITALIC)
    )

    val yes=Label(
        width = 750, height = 130, posX = 212, posY = 325,
        text = "Yes",
        font = Font(size = 30, color = Color.WHITE, family = "Calibri", fontStyle = Font.FontStyle.ITALIC)
    )

    val No=Label(
        width = 750, height = 130, posX = 705, posY = 325,
        text = "No",
        font = Font(size = 30, color = Color.WHITE, family = "Calibri", fontStyle = Font.FontStyle.ITALIC)
    )
   init {
       addComponents(popUpText)
   }


}