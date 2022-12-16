package service

import entity.Player
import service.RootService
import service.message.*

class NetworkService(var rootService: RootService): AbstractRefreshingService() {
    var connectionState: ConnectionState = ConnectionState.DISCONNECTED
    val SERVER_ADDRESS = "sopra.cs.tu-dortmund.de:80/bgw-net/connect"
    val GAME_ID = "CableCar"

    var client: NetworkClient? = null

    var joinedPlayers = mutableListOf<String>()
    fun joinGame(secret: String, name:String, sessionID: String) {
        if(connect(secret, name)) {
            client?.joinGame(sessionID, "Hallo von Gruppe 10.")
            updateConnectionState(ConnectionState.WAITING_FOR_JOIN_CONFIRMATION)
        }
    }

    fun hostGame(secret: String, name: String, sessionID: String) {
        connect(secret, name)
        if(connect(secret, name)) {
            client?.createGame(GAME_ID, sessionID, "Hallo von Gruppe 10")
            updateConnectionState(ConnectionState.WAITING_FOR_HOST_CONFIRMATION)
        }
    }

    private fun connect(secret: String, name: String): Boolean {
        require(connectionState == ConnectionState.DISCONNECTED && client == null)
        { "already connected to another game" }

        require(secret.isNotBlank()) { "server secret must be given" }
        require(name.isNotBlank()) { "player name must be given" }

        val newClient =
            NetworkClient(
                playerName = name,
                host = SERVER_ADDRESS,
                secret = secret,
                networkService = this
            )

        return if (newClient.connect()) {
            this.client = newClient
            updateConnectionState(ConnectionState.CONNECTED)
            true
        } else {
            false
        }
    }

    fun sendGameInitMessage(message: GameInitMessage) {
        require(connectionState == ConnectionState.READY_FOR_GAME) {"Not enough Players have joined yet."}
        client?.sendGameActionMessage(message)
        updateConnectionState(ConnectionState.GAME_INITIALIZED)
    }

    fun sendTurnMessage(message: TurnMessage) {

    }

    fun startNewJoinedGame(message: GameInitMessage) {
        val players = mutableListOf<Player>()
        for (player in message.players) {
            players.add(Player(player.name, mutableListOf()))
        }
        rootService.gameService.startNewGame(players)
        updateConnectionState(ConnectionState.GAME_INITIALIZED)
    }

    fun startNewHostedGame(localPlayers: List<Player>, rotationAllowed: Boolean) {
        // creating GameInitMessage
        var players = mutableListOf<PlayerInfo>()
        for (lp in localPlayers) {
            players.add(PlayerInfo(lp.name, PlayerType.HUMAN))
        }
        var initMessage = GameInitMessage(rotationAllowed, players, listOf<Tile>())

        // TODO adding Tiles from Entity to GameInitMessage

        // sending GameInitMessage
        sendGameInitMessage(initMessage)
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