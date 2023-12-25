package template.UI.places

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.collectLatest
import template.UI.Screen
import template.UI.places.components.filterMenuButton
import template.UI.places.components.placeEntry
import template.UI.places.components.sortMenuButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceScreen(
    navController: NavController,
    viewModel: PlacesViewModel = hiltViewModel(),

    ) {
    val state = viewModel.state.value


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is PlacesViewModel.UiEvent.EditPlace -> {
                    navController.navigate(Screen.AddEditScreen.route + "?placeId=${viewModel.placeToEditId}?locationId=${-1}")
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
                        IconButton(onClick = { navController.navigate(Screen.DisplayMap.route) }) {
                            Icon(Icons.Default.Place, contentDescription = "Go to maps")
                        }
                        filterMenuButton()
                        sortMenuButton(sortFun = { viewModel.triggerResort(it) })

                    }
                },

                )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.AddEditScreen.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
    ) { innerPadding ->
        Log.i("padding:", innerPadding.toString())
        LazyColumn(
            modifier = Modifier
                .padding(10.dp, innerPadding.calculateTopPadding(), 10.dp, 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(state.places) { place ->
                placeEntry(place, deleteEntryFunc = { viewModel.deletePlace(it) }, editEntryFunc = { viewModel.editPlace(it) })
            }

        }
    }


}





