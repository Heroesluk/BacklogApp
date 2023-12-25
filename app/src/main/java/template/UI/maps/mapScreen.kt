package template.UI.maps

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import template.UI.Screen
import template.UI.addEditPlace.PlacesViewModel
import template.UI.places.components.filterMenuButton
import template.UI.places.components.sortMenuButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mapScreen(
    navController: NavController,
    viewModel: MapViewModel = hiltViewModel(),

    ) {

    val cameraPos = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cameraPos, 10f)
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Places you've visited")
                },
                actions = {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        filterMenuButton()
                        sortMenuButton(sortFun = { })

                    }
                },

                )
        },
    ) { it ->
        Log.i("a",it.toString())

        Box() {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
            ) {
////            Marker(
////                state = MarkerState(position = singapore),
////                title = "Singapore",
////                snippet = "Marker in Singapore",
////
////            )
//
////            MarkerInfoWindow(
////                state = MarkerState(
////                    position = LatLng(1.35, 103.90),
////                ),
////                draggable = true,
////                content = {
////                    Box(modifier = Modifier.background(Color.Black)) {
////                        Text("Eiffel tower")
////                    }
////                },
////                onClick = { it ->
////
////                    it.showInfoWindow()
////                    true
////                },
////            )


                viewModel.places.forEach {
                    Marker(
                        state = it.markerState,
                        draggable = it.draggable,
                    )
                }
            }


            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(0.dp, 0.dp, 0.dp, 35.dp),
            ) {
                Button(
                    onClick = { viewModel.onButtonEvent(cameraPos.latitude,cameraPos.longitude)
                    },
                ) {
                    Text(viewModel.buttonMsg.value)
                }
            }
        }

    }


}


data class PlaceLocation(
    val draggable: Boolean,
    val markerState: MarkerState,
)
