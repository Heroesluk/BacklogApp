//package template.UI.addEditPlace
//
//import androidx.compose.runtime.State
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.flow.launchIn
//import kotlinx.coroutines.flow.onEach
//import kotlinx.coroutines.launch
//import template.UI.places.PlaceState
//import template.domain.model.Place
//import template.domain.usecase.PlaceUseCases
//import template.domain.util.SortBy
//import template.domain.util.SortDirection
//import javax.inject.Inject
//
//
//class AddPlaceViewModel @Inject constructor(
//    private val placeUseCases: PlaceUseCases,
//) : ViewModel() {
//
//
////    private val _state = mutableStateOf(PlaceState())
////    val state: State<PlaceState> = _state
////
////    private var getPlacesJob: Job? = null
////
////    init {
////        viewModelScope.launch {
////            placeUseCases.addPlace(Place("eiffel Tower", "desc", "2022/12/11", 5, 0, "imgid"))
////        }
////        getNotes()
////    }
////
////    private fun getNotes() {
////        getPlacesJob?.cancel()
////        getPlacesJob = placeUseCases.getPlaces().onEach { places ->
////            _state.value = state.value.copy(
////                places = places,
////                orderBy = SortBy.DEFAULT,
////                orderDirection = SortDirection.ASC,
////            )
////
////        }.launchIn(viewModelScope)
////    }
//
//
//    private var _placeName by mutableStateOf("")
//    val placeName: String = _placeName
//
////    private val _placeDescription = mutableStateOf("")
////    val placeDescription: State<String> = _placeDescription
////
////    private val _placeRating = mutableStateOf("")
////    val placeRating: State<String> = _placeRating
////
////    private val _placeEventDate = mutableStateOf("")
////    val placeEventDate: State<String> = _placeEventDate
//
//
//    fun onPlaceNameChange(name: String) {
//        _placeName = name
//    }
//
////    fun onDescriptionChange(description: String) {
////        _placeDescription.value = description
////    }
////
////    fun onRatingChange(rating: String) {
////        _placeRating.value = rating
////    }
////
////    fun onDateChange(date: String) {
////        _placeEventDate.value = date
////
////    }
//
//
//}
