package view

import tools.aqua.bgw.animation.DelayAnimation
import tools.aqua.bgw.animation.SequentialAnimation
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.CompoundVisual
import tools.aqua.bgw.visual.ImageVisual
import tools.aqua.bgw.visual.TextVisual
import java.awt.Color

class TitleScene : BoardGameScene(1920, 1080) {

    private val gameLabel = Label(width = 1920, height = 1080, posX = 0, posY = -100,
        font = Font(size = 400, color = Color.PINK, family = "Calibri"),
        text = "Carbel Car",
    )

    private val userPrompt = Button(width = 600, height = 100, posX = 660, posY = 900,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri", fontStyle = Font.FontStyle.ITALIC),
                text = "press any key..."
            )
        )

    )

    private var test = 0

    init {
        background = ColorVisual(108, 168, 59)
        addComponents(gameLabel, userPrompt)
    }

}