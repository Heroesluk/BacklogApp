package template.UI.places

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import template.UI.Screen
import template.UI.places.components.placeEntry


@Composable
fun PlaceScreen(
    navController: NavController,
    viewModel: PlacesViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value


    Button(
        onClick = { navController.navigate(Screen.AddEditScreen.route) },
    ) {
        Text(
            text = "Add new place",
            style = MaterialTheme.typography.bodyLarge,
        )
    }
    Spacer(modifier = Modifier.height(8.dp))

    Column(modifier = Modifier.fillMaxWidth()) {
        state.places.map { place ->
            Log.i("place: ", place.name)
            placeEntry(place, deleteEntryFunc = { viewModel.deletePlace(it) })
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}
