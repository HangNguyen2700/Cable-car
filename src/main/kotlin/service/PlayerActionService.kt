package service

import entity.Player

class PlayerActionService(private val rootService: RootService) : AbstractRefreshingService() {

    fun placeTile(player: Player, fromHand: Boolean, posX: Int, posY: Int) {

    }

    fun rotate(tile: Int) {

    }

    fun isPositionLegal(posX: Int, posY: Int) {

    }

    fun buildPaths(player: Player) {

    }

}