package view

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.core.MenuScene

class LobbyScene : MenuScene(1920,1080) {

    private val test = Button(width = 300, height = 100,

    )

    init {
        addComponents(test)
        opacity = 0.0
    }
}