package template.domain.usecase

import template.domain.repository.PlaceRepository

class GetPlace(
    private val repository: PlaceRepository,
) {

    operator fun invoke() {
        repository.getPlaces()
    }

}
