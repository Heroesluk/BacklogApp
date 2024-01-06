package template.UI

sealed class Screen(val route: String) {
    object PlacesScreen : Screen("places_screen")
    object AddEditScreen : Screen("add_edit_screen")

    object DisplayMap: Screen("map")

    object AssistantScreen: Screen("assistant")


}
