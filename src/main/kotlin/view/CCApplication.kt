package view

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.BoardGameApplication
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.CompoundVisual
import tools.aqua.bgw.visual.ImageVisual
import tools.aqua.bgw.visual.TextVisual
import java.awt.Color

/**
 * Main BoardGameApplication. contains all scenes and manages scene traversing and audio playback & toggle
 *
 * [creditsScene], [gameOverScene], [gameScene], [lobbyScene], [mainMenuScene],
 * [notificationGameScene], [quickMenuGameScene], [titleScene]: Menu & GameScenes
 * [musicEnabled], [soundEnabled]: global flag for music/sound
 * [musicButtons], [soundButtons]: all Buttons distributed in all Scenes for updating
 * [nameEmptyCheck] writes the Host name in the Lobby Scene when not empty
 * */

class CCApplication : BoardGameApplication("Carbel Car Game") {

    private val creditsScene = CreditsScene()

    private val gameOverScene = GameOverScene().apply {
        quitButton.onMouseClicked = { exit() }
    }

    private val gameScene = GameScene()

    private val lobbyScene = LobbyScene().apply {
        quitButton.onMouseClicked = { exit() }
        soundToggleButton.onMouseClicked = { toggleSound() }
        musicToggleButton.onMouseClicked = { toggleMusic() }
        backToMainMenuSceneButton.onMouseClicked = {
            hideMenuScene(3000)
            showMenuScene(mainMenuScene, 3000)
        }
    }

    private val mainMenuScene = MainMenuScene().apply {
        backToTitleSceneButton.onMouseClicked = { hideMenuScene(3000) }
        quitButton.onMouseClicked = { exit() }
        soundToggleButton.onMouseClicked = { toggleSound() }
        musicToggleButton.onMouseClicked = { toggleMusic() }
        joinButton.onMouseClicked = { nameEmptyCheck() }
        hostButton.onMouseClicked = { nameEmptyCheck() }
        hotseatButton.onMouseClicked = { nameEmptyCheck() }
        creditsButton.onMouseClicked = { hideMenuScene(3000); showGameScene(creditsScene)}
    }

    private val notificationGameScene = NotificationGameScene()

    private val quickMenuGameScene = QuickMenuGameScene().apply {
        /*quitButton.onMouseClicked = { exit() }*/
    }

    private val titleScene = TitleScene().apply {
        toMenuButton.onKeyPressed = { showMenuScene(mainMenuScene, 3000) }
        toMenuButton.onMouseClicked = { showMenuScene(mainMenuScene, 3000) }
    }

    private var musicEnabled = true
    private var soundEnabled = true

    private val musicButtons = listOf<Button>(mainMenuScene.musicToggleButton, lobbyScene.musicToggleButton)
    private val soundButtons = listOf<Button>(mainMenuScene.soundToggleButton, lobbyScene.soundToggleButton)

    init {
        this.showGameScene(titleScene)
        icon = ImageVisual("icon.png")
    }

    /**
     * checks if name is input in mainMenuScene and passes it to the first entry in player table in lobbyScene
     * for scene transition
     */

    private fun nameEmptyCheck() {
        if (mainMenuScene.nameField.text != "") {
            hideMenuScene(3000)
            lobbyScene.playerBoxLabel[0].visual = CompoundVisual(
                ColorVisual(63, 255, 63).apply { transparency = 0.3 },
                TextVisual(font = Font(size = 60, color = Color.BLACK, family = "Calibri"),
                    text = mainMenuScene.nameField.text,
                    alignment = Alignment.CENTER_LEFT, offsetX = 20))
            showMenuScene(lobbyScene, 3000)
        } else
            mainMenuScene.nameErrorDisplay()
    }

    /**
     * updates all Buttons in all Scenes with updated visual
     */

    private fun toggleMusic() {
        musicEnabled = !musicEnabled
        for (button in musicButtons) {
            if (!musicEnabled) { button.visual =
                    CompoundVisual(
                        ColorVisual.WHITE.apply { transparency = 0.3 },
                        TextVisual(font = Font(size = 60, color = Color.GREEN, family = "Calibri"), text = "Music"))}
            else { button.visual =
                    CompoundVisual(
                        ColorVisual.WHITE.apply { transparency = 0.3 },
                        TextVisual(font = Font(size = 60, color = Color.RED, family = "Calibri"), text = "Music"))}}}

    /**
     * analog to toggleMusic()
     */
    private fun toggleSound() {
        soundEnabled = !soundEnabled
        for (button in soundButtons) {
            if (!soundEnabled) { button.visual =
                    CompoundVisual(
                        ColorVisual.WHITE.apply { transparency = 0.3 },
                        TextVisual(font = Font(size = 60, color = Color.GREEN, family = "Calibri"), text = "Sound"))}
            else { button.visual =
                    CompoundVisual(
                        ColorVisual.WHITE.apply { transparency = 0.3 },
                        TextVisual(font = Font(size = 60, color = Color.RED, family = "Calibri"), text = "Sound"))}}}

}

