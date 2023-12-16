package template.UI.addEditPlace

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import template.R
import template.UI.addEditPlace.components.Demo_ExposedDropdownMenuBox
import template.UI.places.PlacesViewModel

@Composable
fun AddPlaceScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel(),
) {


    Column(
        modifier = Modifier
            .padding(16.dp, 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        Text(
            text = viewModel.mode,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displaySmall,
        )

        Spacer(modifier = Modifier.padding(2.dp))

        Text(
            text = stringResource(id = R.string.place_name),
            style = MaterialTheme.typography.bodyLarge,
        )
        TextField(
            value = viewModel.name,
            onValueChange = { newName -> viewModel.onPlaceNameChange(newName) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
        )
        Spacer(modifier = Modifier.padding(2.dp))

//
//        TextField(
//            modifier = Modifier.fillMaxWidth(),
//            value = viewModel.placeName,
//            onValueChange = { viewModel.onPlaceNameChange(it) },
//            placeholder = { Text(text = "e.g. Eiffel Tower") },
//
//            )
//        Spacer(modifier = Modifier.padding(8.dp))
//
//        Text(
//            text = stringResource(id = R.string.place_description),
//            style = MaterialTheme.typography.bodyLarge
//        )
//        Spacer(modifier = Modifier.padding(2.dp))
//
//        TextField(
//            modifier = Modifier.fillMaxWidth(),
//            value = viewModel.placeDescription,
//            onValueChange = { viewModel.onDescriptionChange(it) },
//        )
//
//        Spacer(modifier = Modifier.padding(8.dp))
//
//        Text(
//            text = stringResource(id = R.string.place_location),
//            style = MaterialTheme.typography.bodyLarge
//        )
//
//        TextField(
//            modifier = Modifier.fillMaxWidth(),
//            value = viewModel.placeLocation,
//            onValueChange = { viewModel.onChangeLocation(it) },
//        )
//
//        Spacer(modifier = Modifier.padding(8.dp))
//
//
//        Text(
//            text = stringResource(id = R.string.place_score),
//            style = MaterialTheme.typography.bodyLarge
//        )
//
//        // TD: change it to enum hover stars / smh
//        TextField(
//            modifier = Modifier.fillMaxWidth(),
//            value = viewModel.placeRating,
//            onValueChange = { viewModel.onRatingChange(it) },
//        )
//
//        Spacer(modifier = Modifier.padding(8.dp))
//
//        Text(
//            text = stringResource(id = R.string.place_visit_date),
//            style = MaterialTheme.typography.bodyLarge
//        )
//
//        Demo_ExposedDropdownMenuBox { viewModel.placeEventDate = it }
//
//
//        Button(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 16.dp)
//                .height(56.dp),
//            onClick = {
//                viewModel.submitPlace()
//            },
//            shape = MaterialTheme.shapes.extraLarge
//        ) {
//            Text(
//                text = stringResource(id = R.string.add_new_place),
//                style = MaterialTheme.typography.bodyLarge
//            )
//        }
//
//
//    }

    }
}
