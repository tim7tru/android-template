package com.timmytruong.template.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Shapes
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val DarkColorPalette = darkColors(
    primary = PrimaryLight,
    primaryVariant = PrimaryDark,
    secondary = GreenLight,
    secondaryVariant = GreenDark
)

private val LightColorPalette = lightColors(
    primary = Green,
    primaryVariant = GreenDark,
    secondary = Green,
    secondaryVariant = GreenDark,
    onPrimary = Color.White,
    onBackground = onPrimary,
    onSurface = onPrimary,
    onSecondary = Color.White,
    surface = Color.White,
    background = PrimaryLight

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MainAppTheme(
    title: String,
    content: @Composable (PaddingValues) -> Unit
) {
    MaterialTheme(
        colors = LightColorPalette, typography = Typography, shapes = Shapes,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colors.surface,
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        ) {
            content(it)
        }
    }
}