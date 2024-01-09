package template.UI.addEditPlace

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.SearchByTextRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import template.domain.model.Place
import template.domain.usecase.PlaceUseCases
import template.util.Validator
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val placeUseCases: PlaceUseCases,
    savedStateHandle: SavedStateHandle,
    @ApplicationContext applicationContext: Context,

    ) : ViewModel() {

    init {
        Places.initialize(applicationContext, "AIzaSyAiCWPf_5MTU2VzfVF1PrABKoZ53rZw-88")

    }

    var client = Places.createClient(applicationContext)

    val img = mutableStateOf<Bitmap?>(null)
    var imgIndex = 0


    fun requestPlaceApi() = viewModelScope.launch {
        client.searchByText(
            SearchByTextRequest.newInstance(
                name,
                listOf(
                    com.google.android.libraries.places.api.model.Place.Field.PHOTO_METADATAS,
                ),
            ),
        ).addOnSuccessListener { resp ->
            fetchImage(resp.places.get(0))
            Log.i("Fetched data: ", resp.places.get(0).photoMetadatas!!.toString())
        }
    }

    private fun fetchImage(place: com.google.android.libraries.places.api.model.Place) {

        val metada = place.photoMetadatas
        if (metada == null || metada.isEmpty()) {
            Log.w("TAG", "No photo metadata.")
        } else {

            if (imgIndex > metada.size) {
                imgIndex = 0
            }

            val photoMetadata = metada.get(imgIndex)

            val photoRequest = FetchPhotoRequest.builder(photoMetadata)
                .setMaxWidth(300) // Optional.
                .setMaxHeight(300) // Optional.
                .build()
            client.fetchPhoto(photoRequest)
                .addOnSuccessListener { it ->
//                    img.value = it.bitmap
                    var uriNew = saveImageToLocal(it.bitmap)
                    Log.i("fetched uri:", uriNew.toString())
                    onPhotoUriChange(uriNew)

                }.addOnFailureListener { exception: Exception ->
                    Log.i("Error", exception.toString())
                }
        }

        imgIndex += 1

    }


    var mode by mutableStateOf("Add place")
        private set
    var modeSubmit = "Finish editing"
    var name by mutableStateOf("")
        private set
    var nameCharacterLimitMessage by mutableStateOf("0/25")
        private set
    var nameCorrect by mutableStateOf(true)
        private set
    var description by mutableStateOf("")
        private set

    var descriptionCharacterLimitMessage by mutableStateOf("0/128")
        private set
    var descriptionCorrect by mutableStateOf(true)

    var date by mutableStateOf("")
        private set

    var sliderPosition by mutableFloatStateOf(0f)
        private set


    var dateCorrect by mutableStateOf(true)
        private set


    var score by mutableStateOf("")
        private set


    var selectedImageUri by mutableStateOf<Uri?>(null)
        private set

    var locationLat by mutableDoubleStateOf(-1.0)
        private set

    var locationLong by mutableDoubleStateOf(-1.0)
        private set


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var currentId = -1L

    init {

        savedStateHandle.get<String>("locationId")?.let { newLocationId ->
            if (newLocationId != "-1") {
                val latLong = newLocationId.split(":")
                locationLat = latLong[0].toDouble()
                locationLong = latLong[1].toDouble()

            }
        }

        savedStateHandle.get<Long>("placeId")?.let { noteId ->

            if (noteId != -1L) {
                currentId = noteId
                viewModelScope.launch {
                    placeUseCases.getPlace(noteId)?.also { note ->
                        onPlaceNameChange(note.name)
                        onDateChange(note.date)
                        onDescriptionChange(note.description)
                        onScoreSliderChange(note.score.toFloat())
                        onPhotoUriChange(Uri.parse(note.imageFileName))
                        mode = "Edit place"
                    }
                }
            } else {
                mode = "Edit place"
            }
        }


    }


    fun onPlaceNameChange(newName: String) {
        name = newName
        val l = name.length
        nameCorrect = l <= 25
        nameCharacterLimitMessage = "$l/25"
    }

    fun onDescriptionChange(newDescription: String) {
        description = newDescription
        val l = description.length
        descriptionCorrect = l <= 128
        descriptionCharacterLimitMessage = "$l/128"
    }


    fun onScoreSliderChange(newScore: Float) {
        sliderPosition = newScore
    }

    fun onDateChange(newDate: String) {
        date = newDate
        dateCorrect = Validator.isDateCorrect(date)

        // todo: auto add / to date and move cursor
//        if (date.length == 2 || date.length == 5) {
//            date += "/"
//        }

    }

    fun onPhotoUriChange(newUri: Uri?) {
        selectedImageUri = newUri

    }

    fun submitPlace() {
        viewModelScope.launch {
            if (mode == "Edit place") {
                placeUseCases.deletePlace(currentId)
            }
            if (locationLat != -1.0 && locationLong != -1.0) {
                placeUseCases.addPlace(Place(name, description, date, sliderPosition.toInt(), getUriToString(), locationLat, locationLong))
            } else {
                placeUseCases.addPlace(Place(name, description, date, sliderPosition.toInt(), getUriToString()))
            }
            _eventFlow.emit(UiEvent.SavePlace)
        }
    }


    sealed class UiEvent {
        object SavePlace : UiEvent()
    }

    private fun getUriToString(): String {
        if (selectedImageUri == null) {
            return ""
        }
        return selectedImageUri.toString()

    }

    fun saveImageToLocal(data: Bitmap): Uri? {
        val tempFile = File.createTempFile(name, ".png")
        val bytes = ByteArrayOutputStream()
        data.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val bitmapData = bytes.toByteArray()

        val fileOutPut = FileOutputStream(tempFile)
        fileOutPut.write(bitmapData)
        fileOutPut.flush()
        fileOutPut.close()


        return Uri.fromFile(tempFile)

    }


}
