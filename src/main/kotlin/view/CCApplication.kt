package view

import com.soywiz.korau.sound.SoundChannel
import com.soywiz.korau.sound.await
import com.soywiz.korau.sound.infinitePlaybackTimes
import com.soywiz.korau.sound.readMusic
import com.soywiz.korio.async.async
import com.soywiz.korio.file.std.resourcesVfs
import kotlinx.coroutines.GlobalScope
import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.BoardGameApplication
import tools.aqua.bgw.core.MenuScene
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

    val rootService = RootService()

    private val creditsScene = CreditsScene().apply {
        backToTitleSceneButton.onMouseClicked = {
            timesClicked++
            when (timesClicked) {
                1 -> { backToTitleSceneButton.apply { posX = 1400.0; posY = 300.0 }; playNopeSound() }
                2 -> { backToTitleSceneButton.apply { posX = 700.0; posY = 200.0 }; playNopeSound() }
                3 -> { backToTitleSceneButton.apply { posX = 1500.0; posY = 500.0 }; playNopeSound() }
                4 -> { backToTitleSceneButton.apply { posX = 200.0; posY = 800.0 }; playNopeSound() }
                5 -> { backToTitleSceneButton.apply { posX = 1400.0; posY = 200.0 }; playNopeSound() }
                6 -> {
                    backToTitleSceneButton.apply { posX = 100.0; posY = 930.0 }
                    timesClicked = 0
                    playTitleMusic()
                    showGameScene(titleScene)
                    titleScene.gameLabel.opacity = 1.0
                    titleScene.trigger.opacity = 0.0
                    repaint()
                }
            }
        }
        soundToggleButton.onMouseClicked = { toggleSound() }
        musicToggleButton.onMouseClicked = { toggleMusic() }
    }

    private val gameOverScene = GameOverScene().apply {
        soundToggleButton.onMouseClicked = { toggleSound() }
        musicToggleButton.onMouseClicked = { toggleMusic() }
        quitButton.onMouseClicked = {
            hideMenuScene(3000)
            showMenuScene(confirmQuitMenuScene)
        }
    }

    private val gameScene = GameScene(rootService).apply {
        quickMenuButton.onMouseClicked = {
            hideMenuScene(3000)
            showAndStoreMenuScene(quickMenuGameScene,3000)
        }
    }

    private val hostLobbyScene = HostLobbyScene().apply {
        quitButton.onMouseClicked = {
            hideMenuScene(3000)
            showMenuScene(confirmQuitMenuScene)
        }
        soundToggleButton.onMouseClicked = { toggleSound() }
        musicToggleButton.onMouseClicked = { toggleMusic() }
        backToMainMenuSceneButton.onMouseClicked = {
            hideMenuScene(3000)
            showAndStoreMenuScene(mainMenuScene, 3000)
        }
        hostGameButton.onMouseClicked = {
            if(secretTextField.text != "" && sessionIdTextField.text != "") {
                hideMenuScene(3000)
                //switch to gameScene from another host
            } else { playNopeSound() }
        }
    }

    private val lobbyScene = LobbyScene().apply {
        quitButton.onMouseClicked = {
            hideMenuScene(3000)
            showMenuScene(confirmQuitMenuScene)
        }
        soundToggleButton.onMouseClicked = { toggleSound() }
        musicToggleButton.onMouseClicked = { toggleMusic() }
        backToMainMenuSceneButton.onMouseClicked = {
            hideMenuScene(3000)
            showAndStoreMenuScene(mainMenuScene, 3000)
        }
        startGameButton.onMouseClicked = {
            if (shuffleTurnOrderCheckbox.isChecked) {
                this@CCApplication.rootService.gameService.startNewGame(
                    listOfNotNull(
                        mainMenuScene.nameField.text,
                        this.nameFields[0].text,
                        if (this.nameFields[1].text != "") this.nameFields[1].text else null,
                        if (this.nameFields[2].text != "") this.nameFields[2].text else null,
                        if (this.nameFields[3].text != "") this.nameFields[3].text else null,
                        if (this.nameFields[4].text != "") this.nameFields[4].text else null,
                    ).shuffled(),
                    isLocalOnlyGame = true, isHostedGame = false,
                    rotationAllowed = this.allowTileRotationCheckbox.isChecked
                )
            } else {
                this@CCApplication.rootService.gameService.startNewGame(
                    listOfNotNull(
                        mainMenuScene.nameField.text,
                        this.nameFields[0].text,
                        if (this.nameFields[1].text != "") this.nameFields[1].text else null,
                        if (this.nameFields[2].text != "") this.nameFields[2].text else null,
                        if (this.nameFields[3].text != "") this.nameFields[3].text else null,
                        if (this.nameFields[4].text != "") this.nameFields[4].text else null,
                    ),
                    isLocalOnlyGame = true, isHostedGame = false,
                    rotationAllowed = this.allowTileRotationCheckbox.isChecked
                )
            }
            hideMenuScene(3000)
            showGameScene(gameScene)
        }
    }

    private val mainMenuScene = MainMenuScene().apply {
        backToTitleSceneButton.onMouseClicked = { hideMenuScene(3000) }
        quitButton.onMouseClicked = {
            hideMenuScene(3000)
            showMenuScene(confirmQuitMenuScene)
        }
        soundToggleButton.onMouseClicked = { toggleSound() }
        musicToggleButton.onMouseClicked = { toggleMusic() }
        joinButton.onMouseClicked = { nameEmptyCheck(1) }
        hostButton.onMouseClicked = { nameEmptyCheck(2) }
        hotseatButton.onMouseClicked = { nameEmptyCheck(3) }
        creditsButton.onMouseClicked = {
            hideMenuScene(3000)
            explicitlyShowCreditsScene()
            if (musicEnabled) playCreditsMusic()
        }
        debugGameSceneButton.onMouseClicked = { hideMenuScene(3000); showGameScene(gameScene) }
        debugGameEndSceneButton.onMouseClicked={hideMenuScene(3000); showGameScene(gameOverScene)}
    }

    private val networkJoinScene = NetworkJoinScene().apply {
        quitButton.onMouseClicked = {
            hideMenuScene(3000)
            showMenuScene(confirmQuitMenuScene)
        }
        soundToggleButton.onMouseClicked = { toggleSound() }
        musicToggleButton.onMouseClicked = { toggleMusic() }
        backToMainMenuSceneButton.onMouseClicked = {
            hideMenuScene(3000)
            showAndStoreMenuScene(mainMenuScene, 3000)
        }
        joinGameButton.onMouseClicked = {
            if(secretTextField.text != "" && sessionIDTextField.text != "") {
                hideMenuScene(3000)
                //switch to gameScene from another host
            } else { playNopeSound() }
        }
    }

    private val notificationGameScene = NotificationGameScene()

    private val confirmQuitMenuScene = ConfirmQuitMenuScene().apply {
        yesButton.onMouseClicked = { exit() }
        noButton.onMouseClicked = {
            hideMenuScene()
            showAndStoreMenuScene(activeMenuScene!!,3000)
        }
    }

    private val quickMenuGameScene = QuickMenuGameScene().apply {
        soundToggleButton.onMouseClicked = { toggleSound() }
        musicToggleButton.onMouseClicked = { toggleMusic() }
        exitQuitMenuSceneButton.onMouseClicked={ hideMenuScene(3000) }
        quitButton.onMouseClicked = {
            hideMenuScene(3000)
            showMenuScene(confirmQuitMenuScene)
        }
    }

    private val titleScene = TitleScene(this).apply {
        toMenuButton.onKeyPressed = { showAndStoreMenuScene(mainMenuScene, 3000) }
        toMenuButton.onMouseClicked = { showAndStoreMenuScene(mainMenuScene, 3000) }
        trigger.onMouseEntered = { playTitleMusic(); fadeIn() }
    }

    private var activeMenuScene : MenuScene? = null

    private var musicChannel : SoundChannel? = null
    private var soundChannel : SoundChannel? = null

    private var musicEnabled = true
    private var soundEnabled = true

    private val musicButtons = listOf(mainMenuScene.musicToggleButton, lobbyScene.musicToggleButton,
        quickMenuGameScene.musicToggleButton,creditsScene.musicToggleButton,gameOverScene.musicToggleButton,
        networkJoinScene.musicToggleButton,hostLobbyScene.musicToggleButton)
    private val soundButtons = listOf(mainMenuScene.soundToggleButton, lobbyScene.soundToggleButton,
        quickMenuGameScene.soundToggleButton,creditsScene.soundToggleButton,gameOverScene.soundToggleButton,
        networkJoinScene.soundToggleButton,hostLobbyScene.soundToggleButton)

    private val musicButtonEnableImage = ImageVisual("music_enabled.png")
    private val musicButtonDisableImage = ImageVisual("music_disabled.png")
    private val soundButtonEnableImage = ImageVisual("sound_enabled.png")
    private val soundButtonDisableImage = ImageVisual("sound_disabled.png")

    init {
        this.showGameScene(titleScene)
        //isFullScreen = true
        icon = ImageVisual("icon.png")
//        this.showGameScene(gameScene)
    }

    /**
     * checks if name is input in mainMenuScene and passes it to the first entry in player table in lobbyScene
     * for scene transition
     */

    private fun nameEmptyCheck(case : Int) {
        if (mainMenuScene.nameField.text != "") {
            if (case == 1) { //join network
                hideMenuScene(3000)
                showAndStoreMenuScene(networkJoinScene,3000)
            } else if(case == 2) {  //host network
                hideMenuScene(3000)
                showAndStoreMenuScene(hostLobbyScene, 3000)
            } else {    //hotseat
                hideMenuScene(3000)
                lobbyScene.playerBoxLabel[0].visual = CompoundVisual(
                    ColorVisual(63, 255, 63).apply { transparency = 0.3 },
                    TextVisual(font = Font(size = 60, color = Color.BLACK, family = "Calibri"),
                        text = mainMenuScene.nameField.text,
                        alignment = Alignment.CENTER_LEFT, offsetX = 20))
                showAndStoreMenuScene(lobbyScene, 3000)
            }
        } else {
            mainMenuScene.nameErrorDisplay()
            playNopeSound()
        }

    }

    /**
     * updates all Buttons in all Scenes with updated visual
     */

    private fun toggleMusic() {
        musicEnabled = !musicEnabled
        for (button in musicButtons) {
            if (!musicEnabled) {
                button.visual = musicButtonDisableImage
                if (musicChannel != null) musicChannel!!.volume = 0.0
            } else {
                button.visual = musicButtonEnableImage
                if (musicChannel != null) musicChannel!!.volume = 1.0
            }
        }
    }

    /**
     * analog to toggleMusic()
     */

    private fun toggleSound() {
        soundEnabled = !soundEnabled
        for (button in soundButtons) {
            if (!soundEnabled) {
                button.visual = soundButtonDisableImage
                if (soundChannel != null) soundChannel!!.volume = 0.0
            } else {
                button.visual = soundButtonEnableImage
                if (soundChannel != null) soundChannel!!.volume = 1.0
            }
        }
    }

    /**
     * playback of music in credits scene via KorAU audio library
     */

    private fun playTitleMusic() {
        if (musicChannel != null) { musicChannel!!.stop() }
        if (musicEnabled) {
            GlobalScope.async {
                val music = resourcesVfs["title_music.wav"].readMusic()
                musicChannel = music.play(infinitePlaybackTimes)
                musicChannel!!.await()
            }
        }
    }

    /**
     * playback of music in credits scene via KorAU audio library
     */

    private fun playCreditsMusic() {
        if (musicChannel != null) { musicChannel!!.stop() }
        if (musicEnabled) {
            GlobalScope.async {
                val music = resourcesVfs["credits_music.wav"].readMusic()
                musicChannel = music.play(infinitePlaybackTimes)
                musicChannel!!.await()
            }
        }
    }

    /**
     * playback of sound via KorAU audio library
     */

    private fun playNopeSound() {
        if (soundChannel != null) { soundChannel!!.stop() }
        if(soundEnabled) {
            GlobalScope.async {
                val sound = resourcesVfs["nope_sound_effect.wav"].readMusic()
                soundChannel = sound.play()
                soundChannel!!.await()
            }
        }
    }

    /**
     * workaround for kotlin compiler warning
     */

    private fun explicitlyShowCreditsScene() { showGameScene(creditsScene); creditsScene.trigger() }

    /**
     * when a MenuScene is called it needs to be saved for confirmQuit MenuScene
     * to revert to formerly displayed MenuScene
     */

    private fun showAndStoreMenuScene(menuScene: MenuScene, fadeTime : Int) {
        activeMenuScene = menuScene
        showMenuScene(menuScene,fadeTime)
    }

}



