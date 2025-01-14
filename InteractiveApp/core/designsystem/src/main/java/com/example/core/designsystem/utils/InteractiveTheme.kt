package com.example.core.designsystem.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

data class ColorScheme(
    val generalColors: GeneralColors
)

val LocalColorScheme = staticCompositionLocalOf {
    ColorScheme(
        generalColors = GeneralColors()
    )
}

val LocalCustomDimens = staticCompositionLocalOf {
    ConstantDimens()
}

@Composable
fun InteractiveTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = ColorScheme(
        generalColors = GeneralColors.forTheme(darkTheme)
    )

    CompositionLocalProvider(
        LocalColorScheme provides colorScheme,
        content = content
    )
}

object InteractiveTheme {

    val colorScheme: ColorScheme
        @Composable
        get() = LocalColorScheme.current


    val dimens: ConstantDimens
        @Composable
        get() = LocalCustomDimens.current
}