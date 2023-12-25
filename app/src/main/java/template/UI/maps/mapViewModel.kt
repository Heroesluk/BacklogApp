package template.UI.maps

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import template.UI.places.PlaceState
import template.domain.usecase.PlaceUseCases
import template.domain.util.SortBy
import template.domain.util.SortDirection
import template.util.FilterSort
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val placeUseCases: PlaceUseCases,
    savedStateHandle: SavedStateHandle,

    ) : ViewModel() {

    val places = mutableStateListOf<PlaceLocation>()

    val state = mutableStateOf(false)
    var buttonMsg = mutableStateOf("Add new marker")

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getPlacesJob: Job? = null
    private val _placesState = mutableStateOf(PlaceState(emptyList(), SortBy.SCORE, SortDirection.DESC))
    val placesState: State<PlaceState> = _placesState

    init {
        getPlaces(FilterSort(SortBy.SCORE, SortDirection.DESC))
        places.clear()
    }

    fun onButtonEvent(cameraLat: Double, cameraLong: Double) {
        if (state.value) {
            buttonMsg.value = "Add new marker"
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.SubmitLocation);
            }
        } else {
            buttonMsg.value = "Submit new marker"
            places.add(PlaceLocation(true, MarkerState(LatLng(cameraLat, cameraLong)), "NewName"))

        }

        state.value = !state.value


    }

    private fun getPlaces(queryArguments: FilterSort) {
        getPlacesJob?.cancel()

        getPlacesJob = placeUseCases.getPlaces(queryArguments).onEach { places ->
            _placesState.value = placesState.value.copy(
                places = places,
                sortBy = queryArguments.sortBy,
                sortDirection = queryArguments.sortDirection,
            )

        }.launchIn(viewModelScope)
    }


    sealed class UiEvent {
        object SubmitLocation : UiEvent()
    }

    data class PlaceLocation(
        val draggable: Boolean,
        val markerState: MarkerState,
        val name: String,
    )

}







