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

class HostLobbyScene: MenuScene(1920, 1080) {

    val hostGameLabel = Label(width = 300, height = 100, posX = 617, posY = 141,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.1 },
            TextVisual(font = Font(size = 60, color = Color.BLUE, family = "Calibri"), text = "Host Game")
        )
    )
    //Music / sound
    val musicToggleButton = Button(
        width = 140, height = 140, posX = 1620, posY = 880,
        visual = ImageVisual("music_enabled.png")
    )

    val soundToggleButton = Button(
        width = 140, height = 140, posX = 1320, posY = 880,
        visual = ImageVisual("sound_enabled.png")
    )

   //KI Turnier
    val allowKITurnier = Button(width = 600, height = 100, posX = 620, posY = 244,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(font = Font(size = 60, color = Color.RED, family = "Calibri"), text = "KI Turnier Mode"))
    ).apply { onMouseClicked = { allowKITurnierCheckbox.isChecked = !allowKITurnierCheckbox.isChecked } }

    val allowKITurnierCheckbox = CheckBox(width = 115, height = 70, posX = 976, posY =  242)

    //Shuffle Player
    val allowShufflePlayerOrder = Button(width = 600, height = 100, posX = 620, posY = 330,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(font = Font(size = 60, color = Color.RED, family = "Calibri"), text = "Shuffle Player Order"))
    ).apply { onMouseClicked = { allowShufflePlayerOrderCheckbox.isChecked = !allowShufflePlayerOrderCheckbox.isChecked } }

    val allowShufflePlayerOrderCheckbox = CheckBox(width = 115, height = 70, posX = 976, posY =  328)

    //Tile Rotation

    val allowTileRotationButton = Button(width = 600, height = 100, posX = 620, posY = 419,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(font = Font(size = 60, color = Color.RED, family = "Calibri"), text = "Tile Rotation"))
    ).apply { onMouseClicked = { allowTileRotationCheckbox.isChecked = !allowTileRotationCheckbox.isChecked } }

    val allowTileRotationCheckbox = CheckBox(width = 115, height = 70,posX=976,posY= 417)

    // Secret
    val secretLabel = Label(width = 300, height = 100, posX = 620, posY = 515,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.1 },
            TextVisual(font = Font(size = 60, color = Color.BLUE, family = "Calibri"), text = "Secret")
        )
    )
    val secretTextField =
        TextField(width = 611, height = 70, posX = 980, posY = 508, prompt = "",
            font = Font(size = 40, family = "Calibri"))


    //Session Id
    val sessionIdLabel = Label(width = 300, height = 100, posX = 620, posY = 613,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.1 },
            TextVisual(font = Font(size = 60, color = Color.BLUE, family = "Calibri"), text = "Session Id")
        )
    )

    val sessionIdTextField =
        TextField(width = 544, height = 70, posX = 976, posY = 619, prompt = "",
            font = Font(size = 40, family = "Calibri"))

    init{
        addComponents(
            soundToggleButton, musicToggleButton,
            hostGameLabel,allowKITurnier,allowKITurnierCheckbox,
            allowShufflePlayerOrder,allowShufflePlayerOrderCheckbox,
            allowTileRotationButton,allowTileRotationCheckbox,
            secretLabel,secretTextField,sessionIdLabel,secretTextField
        )
        background = ColorVisual(175, 238, 238)
        opacity = 0.4
    }




}