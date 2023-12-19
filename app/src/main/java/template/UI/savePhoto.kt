package template.UI

import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import template.domain.model.Place

//@Composable
//fun savePhoto() {
//// Registers a photo picker activity launcher in single-select mode.
//    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
//        // Callback is invoked after the user selects a media item or closes the
//        // photo picker.
//        if (uri != null) {
//            Log.d("PhotoPicker", "Selected URI: $uri")
//        } else {
//            Log.d("PhotoPicker", "No media selected")
//        }
//
//        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//
//        val mimeType = "image"
//        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType)))
//
//    }
//
//}
