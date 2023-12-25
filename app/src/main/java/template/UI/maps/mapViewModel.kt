package template.UI.maps

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState
import dagger.hilt.android.lifecycle.HiltViewModel
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

    init {
        places.add(PlaceLocation(false, MarkerState(LatLng(1.34, 103.89))))

    }

    fun onButtonEvent(cameraLat: Double, cameraLong: Double){
        if(state.value){
            buttonMsg.value = "Add new marker"
            val temp = places.last()
            places.removeLast();
            places.add(PlaceLocation(false, temp.markerState))
        }
        else{
            buttonMsg.value = "Submit new marker"
            places.add(PlaceLocation(true, MarkerState(LatLng(cameraLat,cameraLong))))
        }

        state.value = !state.value


    }


}



