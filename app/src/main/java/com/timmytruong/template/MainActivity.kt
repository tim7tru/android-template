package com.timmytruong.template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.timmytruong.template.theme.MainAppTheme
import dagger.hilt.android.AndroidEntryPoint

private const val MAX_BOARD_SIZE = 8
private const val MIN_BOARD_SIZE = 3

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainAppTheme("Template") {
                MainScreen()
            }
        }
    }
}

@Composable
private fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    val game by mainViewModel.gameState().collectAsState()

    Column {
        Button(onClick = { mainViewModel.reset() }) {
            Text(text = "Reset")
        }

        if (game.winner != null) {
            Text("The winner is ${game.winner?.marker.toString()}")
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(game.boardSize),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
        ) {
            game.board.forEachIndexed { x, row ->
                itemsIndexed(items = row) { y, col ->
                    val areCoordsWinner = game.winner?.winnerCoords?.contains(x to y)
                    val backgroundColor = if (areCoordsWinner == true) Color.Green else Color.White
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .fillMaxSize()
                            .background(backgroundColor)
                            .clickable { mainViewModel.onGridCellClicked(x = x, y = y) },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = when (col) {
                                Marker.O -> "O"
                                Marker.X -> "X"
                                null -> ""
                            },
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                enabled = game.boardSize > MIN_BOARD_SIZE,
                onClick = { mainViewModel.onDecreaseBoardSize() },
            ) {
                Text(text = "-")
            }
            Text(text = game.boardSize.toString())

            Button(
                enabled = game.boardSize < MAX_BOARD_SIZE,
                onClick = { mainViewModel.onIncreaseBoardSize() },
            ) {
                Text(text = "+")
            }
        }
    }
}