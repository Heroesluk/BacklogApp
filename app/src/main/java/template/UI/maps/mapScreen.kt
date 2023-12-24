package template.UI.maps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.flow.map
import template.UI.places.PlacesViewModel
import template.domain.model.Place
import template.domain.usecase.PlaceUseCases
import template.domain.util.SortBy
import template.domain.util.SortDirection
import template.util.FilterSort

@Composable
fun mapScreen(
    navController: NavController,
) {

    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }




    val places = remember { mutableListOf<PlaceLocation>() }

    places.add(PlaceLocation(1.35f,103.95f))

    Box() {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
        ) {
            Marker(
                state = MarkerState(position = singapore),
                title = "Singapore",
                snippet = "Marker in Singapore",
                
            )

            MarkerInfoWindow(
                state = MarkerState(
                    position = LatLng(1.35, 103.90),
                ),
                draggable = true,
                content = {
                    Box(modifier = Modifier.background(Color.Black)) {
                        Text("Eiffel tower")
                    }
                },
                onClick = { it ->

                    it.showInfoWindow()
                    true
                },
            )
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter).padding(0.dp,0.dp,0.dp,20.dp)) {
            Button(onClick = {}){
                Text("Add new marker")
            }
        }

    }


}


data class PlaceLocation(
    val lat: Float,
    val long: Float,
)
