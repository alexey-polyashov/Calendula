package com.sweethome.calendula.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val ColorScheme.activeDayLayout:Color
    @Composable
    get(){return (if(isSystemInDarkTheme()) ActiveDayLayout40 else ActiveDayLayout80)}

val ColorScheme.specialDayText:Color
    @Composable
    get(){return (if(isSystemInDarkTheme()) SpecialDayText40 else SpecialDayText80)}

val ColorScheme.markText:Color
    @Composable
    get(){return (if(isSystemInDarkTheme()) MarkText40 else MarkText80)}

val ColorScheme.captionText:Color
    @Composable
    get(){return (if(isSystemInDarkTheme()) CaptionText40 else CaptionText80)}

val ColorScheme.acceptButton:Color
    @Composable
    get(){return (if(isSystemInDarkTheme()) AcceptButton40 else AcceptButton80)}

val ColorScheme.actionButton:Color
    @Composable
    get(){return (if(isSystemInDarkTheme()) ActionButton40 else ActionButton80)}

val ColorScheme.cancelButton:Color
    @Composable
    get(){return (if(isSystemInDarkTheme()) CancelButton40 else CancelButton80)}

val ColorScheme.dangerButton:Color
    @Composable
    get(){return (if(isSystemInDarkTheme()) DangerButton40 else DangerButton80)}


val ColorScheme.switchOff:Color
    @Composable
    get(){return (if(isSystemInDarkTheme()) SwitchOff40 else SwitchOff80)}

val ColorScheme.switchOn:Color
    @Composable
    get(){return (if(isSystemInDarkTheme()) SwitchOn40 else SwitchOn80)}

private val DarkColorScheme = darkColorScheme(
    primary = MainLayout40,
    onPrimary = MainText40,
    primaryContainer = ElementsLayout40,
    onPrimaryContainer = ElementText40,
    secondary = ElementsLayout40,
    tertiary = ActiveDayLayout40,
)

private val LightColorScheme = lightColorScheme(
    primary = MainLayout80,
    onPrimary = MainText80,
    primaryContainer = ElementsLayout80,
    onPrimaryContainer = ElementText80,
    secondary = ElementsLayout80,
    tertiary = ActiveDayLayout80,
)

@Composable
fun CalendulaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme =
        when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
}
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}