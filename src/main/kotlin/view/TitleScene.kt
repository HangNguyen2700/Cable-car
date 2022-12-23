package view

import tools.aqua.bgw.animation.FadeAnimation
import tools.aqua.bgw.animation.SequentialAnimation
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.CompoundVisual
import tools.aqua.bgw.visual.TextVisual
import java.awt.Color

/**
 * Scene displayed as first in the game. nice animation to play in background of main menu/lobby Scene
 *
 * [trigger]: trigger to start fadeIn()
 * [gameLabel]: game Name
 * [pressAnyKeyLabel]: fade up and down Animation
 * [toMenuButton]: click anywhere or press any key to get to main menu
 */

class TitleScene : BoardGameScene(1920, 1080) {

    private val trigger = Button(width = 1920, height = 1080, posX = 0, posY = 0,
        visual = ColorVisual(0,0,0),
    ).apply { onMouseEntered = { println("Scene initialized"); fadeIn() }}

    private val gameLabel = Label(width = 1920, height = 1080, posX = 0, posY = -100,
        font = Font(size = 400, color = Color.PINK, family = "Calibri"),
        text = "Carbel Car",
    ).apply { opacity = 0.0 }

    private val pressAnyKeyLabel = Label(width = 600, height = 100, posX = 660, posY = 800,
        visual = CompoundVisual(
            ColorVisual.WHITE.apply { transparency = 0.3 },
            TextVisual(
                font = Font(size = 60, color = Color.RED, family = "Calibri", fontStyle = Font.FontStyle.ITALIC),
                text = "press any key..."
            ))).apply { opacity = 0.0 }

    val toMenuButton = Button(width = 1920, height = 1080).apply { opacity = 0.0 }

    init {
        background = ColorVisual(108, 168, 59)
        addComponents(trigger)
    }

    /**
     * fades from black and starts fade in of elements
     */

    private fun fadeIn() {
        playAnimation(
            FadeAnimation(trigger,1.0,0.0,1000).apply { onFinished = {
                removeComponents(trigger)
                addComponents(gameLabel, pressAnyKeyLabel, toMenuButton)
                playAnimation(FadeAnimation(gameLabel,0.0,1.0,1000))
                pressAnyKeyLabelFadeAnimation()
            }}
        )
    }

    /**
     * looping fade in and out animation for [pressAnyKeyLabel]
     */

    private fun pressAnyKeyLabelFadeAnimation() {
        playAnimation(
            SequentialAnimation(
                FadeAnimation(pressAnyKeyLabel, 0.0, 1.0,1000),
                FadeAnimation(pressAnyKeyLabel, 1.0, 0.0,1000)
            ).apply { onFinished = {pressAnyKeyLabelFadeAnimation()} }
        )
    }

}