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

    val listOfPlaces = mutableListOf<PlaceGenerated>()


    val vertexAI by lazy {
        VertexAI.Builder()
            .setAccessToken("ya29.a0AfB_byC0IlFarkZlxCDSp8bScQq1BUotSMND3RNKOgmA6opOK6IRpNRaemYRIL602YPoLk4UvV-VBebJDNnvMNBXrkxaS4UYRqZ-5rKMv90RdYEwHkFTE0mRr-TMT2ybGwQrqa0MFFxSB2ZfkRzZPL96sTGbwAdqGAKzRAaCgYKAYMSARESFQHGX2Mi3v-PvduVhWAAYtqLEd_9ZA0173")
            .setProjectId("ageless-webbing-405115")
            .build()
    }
    val textRequest by lazy {
        vertexAI.textRequest()
            .setTemperature(0.2)
            .setMaxTokens(256)
    }


    private
    var _output = MutableStateFlow("")
    val output: StateFlow<String> = _output

    var fetchedPlaces = mutableStateListOf<Place>()
    var fetchedImages = mutableStateListOf<Bitmap>()

    fun requestPlaceApi() = viewModelScope.launch {
        Log.i("places: ", listOfPlaces.size.toString())
        listOfPlaces.forEach {
            Log.i("places seen now: ", it.name)

            client.searchByText(
                SearchByTextRequest.newInstance(
                    it.name,
                    listOf(Place.Field.NAME, Place.Field.ID, Place.Field.ICON_URL, Place.Field.ADDRESS, Place.Field.PHOTO_METADATAS),
                ),
            ).addOnSuccessListener { resp ->
                Log.i("places seen: ", resp.places.toString())
                fetchedPlaces.add(resp.places.get(0))
                fetchImage(resp.places.get(0))
                Log.i("Fetched data: ", resp.places.get(0).photoMetadatas!!.toString())
            }

        }


    }

    fun request() = viewModelScope.launch {

        val prompt =
            """I'm going to ask you to write me down a list of places to visit in specific location - town or city. Example of how response could look like:
                Berlin Wall - A very big place around berlin destroyed in 1999;
                Reichstag Building - Houses the German parliament and offers stunning city views;
                Charlottenburg Palace - description=Baroque-style palace with beautiful gardens
                please respond in same message format, but with completely different places    
    description should be around 7-20 words. 
    can you suggest me 3 places to visit in $townInput"""
        val result = textRequest.execute(prompt)
        handleTextResult(result)

        requestPlaceApi()


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
                listOfPlaces.clear()

                fetchedPlaces.clear()
                fetchedImages.clear()


                _output.value = it
                try {
                    val places = it.split(".");
                    for (place in places) {
                        var pl = place.filterNot { it == '*' }
                        var lr = pl.split(" - ")
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
