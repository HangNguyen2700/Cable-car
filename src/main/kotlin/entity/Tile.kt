package entity

/**
 * The Tile class represents a tile on the game board, including information about the tile's ports, rotation, and position.
 *
 * @property ports a mutable list of pairs of integers representing the tile's ports (x,y coordinates)
 * @property rotationDegree an integer representing the tile's rotation in degrees
 * @property posX an integer representing the tile's x position on the game board
 * @property posY an integer representing the tile's y position on the game board
 */
data class Tile(val ports: MutableList<Pair<Int, Int>>) {
    var rotationDegree = 0
    var posX = 0
    var posY = 0
}
