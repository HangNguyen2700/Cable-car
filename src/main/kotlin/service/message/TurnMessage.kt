package service.message

import tools.aqua.bgw.net.common.GameAction
import tools.aqua.bgw.net.common.annotations.GameActionClass
@GameActionClass
data class TurnMessage(
    val posX: Int,
    val posY: Int,
    val fromSupply: Boolean,
    val rotation: Int
): GameAction()