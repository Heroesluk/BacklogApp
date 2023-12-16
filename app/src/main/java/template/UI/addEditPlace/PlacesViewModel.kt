package template.UI.addEditPlace

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import template.domain.model.Place
import template.domain.usecase.PlaceUseCases
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val placeUseCases: PlaceUseCases,
) : ViewModel() {


    var mode = "Edit place"
    var modeSubmit = "Finish editing"
    var name by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var date by mutableStateOf("")
        private set
    var score by mutableStateOf("")
        private set

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onPlaceNameChange(newName: String) {
        name = newName
    }

    fun onDescriptionChange(newDescription: String) {
        description = newDescription
    }

    fun onRatingChange(newScore: String) {
        score = newScore
    }

    fun onDateChange(newDate: String) {
        date = newDate

    }

    fun submitPlace() {
        viewModelScope.launch {
            placeUseCases.addPlace(Place(name, description, date, score.toInt(), -1, ""))

            _eventFlow.emit(UiEvent.SavePlace)
        }


    }

    sealed class UiEvent {
        object SavePlace : UiEvent()
    }


}
