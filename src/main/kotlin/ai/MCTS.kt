package ai

class MCTS (private val rs: service.RootService, private val aiIndex: Int) {

    fun findNextMove(root: Node) : Move {
        while (true) {
            val node = selectPromisingNode(root)
            if (AiActionService.isGameOver(node.state)) {
                backpropagation(node)
                return node.move
            }
            expandNode(node)
            val nodeToExplore = node.children.first()
            simulateRandomPlayout(nodeToExplore)
            backpropagation(nodeToExplore)
        }
    }

    private fun selectPromisingNode(node: Node): Node {
        var current = node
        while (current.children.isNotEmpty()) {
            current.children.shuffle()
            current.children.sortBy { if (it.visitCount != 0.0) it.score + it.winCount / it.visitCount else it.score }
            current = current.children.first()
        }
        return current
    }

    private fun expandNode(node: Node) {
        node.getPossibleMoves().forEach {
            val child = Node(rs, node, it, aiIndex)
            child.setScore(node.state)
            node.children.add(child)
        }
        node.children.shuffle()
    }

    private fun simulateRandomPlayout(node: Node) {
        var state = node.state.copy()
        var shouldContinue = !AiActionService.isGameOver(state)

        while (shouldContinue) {
            val moves = node.getPossibleMoves()

            for (i in 0 until state.players.size) {
                TODO()
            }
            state = AiActionService.doMove(state, moves.shuffled().first())
        }
    }

    private fun backpropagation(last: Node) {
        var node: Node? = last
        while (node != null) {
            node.visitCount += 1
            if (aiIndex == 0 /*AI won*/)
                node.winCount += 1
            node = node.parent
        }
    }

}
