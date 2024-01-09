package template.UI.asistant

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.SearchByTextRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import template.domain.usecase.PlaceUseCases
import java.net.URLEncoder
import javax.inject.Inject


@HiltViewModel
class AssistantViewModel @Inject constructor(
    private val placeUseCases: PlaceUseCases,
    savedStateHandle: SavedStateHandle,
    @ApplicationContext applicationContext: Context,

    ) : ViewModel() {


    val api = "AIzaSyAiCWPf_5MTU2VzfVF1PrABKoZ53rZw-88"

    init {
        Places.initialize(applicationContext, api)

    }

    var client = Places.createClient(applicationContext)
    var townInput by mutableStateOf("")
        private set
    val listOfPlaces = mutableListOf<PlaceGenerated>()


    private
    var _output = MutableStateFlow("")
    val output: StateFlow<String> = _output

    var fetchedPlaces = mutableStateListOf<Place>()
    var fetchedImages = mutableStateListOf<Bitmap>()

    fun requestPlaceApi() = viewModelScope.launch {
        Log.i("places: ", listOfPlaces.size.toString())
        listOfPlaces.forEach {
            client.searchByText(
                SearchByTextRequest.newInstance(
                    it.name,
                    listOf(Place.Field.NAME, Place.Field.ID, Place.Field.ICON_URL, Place.Field.ADDRESS, Place.Field.PHOTO_METADATAS),
                ),
            ).addOnSuccessListener { resp ->
                Log.i("places seen: ", resp.places.toString())
                fetchedPlaces.add(resp.places.get(0))
                fetchImage(resp.places.get(0), listOfPlaces.indexOf(it))
            }
        }


    }

    fun request() = viewModelScope.launch {

            val prompt = """Im going to ask you to write me down a list of places to visit in specific location - town or city. 
   List of places should be in format: PlaceName - PlaceDescription;PlaceName2 - PlaceDescription2 
    Don't number the list, Every list entry should start with PlaceName and end with semicolon 
    description should be around 7-20 words. can you suggest me 5 places to visit in $townInput"""

            val client = HttpClient {
            }

            val encodedurl = URLEncoder.encode(prompt, "UTF-8")

            Log.i("Promt:", encodedurl)
            val response = client.request("https://geminifinal-s3crlxaida-ew.a.run.app/ai/$encodedurl") {
                method = HttpMethod.Get
            }

            handleTextResult(response.bodyAsText())
            requestPlaceApi()





    }

    private fun fetchImage(place: Place, index: Int) {
        val metada = place.photoMetadatas
        if (metada == null || metada.isEmpty()) {
            Log.w("TAG", "No photo metadata.")
        }
        val photoMetadata = metada.first()


        var data = photoMetadata.toString().split(",").toString()
        val regex = Regex("photoReference=([^,]+)")
        val matchResult = regex.find(data)

        if (matchResult != null) {
            val photoReference = matchResult.groupValues[1]
            val imglink =
                "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=$photoReference&sensor=false&key=$api"
            Log.i("Found link: ", imglink)
        }


        val photoRequest = FetchPhotoRequest.builder(photoMetadata)
            .setMaxWidth(500) // Optional.
            .setMaxHeight(300) // Optional.
            .build()
        Log.i("photo request: ", photoRequest.toString())
        client.fetchPhoto(photoRequest)
            .addOnSuccessListener { it ->
                fetchedImages.add(it.bitmap)
            }.addOnFailureListener { exception: Exception ->
                Log.i("Error", exception.toString())
            }
    }


    fun onTownFieldChange(newTown: String) {
        townInput = newTown
    }


    private fun handleTextResult(result: String) {
        listOfPlaces.clear()

        fetchedPlaces.clear()
        fetchedImages.clear()


        _output.value = result
        try {
            val places = result.split(";");
            for (place in places) {
                var lr = place.split(" - ")
                listOfPlaces.add(PlaceGenerated(lr.get(0), lr.get(1)))

            }

            Log.i("1", listOfPlaces.toString())

        } catch (exception: Exception) {
            Log.i("parsingError", "Couldn't parse response: $result")
        }
    }

}
