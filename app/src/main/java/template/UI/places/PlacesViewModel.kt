package template.UI.places

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import template.domain.usecase.PlaceUseCases
import javax.inject.Inject
import androidx.compose.runtime.State
import template.domain.model.Place
import template.domain.util.SortBy
import template.domain.util.SortDirection


@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val placeUseCases: PlaceUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(PlaceState())
    val state: State<PlaceState> = _state

    init {
        placeUseCases.addPlace(Place("eiffel Tower", "desc", "2022/12/11", 5, 0, "imgid"))
        placeUseCases.addPlace(Place("Ramen", "desc2", "2022/12/11", 4, 1, "imgid"))

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
