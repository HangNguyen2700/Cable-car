package ai

import entity.Turn

data class Node(val rs: service.RootService, val parent: Node?, val move: Move, val aiIndex: Int) {
    val children: MutableList<Node> = mutableListOf()
    val state: Turn =
        if (parent != null) parent.state.doMove(move)
        else Turn (rs.currentGame!!.currentTurn.gameField.copy(),
            rs.currentGame!!.currentTurn.players.toMutableList())

    var score = 0.0
    var winCount = 0.0
    var visitCount = 0.0

    fun getPossibleMoves(): MutableList<Move> {
        val moves: MutableList<Move> = mutableListOf()

        for (x in 0 until state.gameField.field.size) {
            for (y in 0 until state.gameField.field[x].size) {
                if (state.gameField.field[x][y] != null
                    || !rs.playerActionService.isPositionLegal(x, y)) continue

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

        // -1 if another path was completed
        for (path in state.players[aiIndex].paths) {
            if (path.complete) actComparator++
        }
        for (path in prev.players[aiIndex].paths) {
            if (path.complete) prevComparator++
        }
        if (actComparator > prevComparator) score -= (actComparator - prevComparator)
        actComparator = 0
        prevComparator = 0

        // +1 if another rival's path was completed
        for (i in 0 until state.players.size) {
            if (i == aiIndex) continue
            for (path in state.players[i].paths) {
                if (path.complete) actComparator++
            }
        }
        for (i in 0 until prev.players.size) {
            if (i == aiIndex) continue
            for (path in prev.players[i].paths) {
                if (path.complete) prevComparator++
            }
        }
        if (actComparator > prevComparator) score += (actComparator - prevComparator)
        actComparator = 0
        prevComparator = 0

        // +1 if another train connected to power station
        for (path in state.players[aiIndex].paths) {
            if (true /*path connected to power station*/) actComparator++
        }
        for (path in prev.players[aiIndex].paths) {
            if (true /*path connected to power station*/) prevComparator++
        }
        if (actComparator > prevComparator) score += (actComparator - prevComparator)
        actComparator = 0
        prevComparator = 0

        // +1 if another path extended
        for (path in state.players[aiIndex].paths) {
            actComparator += path.tiles.size
        }
        for (path in prev.players[aiIndex].paths) {
            prevComparator += path.tiles.size
        }
        if (actComparator > prevComparator) score += (actComparator - prevComparator)
    }
}
