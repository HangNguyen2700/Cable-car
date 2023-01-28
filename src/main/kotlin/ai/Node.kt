package ai
import entity.Turn
import service.PlayerActionService

data class Node(val rs: service.RootService, val parent: Node?, val move: Move, val playerIndex: Int) {
    val children: MutableList<Node> = mutableListOf()
    val state: Turn =
        if (parent != null) AiActionService.doMove(parent.state, move, playerIndex)
        else Turn (rs.currentGame!!.currentTurn.gameField.copy(),
            rs.currentGame!!.currentTurn.players.toMutableList())

    var score = 0.0
    var winCount = 0.0
    var visitCount = 0.0

    fun getPossibleMoves(): MutableList<Move> {
        val moves: MutableList<Move> = mutableListOf()

        for (x in 1 until state.gameField.field.size - 1) {
            for (y in 1 until state.gameField.field[x].size - 1) {
                if (!AiActionService.isPositionLegal(state, x, y)) continue
                for (i in 0..3) {
                    moves.add(Move(false, i, x, y))
                    moves.add(Move(true, i, x, y))
                }
            }
        }
        return moves
    }

    fun setScore (prev: Turn) {
        score = 0.0
        var actComparator = 0
        var prevComparator = 0

        // -5 if another path was completed
        for (path in state.players[playerIndex].paths) {
            if (path.complete) actComparator += 5
        }
        for (path in prev.players[playerIndex].paths) {
            if (path.complete) prevComparator += 5
        }
        if (actComparator > prevComparator) score -= (actComparator - prevComparator)
        actComparator = 0
        prevComparator = 0

        // +5 if another rival's path was completed
        for (i in 0 until state.players.size) {
            if (i == playerIndex) continue
            for (path in state.players[i].paths) {
                if (path.complete) actComparator += 5
            }
        }
        for (i in 0 until prev.players.size) {
            if (i == playerIndex) continue
            for (path in prev.players[i].paths) {
                if (path.complete) prevComparator += 5
            }
        }
        if (actComparator > prevComparator) score += (actComparator - prevComparator)
        actComparator = 0
        prevComparator = 0

        // +1 if another train connected to power station
        for (path in state.players[playerIndex].paths) {
            if (path.tiles.isNotEmpty() &&
                PlayerActionService.isConnectedToPower(path.tiles.last().posX, path.tiles.last().posY, path.lastPort))
                actComparator++
        }
        for (path in prev.players[playerIndex].paths) {
            if (path.tiles.isNotEmpty() &&
                PlayerActionService.isConnectedToPower(path.tiles.last().posX, path.tiles.last().posY, path.lastPort))
                prevComparator++
        }
        if (actComparator > prevComparator) score += (actComparator - prevComparator)
        actComparator = 0
        prevComparator = 0

        // +1 if another path extended
        for (path in state.players[playerIndex].paths) {
            actComparator += path.tiles.size
        }
        for (path in prev.players[playerIndex].paths) {
            prevComparator += path.tiles.size
        }
        if (actComparator > prevComparator) score += (actComparator - prevComparator)
        actComparator = 0
        prevComparator = 0

        // -1 if a rival's path extended
        for (i in 0 until state.players.size) {
            if (i == playerIndex) continue
            for (path in state.players[i].paths) {
                actComparator += path.tiles.size
            }
            for (path in prev.players[i].paths) {
                prevComparator += path.tiles.size
            }
        }
        if (actComparator > prevComparator) score -= (actComparator - prevComparator)
        actComparator = 0
        prevComparator = 0
    }
}
