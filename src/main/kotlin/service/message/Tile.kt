package service.message

import service.message.ConnectionInfo

data class Tile(
    val id: Int,
    val connections: List<ConnectionInfo>
)