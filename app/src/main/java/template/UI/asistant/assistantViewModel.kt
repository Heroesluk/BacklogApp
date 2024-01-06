package template.UI.asistant

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun request() = viewModelScope.launch {
        val prompt =
            """I'm going to ask you to write me down a list of places to visit in specific location - town or city. Your response should be formatted in a way:
    PlaceName - PlaceDescription end with semicolon, dont use asterix
    description should be around 7-20 words. 
    can you suggest me 5 places to visit in $townInput"""

        val result = textRequest.execute(prompt)



        handleTextResult(result)
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
