package com.kedit.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(

    background = DarkBackground,

    surface = DarkSurface,

    primary = DarkPrimary,

    onBackground = DarkText,

    onSurface = DarkText
)

private val LightColors = lightColorScheme(

    background = LightBackground,

    surface = LightSurface,

    primary = LightPrimary,

    onBackground = LightText,

    onSurface = LightText
)

@Composable
fun KEditTheme(

    darkTheme: Boolean,

    content: @Composable () -> Unit
) {

    MaterialTheme(

        colorScheme =
            if (darkTheme)
                DarkColors
            else
                LightColors,

        content = content
    )
}