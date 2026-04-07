package com.example.myprofileapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Palet warna Light & Fresh
val SoftSage = Color(0xFFE8F3E8)
val WhiteSage = Color(0xFFF9FFF9)
val BoldSage = Color(0xFF5D7A63)
val HotPink = Color(0xFFE94560)
val SunnyYellow = Color(0xFFFFD93D)
val TextDark = Color(0xFF2D3436)

private val LightColorScheme = lightColorScheme(
    primary = HotPink,
    secondary = BoldSage,
    tertiary = SunnyYellow,
    background = SoftSage,
    surface = WhiteSage,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TextDark,
    onSurface = TextDark
)

@Composable
fun MyProfileAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}