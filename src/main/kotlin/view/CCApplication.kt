package view

import tools.aqua.bgw.core.BoardGameApplication

class CCApplication : BoardGameApplication("Carbel Car Game") {

    private val creditsScene = CreditsScene()
    private val gameOverScene = GameOverScene()
    private val gameScene = GameScene()
    private val lobbyScene = LobbyScene()
    private val mainMenuScene = MainMenuScene()
    private val notificationGameScene = NotificationGameScene()
    private val quickMenuGameScene = QuickMenuGameScene()
    private val titleScene = TitleScene()

    init {
        this.showGameScene(titleScene)
        this.showMenuScene(mainMenuScene)
    }

}

