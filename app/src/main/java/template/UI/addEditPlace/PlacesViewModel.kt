package template.UI.addEditPlace

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import template.domain.model.Place
import template.domain.usecase.PlaceUseCases
import template.util.Validator
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val placeUseCases: PlaceUseCases,
    savedStateHandle: SavedStateHandle,

    ) : ViewModel() {


    var mode by mutableStateOf("Add place")
        private set
    var modeSubmit = "Finish editing"
    var name by mutableStateOf("")
        private set
    var nameCharacterLimitMessage by mutableStateOf("0/25")
        private set
    var nameCorrect by mutableStateOf(true)
        private set
    var description by mutableStateOf("")
        private set

    var descriptionCharacterLimitMessage by mutableStateOf("0/128")
        private set
    var descriptionCorrect by mutableStateOf(true)

    var date by mutableStateOf("")
        private set

    var sliderPosition by mutableFloatStateOf(0f)
        private set


    var dateCorrect by mutableStateOf(true)
        private set


    var score by mutableStateOf("")
        private set


    var selectedImageUri by mutableStateOf<Uri?>(null)
        private set

    var locationId by mutableStateOf(-1L)
        private set


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var currentId = -1L

    init {

        savedStateHandle.get<Long>("locationId")?.let { newLocationId ->
            if( newLocationId != -1L){
                locationId = newLocationId
            }
        }

        savedStateHandle.get<Long>("placeId")?.let { noteId ->

            if (noteId != -1L) {
                currentId = noteId
                viewModelScope.launch {
                    placeUseCases.getPlace(noteId)?.also { note ->
                        onPlaceNameChange(note.name)
                        onDateChange(note.date)
                        onDescriptionChange(note.description)
                        onScoreSliderChange(note.score.toFloat())
                        onPhotoUriChange(Uri.parse(note.imageFileName))
                        mode = "Edit place"
                    }
                }
            } else {
                mode = "Edit place"
            }
        }



    }


    fun onPlaceNameChange(newName: String) {
        name = newName
        val l = name.length
        nameCorrect = l <= 25
        nameCharacterLimitMessage = "$l/25"
    }

    fun onDescriptionChange(newDescription: String) {
        description = newDescription
        val l = description.length
        descriptionCorrect = l <= 128
        descriptionCharacterLimitMessage = "$l/128"
    }


    fun onScoreSliderChange(newScore: Float) {
        sliderPosition = newScore
    }

    fun onDateChange(newDate: String) {
        date = newDate
        dateCorrect = Validator.isDateCorrect(date)

        // todo: auto add / to date and move cursor
//        if (date.length == 2 || date.length == 5) {
//            date += "/"
//        }

    }

    fun onPhotoUriChange(newUri: Uri?) {
        if (newUri != null) {
            selectedImageUri = newUri
        }
    }

    fun submitPlace() {
        viewModelScope.launch {
            if (mode == "Edit place") {
                placeUseCases.deletePlace(currentId)
            }
            placeUseCases.addPlace(Place(name, description, date, sliderPosition.toInt(), -1, getUriToString()))
            _eventFlow.emit(UiEvent.SavePlace)
        }
    }


    sealed class UiEvent {
        object SavePlace : UiEvent()
    }

    private fun getUriToString(): String {
        if (selectedImageUri == null) {
            return ""
        }
        return selectedImageUri.toString()

    }


}
