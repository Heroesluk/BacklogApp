package template.UI.places.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import template.domain.util.SortBy
import template.domain.util.SortDirection
import template.util.FilterSort

@Composable
fun sortMenuButton(
    sortFun: (SortBy) -> Unit
) {

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
            DropdownMenuItem(onClick = {sortFun(SortBy.SCORE) }, text = { Text("Score") },)
            DropdownMenuItem(onClick = { sortFun(SortBy.NAME)}, text = { Text("Name") })
            DropdownMenuItem(onClick = { sortFun(SortBy.DEFAULT)}, text = { Text("Creation date") })
        }
    }
}
