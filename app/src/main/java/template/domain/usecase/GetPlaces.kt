package template.domain.usecase

import template.domain.model.Place
import template.domain.repository.PlaceRepository

class GetPlaces(
    private val repository: PlaceRepository,
) {

    operator fun invoke(): List<Place> {
        return repository.getPlaces()
    }

}
