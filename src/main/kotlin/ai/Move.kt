package ai

/**
 * Data class containing parameters for an AI move in the game.
 */
data class Move(val shouldDrawFromStack: Boolean, val rotationsNo: Int, val posX: Int, val posY: Int)