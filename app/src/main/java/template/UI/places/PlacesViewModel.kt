package template.UI.places

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import template.domain.usecase.PlaceUseCases
import javax.inject.Inject
import androidx.compose.runtime.State
import kotlinx.coroutines.Job
import template.domain.model.Place
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import template.domain.util.SortBy
import template.domain.util.SortDirection


@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val placeUseCases: PlaceUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(PlaceState())
    val state: State<PlaceState> = _state

    private var getPlacesJob: Job? = null

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
    }

}

