package template.UI.maps

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import template.domain.usecase.PlaceUseCases
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val placeUseCases: PlaceUseCases,
    savedStateHandle: SavedStateHandle,

    ) : ViewModel() {

    val places = mutableStateListOf<PlaceLocation>()

    val state = mutableStateOf(false)
    var buttonMsg =   mutableStateOf("Add new marker")

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        places.add(PlaceLocation(false, MarkerState(LatLng(1.34, 103.89)), "NewName"))

    }

    fun onButtonEvent(cameraLat: Double, cameraLong: Double){
        if(state.value){
            buttonMsg.value = "Add new marker"
            val temp = places.last()
            places.removeLast();
            places.add(PlaceLocation(false, temp.markerState, "NewName"))
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.SubmitLocation);
            }
        }
        else{
            buttonMsg.value = "Submit new marker"
            places.add(PlaceLocation(true, MarkerState(LatLng(cameraLat,cameraLong)), "NewName"))

        }

        state.value = !state.value


    }

    sealed class UiEvent {
        object SubmitLocation : UiEvent()
    }
    data class PlaceLocation(
        val draggable: Boolean,
        val markerState: MarkerState,
        val name: String
    )

}







