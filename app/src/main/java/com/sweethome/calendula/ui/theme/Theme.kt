package com.sweethome.calendula.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val DarkColorScheme = darkColorScheme(
    background = background80,
    onBackground = onBackground80,
    primary = primary80,
    onPrimary = onPrimary80,
    primaryContainer = primaryContainer80,
    onPrimaryContainer = onPrimaryContainer80,
    secondary = secondary80,
    onSecondary = onSecondary80,
    secondaryContainer = secondaryContainer80,
    onSecondaryContainer = onSecondaryContainer80,
    tertiary = tertiary80,
    onTertiary = onTertiary80,
    tertiaryContainer = tertiaryContainer80,
    onTertiaryContainer = onTertiaryContainer80,
    surface = surface80,
    onSurface = onSurface80,
    surfaceVariant = surfaceVariant80,
    onSurfaceVariant = onSurfaceVariant80,
    error = error80,
    onError = onError80,
    errorContainer = errorContainer80,
    onErrorContainer = onErrorContainer80,
    outline = outline80,
    outlineVariant = outlineVariant80,
    scrim = scrim80
)

private val LightColorScheme = lightColorScheme(
    background = background80,
    onBackground = onBackground80,
    primary = primary80,
    onPrimary = onPrimary80,
    primaryContainer = primaryContainer80,
    onPrimaryContainer = onPrimaryContainer80,
    secondary = secondary80,
    onSecondary = onSecondary80,
    secondaryContainer = secondaryContainer80,
    onSecondaryContainer = onSecondaryContainer80,
    tertiary = tertiary80,
    onTertiary = onTertiary80,
    tertiaryContainer = tertiaryContainer80,
    onTertiaryContainer = onTertiaryContainer80,
    surface = surface80,
    onSurface = onSurface80,
    surfaceVariant = surfaceVariant80,
    onSurfaceVariant = onSurfaceVariant80,
    error = error80,
    onError = onError80,
    errorContainer = errorContainer80,
    onErrorContainer = onErrorContainer80,
    outline = outline80,
    outlineVariant = outlineVariant80,
    scrim = scrim80
)

@Composable
fun CalendulaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
//    val colorScheme =
//        when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }
    val colorScheme =
        if (!darkTheme) {
            LightColorScheme
        } else {
            DarkColorScheme
        }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primaryContainer.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}