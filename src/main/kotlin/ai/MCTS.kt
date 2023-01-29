package ai

import service.PlayerActionService

class MCTS (private val rs: service.RootService, private val aiIndex: Int) {

    fun findNextMoveSimplified(allowRotation: Boolean): Move {
        val defaultMove = Move(false, -1, -1, -1)
        val node = Node(rs, null, defaultMove, aiIndex)

        node.getPossibleMoves(allowRotation).forEach {
            val child = Node(rs, node, it, aiIndex)
            child.setScore()
            node.children.add(child)
        }
        node.children.shuffle()

        println("Decision made.")
        return node.children.maxByOrNull { it.score }!!.move
    }

    fun findNextMove(allowRotation: Boolean) : Move {
        val defaultMove = Move(false, -1, -1, -1)
        val root = Node(rs, null, defaultMove, aiIndex)

        var shouldStop = false
        while (true) {
            println("Still Thinking")
            val node = selectPromisingNode(root)
            if (PlayerActionService.isGameOver(node.state) || shouldStop) {
                backpropagation(node, true)
                println("Decision Made")
                return node.move
            }
            shouldStop = expandNode(node, aiIndex, allowRotation)
            val nodeToExplore = selectPromisingNode(node)
            val aiWon = simulateRandomPlayout(nodeToExplore, allowRotation)
            backpropagation(nodeToExplore, aiWon)
        }
    }
    //for the stupid AI xD
    fun findRandomMove(allowRotation: Boolean) : Move {
        val defaultMove = Move(false, -1, -1, -1)
        val node = Node(rs, null, defaultMove, aiIndex)

        node.getPossibleMoves(allowRotation).forEach {
            val child = Node(rs, node, it, aiIndex)
            child.setScore()
            node.children.add(child)
        }

        return node.children.random().move
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

    private fun expandNode(node: Node, playerIndex: Int, allowRotation: Boolean): Boolean {
        node.getPossibleMoves(allowRotation).forEach {
            val child = Node(rs, node, it, playerIndex)
            child.setScore()
            node.children.add(child)
        }
        node.children.shuffle()
        return node.children.isEmpty()
    }

    private fun simulateRandomPlayout(node: Node, allowRotation: Boolean): Boolean {
        var tempNode = node.copy()
        var playerIndex = aiIndex

        var shouldStop = false
        while (!PlayerActionService.isGameOver(tempNode.state) && !shouldStop) {
            playerIndex = (playerIndex + 1) % node.state.players.size
            shouldStop = expandNode(tempNode, playerIndex, allowRotation)
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
