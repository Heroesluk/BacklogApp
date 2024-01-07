package template.UI.asistant

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.SearchByTextRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import template.domain.usecase.PlaceUseCases
import javax.inject.Inject
import com.hexascribe.vertexai.VertexAI
import com.hexascribe.vertexai.domain.VertexResult
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import com.google.gson.JsonSerializer
import dagger.hilt.android.qualifiers.ApplicationContext


@HiltViewModel
class AssistantViewModel @Inject constructor(
    private val placeUseCases: PlaceUseCases,
    savedStateHandle: SavedStateHandle,
    @ApplicationContext applicationContext: Context,

    ) : ViewModel() {

    init {
        Places.initialize(applicationContext, "AIzaSyAiCWPf_5MTU2VzfVF1PrABKoZ53rZw-88")

    }

    var client = Places.createClient(applicationContext)


    var townInput by mutableStateOf("")
        private set


    val vertexAI by lazy {
        VertexAI.Builder()
            .setAccessToken("ya29.a0AfB_byBnKjVRyyCUNjgsHgQlE4yN9V17yW-SaJcegwlCQpbkQfM4hNuLHFyBVkdKdbcDXQX_RqlaqXbPUXy0FzovCJJInfU8vA_mbeqg--izmO4pMK1zCG5iK8d9o8zAQHxkbM0E3jfxVhjCpzPuHJpYo7wE1pglyIbATwaCgYKAdoSARESFQHGX2Mi0P5htl1ySewCLQ6hiJXf5w0173")
            .setProjectId("ageless-webbing-405115")
            .build()
    }
    val textRequest by lazy {
        vertexAI.textRequest()
            .setTemperature(0.8)
            .setMaxTokens(256)
    }


    private
    var _output = MutableStateFlow("")
    val output: StateFlow<String> = _output

    var fetchedPlaces = mutableStateListOf<Place>()
    var fetchedImages = mutableStateListOf<Bitmap>()
    fun request() = viewModelScope.launch {
        var job = false

        val response = client.searchByText(
            SearchByTextRequest.newInstance(
                "Eiffel tower",
                listOf(Place.Field.NAME, Place.Field.ID, Place.Field.ICON_URL, Place.Field.ADDRESS, Place.Field.PHOTO_METADATAS),
            ),
        ).addOnSuccessListener {
            fetchedPlaces.add(it.places.get(0))
            fetchImage(it.places.get(0))
            Log.i("Fetched data: ", it.places.get(0).photoMetadatas!!.toString())
        }

        val prompt =
            """I'm going to ask you to write me down a list of places to visit in specific location - town or city. Your response should be formatted in a way:
    PlaceName - PlaceDescription end with semicolon, dont use asterix
    description should be around 7-20 words. 
    can you suggest me 5 places to visit in $townInput"""
        val result = textRequest.execute(prompt)
        handleTextResult(result)
    }

    private fun fetchImage(place: Place) {
        val metada = place.photoMetadatas
        if (metada == null || metada.isEmpty()) {
            Log.w("TAG", "No photo metadata.")
        }
        val photoMetadata = metada.first()

        val attributions = photoMetadata?.attributions
        var foto = null
        val photoRequest = FetchPhotoRequest.builder(photoMetadata)
            .setMaxWidth(500) // Optional.
            .setMaxHeight(300) // Optional.
            .build()
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


    private fun handleTextResult(result: VertexResult<String>) {
        result
            .onSuccess {
                _output.value = it
                try {
                    val places = it.split(";");
                    val listOfPlaces = mutableListOf<PlaceGenerated>()
                    for (place in places) {
                        var lr = place.split(" - ")
                        listOfPlaces.add(PlaceGenerated(lr.get(0), lr.get(1)))
                    }

                    Log.i("1", listOfPlaces.toString())

                } catch (exception: Exception) {
                    Log.i("parsingError", "Couldn't parse response: $it")
                }

                Log.i("Text Result Success:", it)
            }
            .onFailure {
                Log.i("Text Result Failure: ", it.message.toString())
            }
    }


}
