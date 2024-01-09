package template.UI.asistant

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import template.UI.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun aiAssistantScreen(
    navController: NavController,
    viewModel: AssistantViewModel = hiltViewModel(),
) {
    val output = viewModel.output.collectAsState().value

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
        Column(
            modifier = Modifier
                .padding(10.dp, it.calculateTopPadding(), 10.dp, 10.dp),
        ) {
            Text(text = "What town do you want to explore")
            TextField(
                value = viewModel.townInput,
                onValueChange = {
                    viewModel.onTownFieldChange(it)
                },
            )
            Button(onClick = { viewModel.request() }) {
                Text(text = "Submit")
            }

            LazyColumn(
            ) {


                items(viewModel.fetchedPlaces){
                    Row(){
                        Text(text= it.name!!)
                        Text(text= it.address!!)

                    }
                }

                items(viewModel.fetchedImages){
                    Image(bitmap = it.asImageBitmap(),
                        contentDescription = "some useful description",)
                }

            }


        }

    }


}
