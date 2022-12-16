package service

import service.message.GameInitMessage
import tools.aqua.bgw.core.BoardGameApplication
import tools.aqua.bgw.net.client.BoardGameClient
import tools.aqua.bgw.net.common.notification.PlayerJoinedNotification
import tools.aqua.bgw.net.common.notification.PlayerLeftNotification
import tools.aqua.bgw.net.common.response.CreateGameResponse
import tools.aqua.bgw.net.common.response.CreateGameResponseStatus
import tools.aqua.bgw.net.common.response.JoinGameResponse
import tools.aqua.bgw.net.common.response.JoinGameResponseStatus

class NetworkClient(playerName: String,
                    host: String,
                    secret: String,
                    var networkService: NetworkService,
) : BoardGameClient(playerName, host, secret) {

    var sessionID: String? = null

    override fun onJoinGameResponse(response: JoinGameResponse) {
        check(networkService.connectionState == ConnectionState.WAITING_FOR_JOIN_CONFIRMATION)
            {"unexpected join confirmation"}

        when (response.status) {
            JoinGameResponseStatus.SUCCESS -> {
                sessionID = response.sessionID
                networkService.updateConnectionState(ConnectionState.WAITING_FOR_INIT)
                println("successfully joined game: ${sessionID}, current players are: ${response.opponents}")
            }
            else -> disconnect()
        }

    }

    override fun onCreateGameResponse(response: CreateGameResponse) {
        check(networkService.connectionState == ConnectionState.WAITING_FOR_HOST_CONFIRMATION)
            {"unexpected host confirmation"}

        when (response.status) {
            CreateGameResponseStatus.SUCCESS -> {
                networkService.updateConnectionState(ConnectionState.WAITING_FOR_PLAYERS)
                sessionID = response.sessionID
                println("successfully created game: $sessionID")
            }
            else -> disconnect()
        }
    }

    override fun onPlayerJoined(notification: PlayerJoinedNotification) {
        check(networkService.connectionState == ConnectionState.WAITING_FOR_PLAYERS)
            {"unexpected player joined notification"}

        println("${notification.sender} joined the game.")
        println("${notification.sender}: ${notification.message}")
        networkService.joinedPlayers.add(notification.sender)

        networkService.updateConnectionState(ConnectionState.READY_FOR_GAME)
    }

    override fun onPlayerLeft(notification: PlayerLeftNotification) {
        if (networkService.joinedPlayers.size < 2) {
            networkService.updateConnectionState(ConnectionState.WAITING_FOR_PLAYERS)
        }
        // TODO inGame boolean, for stopping game if player disconnects during a game
        if(true) {
            println("a player has left the game. disconnecting.")
            disconnect()
        }
        println("a player has left the game. current player amount: ${networkService.joinedPlayers.size}")
    }

    fun onInitReceived(message: GameInitMessage, sender: String) {
        BoardGameApplication.runOnGUIThread {
            networkService.startNewJoinedGame(message)
        }
    }


}