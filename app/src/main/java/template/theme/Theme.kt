package template.theme

import android.annotation.TargetApi
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray


private val DarkColorPalette = darkColorScheme(
    primary = Color.White,
    background = DarkGray,
    onBackground = Color.White,
    surface = Color.Cyan,
    onSurface = DarkGray,
)

@Composable
@TargetApi(Build.VERSION_CODES.S)
fun TemplateTheme(
    content: @Composable () -> Unit,
) {

    MaterialTheme(
        colorScheme = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}
