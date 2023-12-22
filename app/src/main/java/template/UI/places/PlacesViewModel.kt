package template.UI.places

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import template.domain.usecase.PlaceUseCases
import template.domain.util.SortBy
import template.domain.util.SortDirection
import javax.inject.Inject


@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val placeUseCases: PlaceUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(PlaceState())
    val state: State<PlaceState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getPlacesJob: Job? = null

    var placeToEditId by mutableStateOf(-1L)
        private set

    init {
        getNotes()
    }

    private fun getNotes() {
        getPlacesJob?.cancel()

        getPlacesJob = placeUseCases.getPlaces().onEach { places ->
            _state.value = state.value.copy(
                places = places,
                orderBy = SortBy.DEFAULT,
                orderDirection = SortDirection.ASC,
            )

        }.launchIn(viewModelScope)

//        getPlacesJob = launch {
//            _state.value = PlaceState(
//                places = placeUseCases.getPlacesList(),
//                orderBy = SortBy.DEFAULT,
//                orderDirection = SortDirection.ASC,
//            )
//        }

    }

    fun deletePlace(id: Long) {
        viewModelScope.launch {
            placeUseCases.deletePlace(id)
            Log.i("PlaceViewModel", "Successfully deleted place with id: $id ")
        }
    }

    fun editPlace(id: Long) {
        viewModelScope.launch {
            Log.i("PlaceViewModel", "Redirecting to edit place with id: $id ")
            _eventFlow.emit(UiEvent.EditPlace)
            placeToEditId = id;
        }

    }

    sealed class UiEvent {
        object EditPlace : UiEvent()
    }


}

