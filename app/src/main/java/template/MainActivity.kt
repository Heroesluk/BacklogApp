package template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import template.UI.Screen
import template.UI.addEditPlace.AddPlaceScreen
import template.UI.places.PlaceScreen
import template.theme.TemplateTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            TemplateTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                ) {

                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.AddEditScreen.route) {
                        composable(route = Screen.AddEditScreen.route){
                            AddPlaceScreen(navController = navController)
                        }

                        composable(route = Screen.PlacesScreen.route) {
                            PlaceScreen(navController = navController)
                        }

                    }
                }
            }
        }
    }

}
