package service.message

import tools.aqua.bgw.net.common.GameAction
import tools.aqua.bgw.net.common.annotations.GameActionClass

@GameActionClass
data class GameInitMessage(
    val rotationAllowed: Boolean,
    val players: List<PlayerInfo>
): GameAction()