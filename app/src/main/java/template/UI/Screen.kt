package template.UI

sealed class Screen(val route: String) {
    object PlacesScreen : Screen("places_screen")
    object AddEditScreen : Screen("add_edit_screen")


}
