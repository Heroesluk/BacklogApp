package template.UI.places.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun sortMenuButton() {

    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = {
            expanded = true
        },
    ) {
        Icon(
            imageVector = Icons.Filled.List,
            contentDescription = "Localized description",
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            Text("Order by:")
            DropdownMenuItem(
                onClick = { }, text = { Text("Score") },
            )
            DropdownMenuItem(onClick = { }, text = { Text("Date") })

        }
    }
}
