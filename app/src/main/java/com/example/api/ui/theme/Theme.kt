package com.example.api.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = SpotifyGreen,
    onPrimary = SpotifyText,
    primaryContainer = SpotifyGreenSoft,
    onPrimaryContainer = SpotifyText,
    secondary = SpotifyGreenDark,
    onSecondary = SpotifySurface,
    secondaryContainer = SpotifySurfaceMuted,
    onSecondaryContainer = SpotifyText,
    tertiary = SpotifyGreenDark,
    onTertiary = SpotifySurface,
    background = SpotifyMintBackground,
    onBackground = SpotifyText,
    surface = SpotifySurface,
    onSurface = SpotifyText,
    surfaceVariant = SpotifySurfaceMuted,
    onSurfaceVariant = SpotifyTextMuted,
    outline = SpotifyOutline
)

private val DarkColorScheme = darkColorScheme(
    primary = SpotifyGreen,
    onPrimary = SpotifyText,
    primaryContainer = SpotifyDarkSurfaceMuted,
    onPrimaryContainer = SpotifyDarkText,
    secondary = SpotifyGreenSoft,
    onSecondary = SpotifyText,
    background = SpotifyDarkBackground,
    onBackground = SpotifyDarkText,
    surface = SpotifyDarkSurface,
    onSurface = SpotifyDarkText,
    surfaceVariant = SpotifyDarkSurfaceMuted,
    onSurfaceVariant = SpotifyDarkText,
    outline = SpotifyOutline
)

@Composable
fun ApiTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}
