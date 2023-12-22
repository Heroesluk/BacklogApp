package template.domain.usecase

data class PlaceUseCases(
    val addPlace: AddPlace,
    val deletePlace: DeletePlace,
    val getPlace: GetPlace,
    val getPlaces: GetPlaces,
    val getPlacesList: GetPlacesList
)

