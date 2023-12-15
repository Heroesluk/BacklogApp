package template.UI.places

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import template.domain.model.Place

@Composable
fun generateEntry(entry: Place) {
    var expanded by remember { mutableStateOf(false) }
    //this feels like affects performance hard so i'll leave it for now
//    var dict = mapOf(true to 200.dp, false to 100.dp)


    Row(
        modifier = Modifier
            .clickable {
                expanded = !expanded;
            }
            .fillMaxWidth(),
//            .height(dict.get(expanded)!!)
    ) {
        Column {
            Text(
                text = entry.name, fontSize = 18.sp,
                style = MaterialTheme.typography.displaySmall,
            )
//            Text(
//                text = entry.name,
//                fontSize = 10.sp,
//                style = MaterialTheme.typography.bodyLarge,
//                textDecoration = TextDecoration.Underline,
//            )
            if (expanded) {
                Text(
                    text = entry.description,
                    fontSize = 8.sp,
                    modifier = Modifier.fillMaxWidth(0.4f),
                )
            }
            Text(text = entry.date)
            Text(text = entry.score.toString())
        }
        Spacer(
            Modifier
                .weight(1f)
                .fillMaxWidth(),
        )

//        Image(
//            painter = painterResource(entry.imageId),
//            contentDescription = "Image",
//            modifier = Modifier.size(80.dp),
//        )


    }

//    if (expanded) {
//        Row(modifier = Modifier.fillMaxWidth()) {
//
//            Button(
//                modifier = Modifier
//                    .padding(vertical = 16.dp)
//                    .height(56.dp)
//                    .fillMaxWidth(0.5f),
//                onClick = { navController.navigate(Screen.AddPlace.route) },
//                shape = MaterialTheme.shapes.extraLarge,
//            ) {
//                Text(
//                    text = stringResource(id = R.string.add_new_place),
//                    style = MaterialTheme.typography.bodyLarge,
//                )
//            }
//
//            Button(
//                modifier = Modifier
//                    .padding(vertical = 16.dp)
//                    .height(56.dp)
//                    .fillMaxWidth(0.5f),
//                onClick = { model.onEvent(PlaceEvent.RemovePlacePlaceEvent) },
//                shape = MaterialTheme.shapes.extraLarge,
//            ) {
//                Text(
//                    text = stringResource(id = R.string.remove_place),
//                    style = MaterialTheme.typography.bodyLarge,
//                )
//            }
//
//        }
//    }


}
