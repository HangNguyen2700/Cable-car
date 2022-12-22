package view

import tools.aqua.bgw.components.container.CardStack
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.CompoundVisual
import tools.aqua.bgw.visual.TextVisual
import java.awt.Color

/**
 * Custom [CardStack] for [CardView]s with a fixed width of 130px and a fixed height of 200px.
 * It is used for all game stacks on the table.
 *
 * Automatically has a [CompoundVisual] that combines a white semi-transparent [ColorVisual] with
 * a [TextVisual] showing a label.
 *
 * @param label the label for this stack view; defaults to empty string.
 */
class LabeledStackView(posX: Number = 0, posY: Number = 0, label: String = "") :
    CardStack<CardView>(height = 100, width = 100, posX = posX, posY = posY) {

    init {
        visual = CompoundVisual(
            ColorVisual(Color(186,136,133,255)),
            TextVisual(label)
        )
    }
}