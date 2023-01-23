package ai

class MCTS (private val rs: service.RootService, private val aiIndex: Int) {

    fun findNextMove() : Move {
        val defaultMove = Move(false, -1, -1, -1)
        val root = Node(rs, null, defaultMove, aiIndex)

        while (true) {
            println("Still thinking")
            val node = selectPromisingNode(root)
            if (AiActionService.isGameOver(node.state)) {
                backpropagation(node, true)
                println("Decision made")
                return node.move
            }
            expandNode(node, aiIndex)
            val nodeToExplore = selectPromisingNode(node)
            val aiWon = simulateRandomPlayout(nodeToExplore)
            backpropagation(nodeToExplore, aiWon)
        }
    }

    private fun selectPromisingNode(node: Node): Node {
        var current = node
        while (current.children.isNotEmpty()) {
            current = current.children.maxByOrNull {
                if (it.visitCount != 0.0) it.score + it.winCount / it.visitCount
                else it.score
            }!!
        }
        return current
    }

    private fun expandNode(node: Node, playerIndex: Int) {
        node.getPossibleMoves().forEach {
            val child = Node(rs, node, it, playerIndex)
            child.setScore(node.state)
            node.children.add(child)
        }
    }

    private fun simulateRandomPlayout(node: Node): Boolean {
        var tempNode = node.copy()
        var playerIndex = aiIndex

        while (!AiActionService.isGameOver(tempNode.state)) {
            playerIndex = (playerIndex + 1) % node.state.players.size
            expandNode(tempNode, playerIndex)
            tempNode = selectPromisingNode(tempNode)
        }

        return tempNode.state.players[aiIndex].score >= tempNode.state.players.maxOf { it.score }
    }

    private fun backpropagation(last: Node, aiWon: Boolean) {
        var node: Node? = last
        while (node != null) {
            node.visitCount += 1
            if (aiWon) node.winCount += 1
            node = node.parent
        }
    }

}
