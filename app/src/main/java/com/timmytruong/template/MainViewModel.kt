package com.timmytruong.template

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val gameState = MutableStateFlow(Game())
    fun gameState(): StateFlow<Game> = gameState

    fun onGridCellClicked(x: Int, y: Int) {
        if (gameState.value.winner != null) return
        if (gameState.value.board[x][y] != null) return

        viewModelScope.launch {
            val currentBoard = gameState.value.board
            val currentPlayer = gameState.value.playerTurn

            val updatedBoard =  currentBoard.toMutableList().apply {
                this[x] = this[x].toMutableList().apply {
                    if (currentPlayer == Marker.X) {
                        this[y] = Marker.X
                    } else {
                        this[y] = Marker.O

                    }
                }
            }

            gameState.emit(
                gameState.value.copy(
                    playerTurn = if (currentPlayer == Marker.X) Marker.O else Marker.X,
                    board = updatedBoard
                )
            )

            checkWinner()
        }
    }

    fun onIncreaseBoardSize() {
        viewModelScope.launch {
            val state = gameState.value
            state.reset(boardSize = state.boardSize + 1)
        }
    }

    fun onDecreaseBoardSize() {
        viewModelScope.launch {
            val state = gameState.value
            state.reset(boardSize = state.boardSize - 1)
        }
    }

    fun reset() {
        viewModelScope.launch {
            gameState.value.reset()
        }
    }

    private suspend fun Game.reset(boardSize: Int = this.boardSize) {
        gameState.emit(
            copy(
                boardSize = boardSize,
                playerTurn = Marker.X,
                winner = null,
                board = List(boardSize) { List(boardSize) { null } }
            )
        )
    }

    private suspend fun checkWinner(): Boolean {
        return checkRows() || checkColumns() || checkDiagonals()
    }

    private suspend fun checkRows(): Boolean {
        val board = gameState.value.board
        for (rowIdx in board.indices) {
            val row = board[rowIdx]
            if (row.checkWinner(WinPattern.Row(rowIdx))) return true
        }
        return false
    }

    private suspend fun checkColumns(): Boolean {
        val gameState = gameState.value
        for (c in 0 until gameState.boardSize) {
            val column = mutableListOf<Marker?>()
            for (r in 0 until gameState.boardSize) {
                val tile = gameState.board[r][c] ?: break
                column.add(tile)
            }

            if (column.checkWinner(WinPattern.Column(idx = c))) return true
        }
        return false
    }

    private suspend fun checkDiagonals(): Boolean {
        val gameState = gameState.value
        val diagonal = mutableListOf<Marker?>()

        for (idx in 0 until gameState.boardSize) {
            val tile = gameState.board[idx][idx] ?: break
            diagonal.add(gameState.board[idx][idx])
        }

        if (diagonal.checkWinner(WinPattern.ForwardDiagonal)) return true
        else diagonal.clear()

        for ((x, y) in gameState.backwardDiagonalCoords()) {
            val tile = gameState.board[x][y] ?: break
            diagonal.add(gameState.board[x][y])
        }

        return diagonal.checkWinner(pattern = WinPattern.BackwardDiagonal)
    }

    private suspend fun List<Marker?>.checkWinner(pattern: WinPattern): Boolean {
        if (size < gameState.value.boardSize) return false

        val winnerMarker = when {
            all { it == Marker.X } -> Marker.X
            all { it == Marker.O } -> Marker.O
            else -> return false
        }

        gameState.emit(
            gameState.value.copy(
                winner = Winner(
                    marker = winnerMarker,
                    winnerCoords = pattern.toCoords(),
                )
            )
        )

        return true
    }

    private fun WinPattern.toCoords(): Set<Pair<Int, Int>> {
        val game = gameState.value
        return when (this) {
            is WinPattern.Row -> game.rowCoords(this.idx)
            is WinPattern.Column -> game.colCoords(this.idx)
            WinPattern.ForwardDiagonal -> game.forwardDiagonalCoords()
            WinPattern.BackwardDiagonal -> game.backwardDiagonalCoords()
        }
    }
}