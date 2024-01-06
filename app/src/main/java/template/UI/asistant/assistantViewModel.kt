package template.UI.asistant

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hexascribe.vertexai.VertexAI
import com.hexascribe.vertexai.domain.VertexResult
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import template.domain.usecase.PlaceUseCases
import template.util.Contents
import template.util.Output
import template.util.Place
import javax.inject.Inject


@HiltViewModel
class AssistantViewModel @Inject constructor(
    private val placeUseCases: PlaceUseCases,
    savedStateHandle: SavedStateHandle,

    ) : ViewModel() {

    val client = HttpClient()


    var townInput by mutableStateOf("")
        private set


    val vertexAI by lazy {
        VertexAI.Builder()
            .setAccessToken("ya29.a0AfB_byAP7RQheU5plhem7nknZMkpardqvwQhz-JDgK8YMl9keAXcQVGhe52xc_g6LnqrZMvbLBkCrK72F6ALUbR7rlHoEpPMqbuK1Qqq95h5cFifwTdxzAURNFkoN_QUAGk8MW6A8pYJhzUifoW23icLa0LGJ-fqtI2kpAaCgYKAVcSARESFQHGX2MiO5zDVUw_OaFaHHqJ0HmPpg0173")
            .setProjectId("ageless-webbing-405115")
            .build()
    }
    val textRequest by lazy {
        vertexAI.textRequest()
            .setTemperature(0.8)
            .setMaxTokens(256)
    }

    val places = listOf(
        Place("Im going to ask you to write me down a list of places to visit in specific location - town or city. Your response should be formatted in a way: PlaceName - PlaceDescription description should be around 7-20 words. can you suggest me 5 places to visit in Berlin")
    )

    val contents = Contents("user", places)
    val out = Output(contents)

    val moshi: Moshi = Moshi.Builder().build()



    private
    var _output = MutableStateFlow("")
    val output: StateFlow<String> = _output

    fun request() = viewModelScope.launch {
        val prompt =
            """I'm going to ask you to write me down a list of places to visit in specific location - town or city. Your response should be formatted in a way:
                PlaceName - PlaceDescription; 
                description should be around 7-20 words. 
                can you suggest me 5 places to visit in Berlin"""
//        Log.i("prompt: ", prompt)
        Log.i("promt", body.toString())

//        val result = textRequest.execute(prompt)
        val response: HttpResponse =
            client.request("https://us-central1-aiplatform.googleapis.com/v1/projects/ageless-webbing-405115/locations/us-central1/publishers/google/models/gemini-pro-vision:streamGenerateContent") {
                method = HttpMethod.Post
                header(
                    "Authorization",
                    "Bearer ya29.a0AfB_byCCbrH9CukGYR53jhYf0KIQ6jFv1f30E9KsPBvSObBMoyYwj_qb-o9nUnf8lrLJ0Cp3pbIipnrMaxURrUYW6o20q6hFwdrNobd4QjSvxYvou5tnbteA0mk6Rqnwf4-l_UJLtVjMD_GsIBg11kXPLrD2dedUHpci1AaCgYKAY4SARESFQHGX2Mi3ygFiwJqSBdZJ7i8nrpSQQ0173",
                )
                setBody("""{
  "contents": {
    "role": "user",
    "parts": [
      {
        "text": "Im going to ask you to write me down a list of places to visit in specific location - town or city. Your response should be formatted in a way: PlaceName - PlaceDescription description should be around 7-20 words. can you suggest me 5 places to visit in Berlin"
      }
    ]
  }
}""")
                Log.i("a", this.body.toString())
            }

        Log.i("response: ", response.bodyAsText())

//        handleTextResult(result)
    }

    fun onTownFieldChange(newTown: String) {
        townInput = newTown
    }


    private fun handleTextResult(result: VertexResult<String>) {
        result
            .onSuccess {
                _output.value = it
                println("Text Result Success: $it")
            }
            .onFailure {
                println("Text Result Failure: " + it.message)
            }
    }


}
