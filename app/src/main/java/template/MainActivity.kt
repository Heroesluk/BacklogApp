package template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                    NavHost(navController = navController, startDestination = Screen.PlacesScreen.route) {
                        composable(
                            route = Screen.AddEditScreen.route + "?placeId={placeId}",
                            arguments = listOf(
                                navArgument(
                                    name = "placeId",
                                ) {
                                    type = NavType.LongType
                                    defaultValue = -1L
                                },
                            ),

                            ) {
                            AddPlaceScreen(navController = navController)
                        }

                        composable(
                            route = Screen.PlacesScreen.route,
                        ) {
                            PlaceScreen(navController = navController)
                        }

                    }
                }
            }
        }
    }

}
