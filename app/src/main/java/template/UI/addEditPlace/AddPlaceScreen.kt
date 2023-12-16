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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import template.R
import template.UI.Screen

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

        Text(
            text = stringResource(id = R.string.date),
            style = MaterialTheme.typography.bodyLarge,
        )
        TextField(
            value = viewModel.date,
            onValueChange = { newDate -> viewModel.onDateChange(newDate) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
        )
        Spacer(modifier = Modifier.padding(2.dp))

        Text(
            text = stringResource(id = R.string.score),
            style = MaterialTheme.typography.bodyLarge,
        )
        TextField(
            value = viewModel.score,
            onValueChange = { newScore -> viewModel.onRatingChange(newScore) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
        )
        Spacer(modifier = Modifier.padding(2.dp))

        Text(
            text = stringResource(id = R.string.description),
            style = MaterialTheme.typography.bodyLarge,
        )
        TextField(
            value = viewModel.description,
            onValueChange = { newDescription -> viewModel.onDescriptionChange(newDescription) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
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
    }
}
