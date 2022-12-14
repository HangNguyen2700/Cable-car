package service.message

data class GameStateVerificationInfo(
    val placedTiles: List<TileInfo>,
    val supply: List<Int>,
    val playerScores: List<Int>
)