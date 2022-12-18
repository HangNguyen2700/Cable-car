package service

import entity.Player
import service.message.*

class NetworkService(var rootService: RootService): AbstractRefreshingService() {
    var connectionState: ConnectionState = ConnectionState.DISCONNECTED
    private val SERVER_ADDRESS = "sopra.cs.tu-dortmund.de:80/bgw-net/connect"
    private val GAME_ID = "CableCar"

    private var client: NetworkClient? = null
    var playerName = ""


    fun hostGame(secret: String, playerName: String, sessionID: String) {
        if(connect(secret, playerName)) {
            client?.createGame(GAME_ID, sessionID, "Hallo von Gruppe 10")
            updateConnectionState(ConnectionState.WAITING_FOR_HOST_CONFIRMATION)
            this.playerName = playerName
        }
    }

    var joinedPlayers = mutableListOf<String>()
    fun joinGame(secret: String, name:String, sessionID: String) {
        if(connect(secret, name)) {
            client?.joinGame(sessionID, "Hallo von Gruppe 10.")
            this.playerName = name
        }
    }


    /**
     * Opens a connection to the Server via creating the client
     */
    private fun connect(secret: String, playerName: String): Boolean {
        require(connectionState == ConnectionState.DISCONNECTED && client == null)
        { "already connected to another game" }

        require(secret.isNotBlank()) { "server secret must be given" }
        require(playerName.isNotBlank()) { "player name must be given" }

        val newClient =
            NetworkClient(
                playerName = playerName,
                host = SERVER_ADDRESS,
                secret = secret,
                networkService = this
            )

        if(newClient.connect()) {
            this.client = newClient
            updateConnectionState(ConnectionState.CONNECTED)
            return true
        }
        return false
    }

    fun startNewHostedGame(hostPlayerName: String, rotationAllowed: Boolean) {
        // TODO set rotation allowed
        val playerInfoList = mutableListOf<PlayerInfo>()
        playerInfoList.add(PlayerInfo(hostPlayerName, PlayerType.HUMAN))
        for (player in joinedPlayers) {
            playerInfoList.add(PlayerInfo(player, PlayerType.HUMAN))
        }

        // player list for local game
        // TODO remove when local startNewGame() no longer takes List<Player> instead of List<String>
        val localPlayerList = mutableListOf<Player>()
        localPlayerList.add(Player(hostPlayerName, mutableListOf()))
        for (player in joinedPlayers) {
            localPlayerList.add(Player(player, mutableListOf()))
        }

        // start game locally
        rootService.gameService.startNewGame(localPlayerList)

        // TODO get TileSupply list from Entity
        val gameInitMessage = GameInitMessage(
            rotationAllowed = true,
            players = playerInfoList,
            tileSupply = listOf()
        )

        sendGameInitMessage(gameInitMessage)
    }

    fun startNewJoinedGame(message: GameInitMessage) {
        // TODO rotationAllowed boolean fehlt in enitity
        val playerList = mutableListOf<Player>()
        for (player in message.players) {
            playerList.add(Player(player.name, mutableListOf()))
        }

        rootService.gameService.startNewGame(playerList)

        val tileStack = IntArray(60)
        for (i in 0..message.tileSupply.size) {
            tileStack[i] = message.tileSupply[i].id
        }
        // TODO local tileStack in entity speichern
    }

    fun sendGameInitMessage(message: GameInitMessage) {
        // send GameInitMessage

        if (connectionState != ConnectionState.READY_FOR_GAME) {
            if (connectionState == ConnectionState.WAITING_FOR_PLAYERS) {
                println("Not enough or too many Players have joined the Game. Current Amount: ${joinedPlayers.size}")
            }
            return
        }
        client?.sendGameActionMessage(message)
        updateConnectionState(ConnectionState.GAME_INITIALIZED)
    }

    fun sendTurnMessage(message: TurnMessage) {
        // TODO sendTurnMessage
    }

    fun updateConnectionState(newState: ConnectionState) {
        connectionState = newState
    }

    fun disconnect() {
        client?.disconnect()
        println("disconnecting.")
        updateConnectionState(ConnectionState.DISCONNECTED)
    }
}