package template.domain.usecase

import template.domain.model.Place
import template.domain.repository.PlaceRepository

class UpdatePlace(
    private val repository: PlaceRepository,
) {

    suspend operator fun invoke(place: Place): Int {
        return repository.updatePlace(place)
    }

}
