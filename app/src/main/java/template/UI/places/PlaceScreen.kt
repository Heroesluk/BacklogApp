package template.UI.places

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
                    navController.navigate(Screen.AddEditScreen.route)
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
                actions = {Row(horizontalArrangement = Arrangement.SpaceBetween){
                    filterMenuButton()
                    sortMenuButton()

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
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            viewModel.state.value.places.forEach { place ->
                Log.i("place: ", place.name)
                placeEntry(place, deleteEntryFunc = { viewModel.deletePlace(it) }, editEntryFunc = { viewModel.editPlace(it) })
            }
            Spacer(modifier = Modifier.height(8.dp))


        }
    }


}





