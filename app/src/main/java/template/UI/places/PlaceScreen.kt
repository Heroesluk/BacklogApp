package template.UI.places

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun PlaceScreen(
    viewModel: PlacesViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value

    state.places.map { place ->
        generateEntry(place)
    }
}
