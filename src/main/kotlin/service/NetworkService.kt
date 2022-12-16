package service

import entity.Player
import service.message.*

class NetworkService(var rootService: RootService): AbstractRefreshingService() {
    var connectionState: ConnectionState = ConnectionState.DISCONNECTED
    private val SERVER_ADDRESS = "sopra.cs.tu-dortmund.de:80/bgw-net/connect"
    private val GAME_ID = "CableCar"

    private var client: NetworkClient? = null


    fun hostGame(secret: String, playerName: String, sessionID: String) {
        if(connect(secret, playerName)) {
            client?.createGame(GAME_ID, sessionID, "Hallo von Gruppe 10")
            updateConnectionState(ConnectionState.WAITING_FOR_HOST_CONFIRMATION)
        }
    }

    var joinedPlayers = mutableListOf<String>()
    fun joinGame(secret: String, name:String, sessionID: String) {
        if(connect(secret, name)) {
            client?.joinGame(sessionID, "Hallo von Gruppe 10.")
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

    fun sendGameInitMessage(message: GameInitMessage?): Boolean {
        if (connectionState != ConnectionState.READY_FOR_GAME) {
            if (connectionState == ConnectionState.WAITING_FOR_PLAYERS) {
                println("Not enough or too many Players have joined the Game. Current Amount: ${joinedPlayers.size}")
            }
            return false
        }
        if (message != null) {
            client?.sendGameActionMessage(message)
        } else {
            // Create GameInitMessage
            val playerInfoList = mutableListOf<PlayerInfo>()
            for (player in joinedPlayers) {
                playerInfoList.add(PlayerInfo(player, PlayerType.HUMAN))
            }

            // TODO get TileSupply list from Entity
            val gameInitMessage = GameInitMessage(
                rotationAllowed = true,
                players = playerInfoList,
                tileSupply = listOf()
            )
            client?.sendGameActionMessage(gameInitMessage)
        }
        updateConnectionState(ConnectionState.GAME_INITIALIZED)
        return true
    }

    fun sendTurnMessage(message: TurnMessage) {
        // TODO
    }

    fun startNewJoinedGame(message: GameInitMessage) {
        // TODO
    }

    fun startNewHostedGame(localPlayers: List<Player>, rotationAllowed: Boolean) {
        // TODO
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