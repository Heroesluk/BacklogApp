package template.UI.addEditPlace

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest
import template.R
import template.UI.Screen
import template.UI.addEditPlace.components.addPhotoToImage

@Composable
fun AddPlaceScreen(
    navController: NavController,
    viewModel: PlacesViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is PlacesViewModel.UiEvent.SavePlace -> {
                    navController.navigate(Screen.PlacesScreen.route)
                }
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp, 16.dp, 16.dp, 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        Text(
            text = viewModel.mode,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displaySmall,
        )

        Text(text = "LocationId: " + viewModel.locationLat.toString() + ":" + viewModel.locationLong.toString())

        if (viewModel.selectedImageUri != null) {
            Box(Modifier.height(100.dp)) {
                AsyncImage(
                    model = viewModel.selectedImageUri,
                    contentDescription = null,
                )
            }
        }

        Spacer(modifier = Modifier.padding(2.dp))

        Text(
            text = stringResource(id = R.string.place_name),
            style = MaterialTheme.typography.bodyLarge,
        )
        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { newName -> viewModel.onPlaceNameChange(newName) },
            keyboardOptions = KeyboardOptions(autoCorrect = true),
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            supportingText = { Text(viewModel.nameCharacterLimitMessage) },
            isError = !viewModel.nameCorrect,

            )
        Spacer(modifier = Modifier.padding(2.dp))

        Text(
            text = stringResource(id = R.string.date),
            style = MaterialTheme.typography.bodyLarge,
        )
        OutlinedTextField(
            value = viewModel.date,
            onValueChange = { newDate -> viewModel.onDateChange(newDate) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            isError = !viewModel.dateCorrect,
            label = { Text("DD/MM/YYYY") },
        )
        Spacer(modifier = Modifier.padding(2.dp))

        Text(text = "Score: ")
        Slider(
            valueRange = 1f..05f,
            steps = 3,

            value = viewModel.sliderPosition,
            onValueChange = { newScore -> viewModel.onScoreSliderChange(newScore) },
        )
        Text(text = viewModel.sliderPosition.toString())
        Spacer(modifier = Modifier.padding(2.dp))

        Text(
            text = stringResource(id = R.string.description),
            style = MaterialTheme.typography.bodyLarge,
        )
        TextField(
            value = viewModel.description,
            onValueChange = { newDescription -> viewModel.onDescriptionChange(newDescription) },
            keyboardOptions = KeyboardOptions(autoCorrect = true),
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            supportingText = { Text(viewModel.descriptionCharacterLimitMessage) },
            isError = !viewModel.descriptionCorrect,
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .height(56.dp),
            onClick = {
                viewModel.submitPlace()
            },

            shape = MaterialTheme.shapes.extraLarge,
        ) {
            Text(
                text = viewModel.modeSubmit,
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        Button(onClick = { viewModel.requestPlaceApi() }) {
            Text(text = "Click this")
        }

        if (viewModel.img.value != null) {
            Image(
                bitmap = viewModel.img.value!!.asImageBitmap(),
                contentDescription = "some useful description",
            )
        }

//        addPhotoToImage(saveImage = { viewModel.onPhotoUriChange(it) })

    }
}
