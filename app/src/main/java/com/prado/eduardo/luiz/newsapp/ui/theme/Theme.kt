package com.prado.eduardo.luiz.newsapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = BBC_White,
    onPrimary = BBC_Black,
    primaryContainer = BBC_GrayLight,
    onPrimaryContainer = BBC_Black,

    secondary = BBC_GrayMid,
    onSecondary = BBC_Black,
    secondaryContainer = BBC_GrayDark,
    onSecondaryContainer = BBC_White,

    background = BBC_Black,
    onBackground = BBC_White,

    surface = BBC_GrayDark,
    onSurface = BBC_White,
    surfaceVariant = BBC_GrayDark,
    onSurfaceVariant = BBC_White,

    error = Color(0xFFF2B8B5),
    onError = BBC_Black
)

private val LightColorScheme = lightColorScheme(
    primary = BBC_Black,
    onPrimary = BBC_White,
    primaryContainer = BBC_GrayDark,
    onPrimaryContainer = BBC_White,

    secondary = BBC_GrayMid,
    onSecondary = BBC_White,
    secondaryContainer = BBC_GrayLight,
    onSecondaryContainer = BBC_Black,

    background = BBC_White,
    onBackground = BBC_Black,

    surface = BBC_White,
    onSurface = BBC_Black,
    surfaceVariant = BBC_GrayLight,
    onSurfaceVariant = BBC_Black,

    error = Color(0xFFB3261E),
    onError = BBC_White
)

@Composable
fun NewsAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = BbcTypography,
        content = content
    )
}
