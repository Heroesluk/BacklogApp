package template.UI.maps

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.flow.collectLatest
import template.UI.Screen
import template.UI.places.components.filterMenuButton
import template.UI.places.components.sortMenuButton

import kotlin.math.pow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mapScreen(
    navController: NavController,
    viewModel: MapViewModel = hiltViewModel(),

    ) {

    val cameraPos = LatLng( 51.759445, 19.457216)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cameraPos, 10f)
    }


    val imagePrep = HashMap<Long, ImageRequest>()

    viewModel.placesState.value.places.filter { it.imageFileName != "" && it.latitude != -1.0 && it.longtitude != -1.0 }.map { it ->
        imagePrep[it.id] = ImageRequest.Builder(LocalContext.current)
            .data(Uri.parse(it.imageFileName)).allowHardware(false)
            .build()
    }





    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is MapViewModel.UiEvent.SubmitLocation -> {
                    var long = viewModel.place.value.markerState.position.longitude
                    var lat = viewModel.place.value.markerState.position.latitude

                    navController.navigate(
                        Screen.AddEditScreen.route + "?placeId=${-1}?locationId=" +
                            lat.toString() + ":" + long.toString(),
                    )
                }
            }
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Places you've visited")
                },
                actions = {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        IconButton(onClick = { navController.navigate(Screen.PlacesScreen.route) }) {
                            Icon(Icons.Default.List, contentDescription = "Navigate to list")
                        }
                    }
                },

                )
        },
    ) { it ->
        Log.i("a", it.toString())

        Box() {
            GoogleMap(
//                properties = MapProperties(isBuildingEnabled=true),
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
            ) {
                viewModel.placesState.value.places.forEach { place ->
                    if (place.latitude != -1.0 && place.longtitude != -1.0) {
                        MarkerInfoWindow(
                            state = MarkerState(LatLng(place.latitude, place.longtitude)),
                            draggable = false,
                            content = {
                                Row(
                                    modifier = Modifier
                                        .background(Color.Black)
                                        .height(95.dp)
                                        .width(75.dp),
                                ) {
                                    Text(text = place.name, style = MaterialTheme.typography.labelMedium)
                                    if (place.imageFileName != "") {

                                        AsyncImage(
                                            model = imagePrep.get(place.id),
                                            contentDescription = null,
                                            contentScale = ContentScale.FillWidth,
                                        )
                                    }
                                }
                            },
                        )
                    }

                }
                if (viewModel.place.value.draggable) {
                    Marker(
                        state = viewModel.place.value.markerState,
                        draggable = viewModel.place.value.draggable,
                    )
                }
            }


            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(0.dp, 0.dp, 0.dp, 35.dp),
            ) {
                Button(
                    onClick = {
                        Log.i("camera positoin", cameraPositionState.position.zoom.toString())
                        viewModel.onButtonEvent(cameraPos.latitude, cameraPos.longitude)
                    },
                ) {
                    Text(viewModel.buttonMsg.value)
                }
            }
        }

    }


}

