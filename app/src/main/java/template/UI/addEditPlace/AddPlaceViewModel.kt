package template.UI.addEditPlace

import androidx.compose.runtime.State

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import template.domain.usecase.PlaceUseCases
import javax.inject.Inject

class AddPlaceViewModel @Inject constructor(
    private val placeUseCases: PlaceUseCases,
) : ViewModel() {

    init {

    }

//    private var _placeName by mutableStateOf("")
//    val placeName: String = _placeName

//    private val _placeDescription = mutableStateOf("")
//    val placeDescription: State<String> = _placeDescription
//
//    private val _placeRating = mutableStateOf("")
//    val placeRating: State<String> = _placeRating
//
//    private val _placeEventDate = mutableStateOf("")
//    val placeEventDate: State<String> = _placeEventDate


//    fun onPlaceNameChange(name: String) {
//        _placeName = name
//    }
//
//    fun onDescriptionChange(description: String) {
//        _placeDescription.value = description
//    }
//
//    fun onRatingChange(rating: String) {
//        _placeRating.value = rating
//    }
//
//    fun onDateChange(date: String) {
//        _placeEventDate.value = date
//
//    }


}
