package template.domain.usecase

import template.domain.model.Place
import template.domain.repository.PlaceRepository

class GetPlacesList(
    private val repository: PlaceRepository,
) {

    operator fun invoke(): List<Place> {
        return repository.getPlacesList()
    }
}
