package view

import tools.aqua.bgw.core.BoardGameApplication

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
    }

}

