package template.domain.usecase

import template.domain.model.Place
import template.domain.repository.PlaceRepository

class GetPlace(
    private val repository: PlaceRepository,
) {
    suspend operator fun invoke(placeId: Long): Place? {
        return repository.getPlaceById(placeId)
    }

}
