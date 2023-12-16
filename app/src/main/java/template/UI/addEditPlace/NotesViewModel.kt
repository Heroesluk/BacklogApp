package template.UI.addEditPlace

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import template.domain.usecase.PlaceUseCases
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val placeUseCases: PlaceUseCases,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddEditPlaceState())
    val uiState: StateFlow<AddEditPlaceState> = _uiState.asStateFlow();

    val mode = "Edit place"

    var name by mutableStateOf("")
        private set


    fun onPlaceNameChange(newName: String) {
        name = newName
    }

}
