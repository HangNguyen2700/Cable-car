package ai

import service.PlayerActionService

class MCTS (private val rs: service.RootService, private val aiIndex: Int) {

    fun findNextMove() : Move {
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
            shouldStop = expandNode(node, aiIndex)
            val nodeToExplore = selectPromisingNode(node)
            val aiWon = simulateRandomPlayout(nodeToExplore)
            backpropagation(nodeToExplore, aiWon)
        }
    }
    //for the stupid AI xD
    fun findRandomMove() : Move {
        val defaultMove = Move(false, -1, -1, -1)
        val root = Node(rs, null, defaultMove, aiIndex)

        var shouldStop = false
        while (true) {
            //println("Still Thinking")
            val node = selectRandomNode(root)
            if (PlayerActionService.isGameOver(node.state) || shouldStop) {
                backpropagation(node, true)
                println("Bad Decision Made")
                return node.move
            }
            shouldStop = expandNode(node, aiIndex)
            val nodeToExplore = selectRandomNode(node)
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

    private fun expandNode(node: Node, playerIndex: Int): Boolean {
        node.getPossibleMoves().forEach {
            val child = Node(rs, node, it, playerIndex)
            child.setScore(node.state)
            node.children.add(child)
        }
        return node.children.isEmpty()
    }

    private fun simulateRandomPlayout(node: Node): Boolean {
        var tempNode = node.copy()
        var playerIndex = aiIndex

        var shouldStop = false
        while (!PlayerActionService.isGameOver(tempNode.state) && !shouldStop) {
//            for (player in tempNode.state.players) {
//                println(player.toString() + ": " + tempNode.state.players[playerIndex].handTile.toString())
//            }
//            println(tempNode.state.players[playerIndex].toString() + " - your turn.")

            playerIndex = (playerIndex + 1) % node.state.players.size
            shouldStop = expandNode(tempNode, playerIndex)
            tempNode = selectPromisingNode(tempNode)
        }

        return tempNode.state.players[aiIndex].score >= tempNode.state.players.maxOf { it.score }
    }
    //stupid Ai random move
    private fun selectRandomNode(node: Node): Node {
        var current =node
        while (current.children.isNotEmpty()) {
            current = current.children.maxByOrNull {
                if (it.visitCount != 0.0) it.score + it.winCount / it.visitCount
                else it.score
            }!!
        }
        return current
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
