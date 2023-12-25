package template

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import template.UI.Screen
import template.UI.addEditPlace.AddPlaceScreen
import template.UI.maps.mapScreen
import template.UI.places.PlaceScreen
import template.theme.TemplateTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TemplateTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                ) {

                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.DisplayMap.route) {
                        composable(
                            route = Screen.AddEditScreen.route + "?placeId={placeId}?locationId={locationId}",
                            arguments = listOf(
                                navArgument(
                                    name = "placeId",
                                ) {
                                    type = NavType.LongType
                                    defaultValue = -1L
                                },

                                navArgument(
                                    name = "locationId",
                                ) {
                                    type = NavType.StringType
                                    defaultValue = "-1"
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

                        composable(
                            route = Screen.DisplayMap.route
                        ) {
                            mapScreen(navController = navController)
                        }

                    }
                }
            }
        }
    }

}
