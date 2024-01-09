package template.theme

import android.annotation.TargetApi
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable


//private val DarkColorPalette = darkColorScheme(
//    primary = Color.White,
//    secondary = Color.Green,
//    tertiary = Color.Red,
//    outline = Color.Yellow,
//    background = DarkGray,
//    onBackground = Color.White,
//    surface = Color.Gray,
//    onSurface = DarkGray,
//)

private val DarkColors = darkColorScheme(
    primary = com.example.compose.md_theme_dark_primary,
    onPrimary = com.example.compose.md_theme_dark_onPrimary,
    primaryContainer = com.example.compose.md_theme_dark_primaryContainer,
    onPrimaryContainer = com.example.compose.md_theme_dark_onPrimaryContainer,
    secondary = com.example.compose.md_theme_dark_secondary,
    onSecondary = com.example.compose.md_theme_dark_onSecondary,
    secondaryContainer = com.example.compose.md_theme_dark_secondaryContainer,
    onSecondaryContainer = com.example.compose.md_theme_dark_onSecondaryContainer,
    tertiary = com.example.compose.md_theme_dark_tertiary,
    onTertiary = com.example.compose.md_theme_dark_onTertiary,
    tertiaryContainer = com.example.compose.md_theme_dark_tertiaryContainer,
    onTertiaryContainer = com.example.compose.md_theme_dark_onTertiaryContainer,
    error = com.example.compose.md_theme_dark_error,
    errorContainer = com.example.compose.md_theme_dark_errorContainer,
    onError = com.example.compose.md_theme_dark_onError,
    onErrorContainer = com.example.compose.md_theme_dark_onErrorContainer,
    background = com.example.compose.md_theme_dark_background,
    onBackground = com.example.compose.md_theme_dark_onBackground,
    surface = com.example.compose.md_theme_dark_surface,
    onSurface = com.example.compose.md_theme_dark_onSurface,
    surfaceVariant = com.example.compose.md_theme_dark_surfaceVariant,
    onSurfaceVariant = com.example.compose.md_theme_dark_onSurfaceVariant,
    outline = com.example.compose.md_theme_dark_outline,
    inverseOnSurface = com.example.compose.md_theme_dark_inverseOnSurface,
    inverseSurface = com.example.compose.md_theme_dark_inverseSurface,
    inversePrimary = com.example.compose.md_theme_dark_inversePrimary,
    surfaceTint = com.example.compose.md_theme_dark_surfaceTint,
    outlineVariant = com.example.compose.md_theme_dark_outlineVariant,
    scrim = com.example.compose.md_theme_dark_scrim,
)

@Composable
@TargetApi(Build.VERSION_CODES.S)
fun TemplateTheme(
    content: @Composable () -> Unit,
) {

    MaterialTheme(
        colorScheme = DarkColors,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}
