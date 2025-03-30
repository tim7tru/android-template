package com.timmytruong.template

data class Game(
    val boardSize: Int = 4,
    val playerTurn: Marker = Marker.X,
    val winner: Winner? = null,
    val board: List<List<Marker?>> = List(boardSize) { List(boardSize) { null } }
) {
    fun rowCoords(rowIndex: Int): Set<Pair<Int, Int>> {
        return mutableSetOf<Pair<Int, Int>>().apply {
            repeat(boardSize) { colIndex -> add(rowIndex to colIndex) }
        }
    }

    fun colCoords(colIndex: Int): Set<Pair<Int, Int>> {
        return mutableSetOf<Pair<Int, Int>>().apply {
            repeat(boardSize) { rowIndex -> add(rowIndex to colIndex)}
        }
    }

    fun forwardDiagonalCoords(): Set<Pair<Int, Int>> {
        return mutableSetOf<Pair<Int, Int>>().apply {
            repeat(boardSize) { add(it to it) }
        }
    }

    fun backwardDiagonalCoords(): Set<Pair<Int, Int>> {
        val coords = mutableSetOf<Pair<Int, Int>>()
        var i = 0
        var j = boardSize - 1
        while (i <= j) {
            coords.add(i to j)
            coords.add(j to i)
            i++
            j--
        }
        return coords
    }
}

enum class Marker {
    O,
    X;
}

data class Winner(
    val marker: Marker,
    val winnerCoords: Set<Pair<Int, Int>>,
)

sealed interface WinPattern {

    data class Row(val idx: Int): WinPattern
    data class Column(val idx: Int) : WinPattern
    data object ForwardDiagonal : WinPattern
    data object BackwardDiagonal : WinPattern
}
