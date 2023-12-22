package template.UI.places.components

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import template.R
import template.domain.model.Place

@Composable
fun placeEntry(
    place: Place,
    deleteEntryFunc: (Long) -> Unit,
    editEntryFunc: (Long) -> Unit,
) {


    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .clickable {
                expanded = !expanded;
            }
            .fillMaxWidth(),
    ) {
        Column {
            Box {
                Text(
                    text = place.name, fontSize = 18.sp,
                    style = MaterialTheme.typography.displaySmall,
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    DropdownMenuItem(
                        onClick = { deleteEntryFunc(place.id) }, text = { Text("Delete") },
                    )
                    DropdownMenuItem(onClick = { editEntryFunc(place.id) }, text = { Text("Edit") })

                }
            }
            if (expanded) {
                Text(
                    text = place.description,
                    fontSize = 8.sp,
                    modifier = Modifier.fillMaxWidth(0.4f),
                )
            }
            Text(text = place.date)
            Text(text = place.score.toString())


        }
        Spacer(
            Modifier
                .weight(1f)
                .fillMaxWidth(),
        )
//        // todo: add error handling / less naive validation
        if (place.imageFileName != "") {

            Log.i("image name:", place.imageFileName)
            Box(Modifier.height(100.dp)) {
                AsyncImage(
                    model = Uri.parse(place.imageFileName),
                    contentDescription = null,
                )
            }

        }

    }

    Spacer(modifier = Modifier.height(8.dp))


}
