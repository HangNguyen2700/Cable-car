package service

import service.message.GameInitMessage
import service.message.TurnMessage
import tools.aqua.bgw.core.BoardGameApplication
import tools.aqua.bgw.net.client.BoardGameClient
import tools.aqua.bgw.net.client.NetworkLogging
import tools.aqua.bgw.net.common.annotations.GameActionReceiver
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
) : BoardGameClient(playerName, host, secret, networkLoggingBehavior = NetworkLogging.VERBOSE) {

    var sessionID: String? = null

    override fun onJoinGameResponse(response: JoinGameResponse) {
        if (networkService.connectionState == ConnectionState.WAITING_FOR_JOIN_CONFIRMATION) {
            println("received unexpected join confirmation")
        }

        when (response.status) {
            JoinGameResponseStatus.SUCCESS -> {
                networkService.updateConnectionState(ConnectionState.WAITING_FOR_INIT)
                println("Successfully joined game.")
                println("The host's greeting is \"${response.message}\".")
            }
            JoinGameResponseStatus.ALREADY_ASSOCIATED_WITH_GAME ->
                error("You are already in a game.")

            JoinGameResponseStatus.INVALID_SESSION_ID ->
                error("SessionID invalid.")

            JoinGameResponseStatus.PLAYER_NAME_ALREADY_TAKEN ->
                error("Player name already exists.")

            JoinGameResponseStatus.SERVER_ERROR ->
                error(response)
            else -> disconnect()
        }
    }

    override fun onCreateGameResponse(response: CreateGameResponse) {
        if (networkService.connectionState == ConnectionState.WAITING_FOR_HOST_CONFIRMATION) {
            println("unexpected host confirmation")
        }

        when (response.status) {
            CreateGameResponseStatus.SUCCESS -> {
                println("Successfully created game.")
                networkService.updateConnectionState(ConnectionState.WAITING_FOR_PLAYERS)
            }

            CreateGameResponseStatus.ALREADY_ASSOCIATED_WITH_GAME ->
                error("Leave current game first.")

            CreateGameResponseStatus.SESSION_WITH_ID_ALREADY_EXISTS ->
                error("Session id already exists.")

            CreateGameResponseStatus.GAME_ID_DOES_NOT_EXIST ->
                error("GameID does not exist.")

            CreateGameResponseStatus.SERVER_ERROR ->
                error(response)
            else -> disconnect()
        }
    }

    override fun onPlayerJoined(notification: PlayerJoinedNotification) {
        if(networkService.connectionState != ConnectionState.WAITING_FOR_INIT) {
            if (networkService.connectionState == ConnectionState.WAITING_FOR_PLAYERS) {
                println("unexpected player joined notification")
                return
            }

            println("${notification.sender} joined the game.")
            println("${notification.sender}: ${notification.message}")

            networkService.joinedPlayers.add(notification.sender)

            if (networkService.joinedPlayers.size > 6) {
                networkService.updateConnectionState(ConnectionState.WAITING_FOR_PLAYERS)
                println("Too many players are connected to the game. Currently Connected Players: ${networkService.joinedPlayers.size}")
                return
            }

            networkService.updateConnectionState(ConnectionState.READY_FOR_GAME)
        } else {
            println("A Player joined the remotely hosted game")
        }
    }

    override fun onPlayerLeft(notification: PlayerLeftNotification) {
        if (networkService.connectionState != ConnectionState.WAITING_FOR_INIT) {
            if (networkService.connectionState == ConnectionState.READY_FOR_GAME) {
                networkService.joinedPlayers.remove(notification.sender)
                if (networkService.joinedPlayers.size < 2) {
                    networkService.updateConnectionState(ConnectionState.WAITING_FOR_PLAYERS)
                }
                println("Player: ${notification.sender} has left the game. Current Players: ${networkService.joinedPlayers.size}")
            }
            // TODO inGame boolean, for stopping game if player disconnects during a game
            if(false) {
                println("a player has left the game. disconnecting.")
                disconnect()
            }
        }
    }

    @GameActionReceiver
    fun onInitReceived(message: GameInitMessage, sender: String) {
        BoardGameApplication.runOnGUIThread {
            networkService.startNewJoinedGame(message)
        }
    }

    @GameActionReceiver
    fun onTurnMessageReceived(message: TurnMessage, sender: String) {
        BoardGameApplication.runOnGUIThread {
            //execute turn
        }
    }


}