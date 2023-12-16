package template.UI.places

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import template.domain.model.Place
import template.domain.usecase.PlaceUseCases
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import template.domain.util.SortBy
import template.domain.util.SortDirection


@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val placeUseCases: PlaceUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(PlaceState())
    val state: State<PlaceState> = _state

    init {
        getNotes()
    }

    private fun getNotes() {
        _state.value = state.value.copy(
            places = placeUseCases.getPlaces(),
            orderBy = SortBy.DEFAULT,
            orderDirection = SortDirection.ASC,
        )
    }

}
