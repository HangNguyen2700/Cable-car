package view

import tools.aqua.bgw.core.BoardGameApplication
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual

class CCApplication : BoardGameApplication("Carbel Car Game") {

    private val creditsScene = CreditsScene()
    private val gameOverScene = GameOverScene()
    private val gameScene = GameScene()
    private val lobbyScene = LobbyScene()

    private val mainMenuScene = MainMenuScene().apply {
        backToTitleSceneButton.onMouseClicked = {
            hideMenuScene(3000)
        }
    }

    private val notificationGameScene = NotificationGameScene()
    private val quickMenuGameScene = QuickMenuGameScene()

    private val titleScene = TitleScene().apply {
        toMenuButton.onKeyPressed = {
            showMenuScene(mainMenuScene,3000)
        }
        toMenuButton.onMouseClicked = {
            showMenuScene(mainMenuScene,3000)
        }
    }

    init {
        this.showGameScene(titleScene)
        //icon = ImageVisual("")
    }

}

