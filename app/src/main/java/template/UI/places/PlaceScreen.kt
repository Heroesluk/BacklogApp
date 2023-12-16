package template.UI.places

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import template.UI.places.components.placeEntry


@Composable
fun PlaceScreen(
    navController: NavController,
    viewModel: PlacesViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value

    Column(modifier = Modifier.fillMaxWidth()) {
        state.places.map { place ->
            placeEntry(place)

        }
        Spacer(modifier = Modifier.height(8.dp))

    }

}
