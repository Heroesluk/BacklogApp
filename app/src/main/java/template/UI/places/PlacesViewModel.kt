package template.UI.places

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import template.domain.model.Place
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

    private var getPlacesJob: Job? = null

    init {
        viewModelScope.launch {
            placeUseCases.getPlaces().onEach { places ->
                places.map { place ->
                    placeUseCases.deletePlace(place.id)

                }
            }

        }
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

//        Log.i("places: ")
    }


}

