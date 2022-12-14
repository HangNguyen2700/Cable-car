package service

import entity.Game

class RootService {

    var currentGame: Game? = null

    var gameService: GameService = GameService(this)
    var playerActionService: PlayerActionService = PlayerActionService(this)
}